package com.example.devcamp;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.devcamp.util.User;

import java.util.ArrayList;
import java.util.Calendar;

import sun.bob.mcalendarview.listeners.OnDateClickListener;
import sun.bob.mcalendarview.listeners.OnExpDateClickListener;
import sun.bob.mcalendarview.listeners.OnMonthScrollListener;
import sun.bob.mcalendarview.views.ExpCalendarView;
import sun.bob.mcalendarview.vo.DateData;

public class MainActivity extends AppCompatActivity {

    TextView YearMonthTv;
    ExpCalendarView expCalendarView;
    FrameLayout settingBtn, guideBtn, reportBtn, saveBtn;
    LinearLayout cleansingLayout, clean1, clean2, clean3, clean4;
    LinearLayout skinLayout, skin1, skin2, skin3, skin4;
    CheckBox cbox1, cbox2, cbox3, cbox4, sbox1, sbox2, sbox3, sbox4;
    ImageView cleanSettingBtn, skinSettingBtn;
    Dialog mMainDialog;
    String nowDate;
    DateData todayDate;
    public static int currentYear, currentMonth, currentDay, tempMonth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);

        expCalendarView = ((ExpCalendarView) findViewById(R.id.calendar_exp));
        YearMonthTv = (TextView) findViewById(R.id.main_YYMM_Tv);
        settingBtn = (FrameLayout) findViewById(R.id.settingBtn);
        guideBtn = (FrameLayout) findViewById(R.id.guideBtn);
        reportBtn = (FrameLayout) findViewById(R.id.reportBtn);
        setCurrentDate();

        expCalendarView.setOnDateClickListener(new OnExpDateClickListener()).setOnMonthScrollListener(new OnMonthScrollListener() {
            @Override
            public void onMonthChange(int year, int month) {
                YearMonthTv.setText(String.format("%d月", month));
                tempMonth = month;
            }

            @Override
            public void onMonthScroll(float positionOffset) {
//                Log.i("listener", "onMonthScroll:" + positionOffset);
            }
        });

        expCalendarView.setOnDateClickListener(new OnDateClickListener() {
            @Override
            public void onDateClick(View view, DateData date) {
                if(User.hasData(getApplicationContext(), date.getYear()+"."+date.getMonth()+"."+date.getDay())) {
                    mMainDialog = createDialog(date.getYear() + "." + date.getMonth() + "." + date.getDay());
                    mMainDialog.show();
                }
            }
        });

        settingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "setting", Toast.LENGTH_SHORT).show();
            }
        });

        guideBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "guide", Toast.LENGTH_SHORT).show();
            }
        });

        reportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "report", Toast.LENGTH_SHORT).show();
            }
        });

        expCalendarView.setDateCell(R.layout.layout_date_cell);
    }

    private AlertDialog createDialog(String date) {
        final View innerView = getLayoutInflater().inflate(R.layout.layout_checklist_dialog, null);
        AlertDialog.Builder ab = new AlertDialog.Builder(this);
        ab.setView(innerView);

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
                Toast.makeText(getApplicationContext(), "move to setting", Toast.LENGTH_SHORT).show();
            }
        });

        skinSettingBtn = (ImageView) innerView.findViewById(R.id.skinSettingBtn);
        skinSettingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "move to setting", Toast.LENGTH_SHORT).show();
            }
        });

        saveBtn = (FrameLayout) innerView.findViewById(R.id.dialog_saveBtn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
                setDismiss(mMainDialog);
            }
        });

        loadCleansingData(innerView, date);
        loadSkincareData(innerView, date);

        saveBtn.requestFocus();
        return  ab.create();
    }

    private void setDismiss(Dialog dialog){
        if(dialog!=null&&dialog.isShowing())
            dialog.dismiss();
    }

    public void saveData(){
        ArrayList<User> cleansing = User.load(getApplicationContext(), nowDate, User.CLEANSING_NAME);
        ArrayList<User> skin = User.load(getApplicationContext(), nowDate, User.SKINCARE_NAME);
        int count = 0;
        if(cleansing.size() > 0){
            for (int i = 0; i < cleansing.size(); i++) {
                switch (i){
                    case 0:
                        User.saveItemCheck(getApplicationContext(), nowDate + "_" + User.CLEANSING_CHECK, ""+i, cbox1.isChecked());
                        if(cbox1.isChecked())   count++;
                        break;
                    case 1:
                        User.saveItemCheck(getApplicationContext(), nowDate + "_" + User.CLEANSING_CHECK, ""+i, cbox2.isChecked());
                        if(cbox2.isChecked())   count++;
                        break;
                    case 2:
                        User.saveItemCheck(getApplicationContext(), nowDate + "_" + User.CLEANSING_CHECK, ""+i, cbox3.isChecked());
                        if(cbox3.isChecked())   count++;
                        break;
                    case 3:
                        User.saveItemCheck(getApplicationContext(), nowDate + "_" + User.CLEANSING_CHECK, ""+i, cbox4.isChecked());
                        if(cbox4.isChecked())   count++;
                        break;
                    default:
                        break;
                }
            }
        }
        if(skin.size() > 0){
            for (int i = 0; i < skin.size(); i++) {
                switch (i){
                    case 0:
                        User.saveItemCheck(getApplicationContext(), nowDate + "_" + User.SKINCARE_CHECK, ""+i, sbox1.isChecked());
                        if(sbox1.isChecked())   count++;
                        break;
                    case 1:
                        User.saveItemCheck(getApplicationContext(), nowDate + "_" + User.SKINCARE_CHECK, ""+i, sbox2.isChecked());
                        if(sbox2.isChecked())   count++;
                        break;
                    case 2:
                        User.saveItemCheck(getApplicationContext(), nowDate + "_" + User.SKINCARE_CHECK, ""+i, sbox3.isChecked());
                        if(sbox3.isChecked())   count++;
                        break;
                    case 3:
                        User.saveItemCheck(getApplicationContext(), nowDate + "_" + User.SKINCARE_CHECK, ""+i, sbox4.isChecked());
                        if(sbox4.isChecked())   count++;
                        break;
                    default:
                        break;
                }
            }
        }
        saveUserData(count, cleansing.size(), skin.size());
    }

    public void loadCleansingData(View innerView, String date){
        ArrayList<User> cleansing = User.load(getApplicationContext(), date, User.CLEANSING_NAME);
        if(cleansing.size() > 0) {
            cleansingLayout.setVisibility(View.VISIBLE);
            for (int i = 0; i < cleansing.size(); i++) {
                switch(i){
                    case 0:
                        clean1.setVisibility(View.VISIBLE);
                        cbox1.setChecked(cleansing.get(i).getCheck());
                        cbox1.setText(cleansing.get(i).getID());
                        break;
                    case 1:
                        clean2.setVisibility(View.VISIBLE);
                        cbox2.setChecked(cleansing.get(i).getCheck());
                        cbox2.setText(cleansing.get(i).getID());
                        break;
                    case 2:
                        clean3.setVisibility(View.VISIBLE);
                        cbox3.setChecked(cleansing.get(i).getCheck());
                        cbox3.setText(cleansing.get(i).getID());
                        break;
                    case 3:
                        clean4.setVisibility(View.VISIBLE);
                        cbox4.setChecked(cleansing.get(i).getCheck());
                        cbox4.setText(cleansing.get(i).getID());
                        break;
                    default:
                }
            }
        }
    }

    public void loadSkincareData(View innerView, String date){
        ArrayList<User> skincare = User.load(getApplicationContext(), date, User.SKINCARE_NAME);
        if(skincare.size() > 0) {
            skinLayout.setVisibility(View.VISIBLE);
            for (int i = 0; i < skincare.size(); i++) {
                switch(i){
                    case 0:
                        skin1.setVisibility(View.VISIBLE);
                        sbox1.setChecked(skincare.get(i).getCheck());
                        sbox1.setText(skincare.get(i).getID());
                        break;
                    case 1:
                        skin2.setVisibility(View.VISIBLE);
                        sbox2.setChecked(skincare.get(i).getCheck());
                        sbox2.setText(skincare.get(i).getID());
                        break;
                    case 2:
                        skin3.setVisibility(View.VISIBLE);
                        sbox3.setChecked(skincare.get(i).getCheck());
                        sbox3.setText(skincare.get(i).getID());
                        break;
                    case 3:
                        skin4.setVisibility(View.VISIBLE);
                        sbox4.setChecked(skincare.get(i).getCheck());
                        sbox4.setText(skincare.get(i).getID());
                        break;
                    default:
                }
            }
        }
    }

    public void setCurrentDate(){
        Calendar c = Calendar.getInstance();
        currentYear = c.get(Calendar.YEAR);
        currentMonth = c.get(Calendar.MONTH) + 1;
        currentDay = c.get(Calendar.DAY_OF_MONTH);
        YearMonthTv.setText(String.format("%d月", currentMonth));
        nowDate = currentYear+"."+currentMonth+"."+currentDay;
        tempMonth = currentMonth;
    }

    public void saveUserData(int count, int cSize, int sSize){
        if(count == 0) {
            User.saveCheckList(getApplicationContext(), nowDate, User.BAD);
        }else if(count == cSize + sSize){
            // very good
            User.saveCheckList(getApplicationContext(), nowDate, User.VERY_GOOD);
        }else{
            User.saveCheckList(getApplicationContext(), nowDate, User.GOOD);
        }
    }

}

