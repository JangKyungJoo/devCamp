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

import sun.bob.mcalendarview.listeners.OnExpDateClickListener;
import sun.bob.mcalendarview.listeners.OnMonthScrollListener;
import sun.bob.mcalendarview.views.ExpCalendarView;

public class MainActivity extends AppCompatActivity {

    TextView YearMonthTv;
    ExpCalendarView expCalendarView;
    FrameLayout settingBtn, guideBtn, reportBtn, saveBtn;
    LinearLayout cleansingLayout, clean1, clean2, clean3, clean4;
    LinearLayout skinLayout, skin1, skin2, skin3, skin4;
    CheckBox cbox1, cbox2, cbox3, cbox4, sbox1, sbox2, sbox3, sbox4;
    ImageView cleanSetBtn, skinSetBtn;
    Dialog mMainDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);

        expCalendarView = ((ExpCalendarView) findViewById(R.id.calendar_exp));
        YearMonthTv = (TextView) findViewById(R.id.main_YYMM_Tv);
        settingBtn = (FrameLayout) findViewById(R.id.settingBtn);
        guideBtn = (FrameLayout) findViewById(R.id.guideBtn);
        reportBtn = (FrameLayout) findViewById(R.id.reportBtn);

        mMainDialog = createDialog();

        //YearMonthTv.setText(Calendar.getInstance().get(Calendar.YEAR) + "年" + (Calendar.getInstance().get(Calendar.MONTH) + 1) + "月");

        expCalendarView.setOnDateClickListener(new OnExpDateClickListener()).setOnMonthScrollListener(new OnMonthScrollListener() {
            @Override
            public void onMonthChange(int year, int month) {
                YearMonthTv.setText(String.format("%d年%d月", year, month));
            }

            @Override
            public void onMonthScroll(float positionOffset) {
//                Log.i("listener", "onMonthScroll:" + positionOffset);
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
                //Toast.makeText(getApplicationContext(), "report", Toast.LENGTH_SHORT).show();
                mMainDialog.show();
            }
        });

        expCalendarView.setDateCell(R.layout.layout_date_cell);
        expCalendarView.markDate(2016, 10, 16);

    }

    private AlertDialog createDialog() {
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

        cleanSetBtn = (ImageView) innerView.findViewById(R.id.cleanSettingBtn);
        cleanSetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "move to setting", Toast.LENGTH_SHORT).show();
            }
        });

        skinSetBtn = (ImageView) innerView.findViewById(R.id.skinSettingBtn);
        skinSetBtn.setOnClickListener(new View.OnClickListener() {
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

        loadCleansingData(innerView);
        loadSkincareDate(innerView);

        saveBtn.requestFocus();
        return  ab.create();
    }

    private void setDismiss(Dialog dialog){
        if(dialog!=null&&dialog.isShowing())
            dialog.dismiss();
    }

    public void saveData(){
        ArrayList<User> cleansing = User.load(getApplicationContext(), User.CLEANSING_NAME);
        ArrayList<User> skin = User.load(getApplicationContext(), User.SKINCARE_NAME);
        if(cleansing.size() > 0){
            for (int i = 0; i < cleansing.size(); i++) {
                switch (i){
                    case 0:
                        User.saveItemCheck(getApplicationContext(), User.CLEANSING_CHECK, ""+i, cbox1.isChecked());
                        break;
                    case 1:
                        User.saveItemCheck(getApplicationContext(), User.CLEANSING_CHECK, ""+i, cbox2.isChecked());
                        break;
                    case 2:
                        User.saveItemCheck(getApplicationContext(), User.CLEANSING_CHECK, ""+i, cbox3.isChecked());
                        break;
                    case 3:
                        User.saveItemCheck(getApplicationContext(), User.CLEANSING_CHECK, ""+i, cbox4.isChecked());
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
                        User.saveItemCheck(getApplicationContext(), User.SKINCARE_CHECK, ""+i, sbox1.isChecked());
                        break;
                    case 1:
                        User.saveItemCheck(getApplicationContext(), User.SKINCARE_CHECK, ""+i, sbox2.isChecked());
                        break;
                    case 2:
                        User.saveItemCheck(getApplicationContext(), User.SKINCARE_CHECK, ""+i, sbox3.isChecked());
                        break;
                    case 3:
                        User.saveItemCheck(getApplicationContext(), User.SKINCARE_CHECK, ""+i, sbox4.isChecked());
                        break;
                    default:
                        break;
                }
            }
        }
    }

    public void loadCleansingData(View innerView){
        ArrayList<User> cleansing = User.load(getApplicationContext(), User.CLEANSING_NAME);
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

    public void loadSkincareDate(View innerView){
        ArrayList<User> skincare = User.load(getApplicationContext(), User.SKINCARE_NAME);
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
}

