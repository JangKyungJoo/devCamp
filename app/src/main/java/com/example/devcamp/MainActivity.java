package com.example.devcamp;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.devcamp.entity.CheckListResult;
import com.example.devcamp.entity.CleansingList;
import com.example.devcamp.entity.Memo;
import com.example.devcamp.entity.SkincareList;
import com.example.devcamp.guide.GuideActivity;
import com.example.devcamp.setting.CleansingActivity;
import com.example.devcamp.setting.SettingActivity;
import com.example.devcamp.setting.SkincareActivity;
import com.example.devcamp.util.CheckListResultDBHelper;
import com.example.devcamp.util.CleansingListDBHelper;
import com.example.devcamp.util.MemoDBHelper;
import com.example.devcamp.util.SkincareListDBHelper;
import com.example.devcamp.util.User;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.Queue;

import sun.bob.mcalendarview.listeners.OnDateClickListener;
import sun.bob.mcalendarview.listeners.OnExpDateClickListener;
import sun.bob.mcalendarview.listeners.OnMonthScrollListener;
import sun.bob.mcalendarview.views.ExpCalendarView;
import sun.bob.mcalendarview.vo.DateData;

public class MainActivity extends AppCompatActivity {
    public static int nowMonth, nowYear, monthCount;
    public static String nowDate;
    public static final int MONTH_FLAG = 1;
    public static final int MAX_DATE_NUM = 41;
    public static Queue<Integer> monthCheckQueue;
    public static Queue<Integer> nextMonth;
    public final int SHOW_RESULT = 1;
    public final int SHOW_CHECK_LIST = 0;
    int cYear, cMonth, cDay;
    TextView YearMonthTv;
    ExpCalendarView expCalendarView;
    FrameLayout saveBtn;
    LinearLayout cleansingLayout, clean1, clean2, clean3, clean4;
    LinearLayout skinLayout, skin1, skin2, skin3, skin4;
    CheckBox cbox1, cbox2, cbox3, cbox4, sbox1, sbox2, sbox3, sbox4;
    ImageView cleanSettingBtn, skinSettingBtn, settingBtn, guideBtn, reportBtn;
    Dialog mMainDialog;
    EditText editText;
    String startDate;
    View clickView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);

        expCalendarView = ((ExpCalendarView) findViewById(R.id.calendar_exp));
        YearMonthTv = (TextView) findViewById(R.id.main_YYMM_Tv);
        settingBtn = (ImageView) findViewById(R.id.settingBtn);
        guideBtn = (ImageView) findViewById(R.id.guideBtn);
        reportBtn = (ImageView) findViewById(R.id.reportBtn);
        setCurrentDate();

        expCalendarView.setOnDateClickListener(new OnExpDateClickListener()).setOnMonthScrollListener(new OnMonthScrollListener() {
            @Override
            public void onMonthChange(int year, int month) {
                YearMonthTv.setText(String.format("%d.%d", year, month));
                monthCheckQueue.add(MONTH_FLAG + 2);
                if(nowYear > year || (nowYear==year && nowMonth > month)){
                    nextMonth.add(getNextMonth(month-1));
                }else if(nowYear < year || (nowYear==year && nowMonth < month)){
                    nextMonth.add(getNextMonth(month+1));
                }
                nowMonth = month;
                nowYear = year;
            }

            @Override
            public void onMonthScroll(float positionOffset) {
//                Log.i("listener", "onMonthScroll:" + positionOffset);
            }
        });

        expCalendarView.setOnDateClickListener(new OnDateClickListener() {
            @Override
            public void onDateClick(View view, DateData date) {
                String clickDate = date.getYear() + "." + date.getMonth() + "." + date.getDay();
                if (startDate.equals("")){
                    Toast.makeText(getApplicationContext(), "please set check list", Toast.LENGTH_SHORT).show();
                }else {
                    if(isToday(clickDate) || !User.compareToDate(clickDate, startDate)){
                        // clickDate <= today
                        int result[] = User.getCheckListResultCount(getApplicationContext(), clickDate);

                        if(isToday(clickDate)){
                            if(result[0] + result[1] > 0){
                                // has today's result
                                mMainDialog = createDialog(clickDate, SHOW_RESULT);
                                clickView = view;
                                mMainDialog.show();
                                return;
                            }else{
                                if(getCurrentCheckListCount() > 0) {
                                    // before save today's result && has checklist
                                    mMainDialog = createDialog(clickDate, SHOW_CHECK_LIST);
                                    clickView = view;
                                    mMainDialog.show();
                                    return;
                                }
                                Toast.makeText(getApplicationContext(), "please set check list", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            if(result[0] + result[1] > 0) {
                                // has clickDate's result
                                mMainDialog = createDialog(clickDate, SHOW_RESULT);
                                clickView = view;
                                mMainDialog.show();
                                return;
                            }
                            Toast.makeText(getApplicationContext(), "no check list data", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        // clickDate > today
                        if(getCurrentCheckListCount() > 0) {
                            mMainDialog = createDialog(clickDate, SHOW_CHECK_LIST);
                            clickView = view;
                            mMainDialog.show();
                            return;
                        }
                        Toast.makeText(getApplicationContext(), "please set check list", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        settingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
                startActivity(intent);
            }
        });

        guideBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), GuideActivity.class);
                startActivity(intent);
            }
        });

        reportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "report", Toast.LENGTH_SHORT).show();
            }
        });

        initMonthLayout();
        updateResult();
        expCalendarView.setDateCell(R.layout.layout_date_cell);
    }

    // init data about display month date
    public void initMonthLayout(){
        monthCheckQueue = new LinkedList<>();
        nextMonth = new LinkedList<>();
        monthCount = 0;
        monthCheckQueue.add(MONTH_FLAG+3);
        monthCheckQueue.add(MONTH_FLAG);
        monthCheckQueue.add(MONTH_FLAG);
        nextMonth.add(cMonth);
        nextMonth.add(cMonth-1);
        nextMonth.add(cMonth+1);
    }

    // update checkList result from lastUpdateDate to today
    public void updateResult(){
        String lastUpdateDate = User.getLastUpdateDate(getApplicationContext());
        CheckListResultDBHelper dbHelper = new CheckListResultDBHelper(this);
        dbHelper.getReadableDatabase();
        int cnt = getCurrentCheckListCount();
        Log.d("TEST", "start date : " + User.getStartDate(getApplicationContext()));
        Log.d("TEST", "last update date : " + lastUpdateDate + ", cnt : " + cnt);
        dbHelper.close();

        if(!lastUpdateDate.equals("") && cnt > 0 && !lastUpdateDate.equals(nowDate)){
            ArrayList<String> list = User.getBetweenDate(lastUpdateDate, nowDate);
            save(list, null, null);
        }
    }

    public void save(ArrayList<String> list, ArrayList<Boolean> cResult, ArrayList<Boolean> sResult){
        CheckListResultDBHelper dbHelper = new CheckListResultDBHelper(this);
        MemoDBHelper memoDBHelper = new MemoDBHelper(this);
        SQLiteDatabase memodb = memoDBHelper.getWritableDatabase();
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<CleansingList> clist = User.getCleansingList(getApplicationContext());
        ArrayList<SkincareList> slist = User.getSkincareList(getApplicationContext());

        if(list == null){
            // save today result
            int count = 0;
            db.execSQL("DELETE FROM " + CheckListResult.TABLE_NAME + " WHERE date='" + nowDate + "'");
            for (int j = 0; j < clist.size(); j++) {
                ContentValues row = new ContentValues();
                row.put("item", clist.get(j).getItem());
                row.put("type", 1);
                row.put("date", nowDate);
                row.put("result", cResult.get(j));
                if(cResult.get(j)) count++;
                db.insert(CheckListResult.TABLE_NAME, null, row);
            }
            for (int j = 0; j < slist.size(); j++) {
                ContentValues row = new ContentValues();
                row.put("item", slist.get(j).getItem());
                row.put("type", 2);
                row.put("date", nowDate);
                row.put("result", sResult.get(j));
                if(sResult.get(j)) count++;
                db.insert(CheckListResult.TABLE_NAME, null, row);
            }
            memodb.execSQL("DELETE FROM " + Memo.TABLE_NAME + " WHERE date='" + nowDate + "'");
            ContentValues row = new ContentValues();
            row.put("memo", editText.getText().toString());
            row.put("date", nowDate);
            memodb.insert(Memo.TABLE_NAME, null, row);
            updateView(count, clist.size(), slist.size());
        }else {
            // save between lastUpdateDate to today
            for (int i = 0; i < list.size(); i++) {
                for (int j = 0; j < clist.size(); j++) {
                    ContentValues row = new ContentValues();
                    row.put("item", clist.get(j).getItem());
                    row.put("type", 1);
                    row.put("date", list.get(i));
                    row.put("result", 0);
                    db.insert(CheckListResult.TABLE_NAME, null, row);
                }
                for (int j = 0; j < slist.size(); j++) {
                    ContentValues row = new ContentValues();
                    row.put("item", slist.get(j).getItem());
                    row.put("type", 2);
                    row.put("date", list.get(i));
                    row.put("result", 0);
                    db.insert(CheckListResult.TABLE_NAME, null, row);
                }
                ContentValues row = new ContentValues();
                row.put("memo", "");
                row.put("date", list.get(i));
                memodb.insert(Memo.TABLE_NAME, null, row);
            }
        }
        User.saveLastUpdateDate(getApplicationContext(), nowDate);
        Log.d("TEST", "update checklist reuslt");
        dbHelper.close();
        memoDBHelper.close();
    }

    public int getCurrentCheckListCount(){
        CleansingListDBHelper cDBHelper = new CleansingListDBHelper(this);
        SkincareListDBHelper sDBHelper = new SkincareListDBHelper(this);

        SQLiteDatabase db1 = cDBHelper.getReadableDatabase();
        SQLiteDatabase db2 = sDBHelper.getReadableDatabase();

        Cursor c1 = db1.query(CleansingList.TABLE_NAME, null, null, null, null, null, null, null);
        Cursor c2 = db2.query(SkincareList.TABLE_NAME, null, null, null, null, null, null, null);

        int result = c1.getCount() + c2.getCount();

        cDBHelper.close();
        sDBHelper.close();

        return result;
    }

    // create checklist dialog
    private AlertDialog createDialog(final String date, int flag) {
        final View innerView = getLayoutInflater().inflate(R.layout.layout_checklist_dialog, null);
        AlertDialog.Builder ab = new AlertDialog.Builder(this);
        ab.setView(innerView);

        editText = (EditText) innerView.findViewById(R.id.checkListMemo);

        cleansingLayout = (LinearLayout) innerView.findViewById(R.id.cleansing_layout);
        clean1 = (LinearLayout) innerView.findViewById(R.id.cleansing_1);
        clean2 = (LinearLayout) innerView.findViewById(R.id.cleansing_2);
        clean3 = (LinearLayout) innerView.findViewById(R.id.cleansing_3);
        clean4 = (LinearLayout) innerView.findViewById(R.id.cleansing_4);

        skinLayout = (LinearLayout) innerView.findViewById(R.id.skin_layout);
        skin1 = (LinearLayout) innerView.findViewById(R.id.skin_1);
        skin2 = (LinearLayout) innerView.findViewById(R.id.skin_2);
        skin3 = (LinearLayout) innerView.findViewById(R.id.skin_3);
        skin4 = (LinearLayout) innerView.findViewById(R.id.skin_4);

        cbox1 = (CheckBox) innerView.findViewById(R.id.checkBox_1);
        cbox2 = (CheckBox) innerView.findViewById(R.id.checkBox_2);
        cbox3 = (CheckBox) innerView.findViewById(R.id.checkBox_3);
        cbox4 = (CheckBox) innerView.findViewById(R.id.checkBox_4);

        sbox1 = (CheckBox) innerView.findViewById(R.id.skinbox_1);
        sbox2 = (CheckBox) innerView.findViewById(R.id.skinbox_2);
        sbox3 = (CheckBox) innerView.findViewById(R.id.skinbox_3);
        sbox4 = (CheckBox) innerView.findViewById(R.id.skinbox_4);

        cleanSettingBtn = (ImageView) innerView.findViewById(R.id.cleanSettingBtn);
        cleanSettingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CleansingActivity.class);
                startActivity(intent);
                setDismiss(mMainDialog);
            }
        });

        skinSettingBtn = (ImageView) innerView.findViewById(R.id.skinSettingBtn);
        skinSettingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SkincareActivity.class);
                startActivity(intent);
                setDismiss(mMainDialog);
            }
        });

        saveBtn = (FrameLayout) innerView.findViewById(R.id.dialog_saveBtn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isToday(date))
                    saveTodayResult();
                else
                    Toast.makeText(getApplicationContext(), "Can save only today's result", Toast.LENGTH_SHORT).show();
                setDismiss(mMainDialog);
            }
        });

        if(!isToday(date)) {
            // before || after
            editText.setEnabled(false);
            saveBtn.setEnabled(false);
            cbox1.setEnabled(false);
            cbox2.setEnabled(false);
            cbox3.setEnabled(false);
            cbox4.setEnabled(false);
            sbox1.setEnabled(false);
            sbox2.setEnabled(false);
            sbox3.setEnabled(false);
            sbox4.setEnabled(false);
        }

        if(flag == SHOW_RESULT){
            getCleansingResult(date);
            getSkincareResult(date);
            getMemo(date);
        }else{
            getCheckList();
        }

        saveBtn.requestFocus();
        return  ab.create();
    }

    private void setDismiss(Dialog dialog){
        if(dialog!=null&&dialog.isShowing())
            dialog.dismiss();
    }

    // save each select box result whether true or false
    public void saveTodayResult(){
        ArrayList<Boolean> cResult = new ArrayList<>();
        ArrayList<Boolean> sResult = new ArrayList<>();

        cResult.add(cbox1.isChecked());
        cResult.add(cbox2.isChecked());
        cResult.add(cbox3.isChecked());
        cResult.add(cbox4.isChecked());

        sResult.add(sbox1.isChecked());
        sResult.add(sbox2.isChecked());
        sResult.add(sbox3.isChecked());
        sResult.add(sbox4.isChecked());

        save(null, cResult, sResult);
    }

    public void getCheckList(){
        ArrayList<CleansingList> clist = User.getCleansingList(getApplicationContext());
        if(clist.size() > 0){
            cleansingLayout.setVisibility(View.VISIBLE);
            for(int i = 0; i<clist.size(); i++){
                switch (i) {
                    case 0:
                        clean1.setVisibility(View.VISIBLE);
                        cbox1.setText(clist.get(i).getItem());
                        break;
                    case 1:
                        clean2.setVisibility(View.VISIBLE);
                        cbox2.setText(clist.get(i).getItem());
                        break;
                    case 2:
                        clean3.setVisibility(View.VISIBLE);
                        cbox3.setText(clist.get(i).getItem());
                        break;
                    case 3:
                        clean4.setVisibility(View.VISIBLE);
                        cbox4.setText(clist.get(i).getItem());
                        break;
                    default:
                }
            }
        }
        ArrayList<SkincareList> slist = User.getSkincareList(getApplicationContext());
        if(slist.size() > 0){
            skinLayout.setVisibility(View.VISIBLE);
            for(int i = 0; i<slist.size(); i++){
                switch (i) {
                    case 0:
                        skin1.setVisibility(View.VISIBLE);
                        sbox1.setText(slist.get(i).getItem());
                        break;
                    case 1:
                        skin2.setVisibility(View.VISIBLE);
                        sbox2.setText(slist.get(i).getItem());
                        break;
                    case 2:
                        skin3.setVisibility(View.VISIBLE);
                        sbox3.setText(slist.get(i).getItem());
                        break;
                    case 3:
                        skin4.setVisibility(View.VISIBLE);
                        sbox4.setText(slist.get(i).getItem());
                        break;
                    default:
                }
            }
        }
    }

    public void getCleansingResult(String date){
        CheckListResultDBHelper dbHelper = new CheckListResultDBHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM checklist_result_table where date='" + date + "'" + " AND type=1", null);

        if(c.getCount() > 0) {
            cleansingLayout.setVisibility(View.VISIBLE);
            int i = 0;
            while(c.moveToNext()) {
                switch (i) {
                    case 0:
                        clean1.setVisibility(View.VISIBLE);
                        cbox1.setChecked(intToBool(c.getInt(4)));
                        cbox1.setText(c.getString(1));
                        break;
                    case 1:
                        clean2.setVisibility(View.VISIBLE);
                        cbox2.setChecked(intToBool(c.getInt(4)));
                        cbox2.setText(c.getString(1));
                        break;
                    case 2:
                        clean3.setVisibility(View.VISIBLE);
                        cbox3.setChecked(intToBool(c.getInt(4)));
                        cbox3.setText(c.getString(1));
                        break;
                    case 3:
                        clean4.setVisibility(View.VISIBLE);
                        cbox4.setChecked(intToBool(c.getInt(4)));
                        cbox4.setText(c.getString(1));
                        break;
                    default:
                }
                i++;
            }
        }
        dbHelper.close();
    }

    public void getSkincareResult(String date){
        CheckListResultDBHelper dbHelper = new CheckListResultDBHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM checklist_result_table where date='" + date + "'" + " AND type=2", null);

        if(c.getCount() > 0) {
            skinLayout.setVisibility(View.VISIBLE);
            int i = 0;
            while(c.moveToNext()){
                switch(i){
                    case 0:
                        skin1.setVisibility(View.VISIBLE);
                        sbox1.setChecked(intToBool(c.getInt(4)));
                        sbox1.setText(c.getString(1));
                        break;
                    case 1:
                        skin2.setVisibility(View.VISIBLE);
                        sbox2.setChecked(intToBool(c.getInt(4)));
                        sbox2.setText(c.getString(1));
                        break;
                    case 2:
                        skin3.setVisibility(View.VISIBLE);
                        sbox3.setChecked(intToBool(c.getInt(4)));
                        sbox3.setText(c.getString(1));
                        break;
                    case 3:
                        skin4.setVisibility(View.VISIBLE);
                        sbox4.setChecked(intToBool(c.getInt(4)));
                        sbox4.setText(c.getString(1));
                        break;
                    default:
                }
            }
        }
        dbHelper.close();
    }

    public void getMemo(String date){
        MemoDBHelper dbHelper = new MemoDBHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM memo_table where date='" + date + "'", null);
        if(c.getCount() > 0){
            c.moveToNext();
            editText.setText(c.getString(1));
        }
        dbHelper.close();
    }

    public void setCurrentDate(){
        Calendar c = Calendar.getInstance();
        cMonth = c.get(Calendar.MONTH) + 1;
        cYear = c.get(Calendar.YEAR);
        cDay = c.get(Calendar.DATE);
        YearMonthTv.setText(String.format("%d.%d", cYear, cMonth));
        nowDate = cYear +"."+ cMonth +"."+ cDay;
        nowMonth = cMonth;
        nowYear = cYear;
        startDate = User.getStartDate(getApplicationContext());
        Log.d("TEST", "now date : " + nowDate);
    }

    // save cleansing result(count) and display result to date cell clicked
    public void updateView(int count, int cSize, int sSize){
        if(clickView != null) {
            ImageView imageView = (ImageView) clickView.findViewById(R.id.clean_result);

            if (count == 0) {
                imageView.setImageResource(R.mipmap.icon_clean_bad);
            } else if (count == cSize + sSize) {
                imageView.setImageResource(R.mipmap.icon_clean_very_good);
            } else if(count > 0 && count < cSize + sSize){
                imageView.setImageResource(R.mipmap.icon_clean_good);
            }
            imageView.setVisibility(View.VISIBLE);
        }
    }

    public boolean intToBool(int i){
        if(i==1)
            return true;
        return false;
    }

    public boolean isToday(String date){
        if(date.equals(nowDate))
            return true;
        else
            return false;
    }

    public int getNextMonth(int now){
        if(now == 0)
            return 12;
        if(now == 13)
            return 1;
        return now;
    }
}

