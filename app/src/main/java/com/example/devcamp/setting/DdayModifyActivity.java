package com.example.devcamp.setting;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.devcamp.R;
import com.example.devcamp.util.Special;

import java.util.ArrayList;
import java.util.Calendar;

public class DdayModifyActivity extends Activity {
    /** Called when the activity is first created. */

    private TextView ddayText;
    private TextView resultText;
    private EditText editText;
    //private Button dateButton;
    //private Button saveButton;
    private View saveButton;
    private View dateButton;
    private View deleteButton;

    private int tYear;           //오늘 연월일 변수
    private int tMonth;
    private int tDay;

    private int dYear=2017;        //디데이 연월일 변수
    private int dMonth=1;
    private int dDay=26;


    private long d;
    private long t;
    private long r;

    private int resultNumber=0;
    private String activityName ;
    private String ddayStr;
    static final int DATE_DIALOG_ID=0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dday_modify);
        Intent i = getIntent();
        activityName = i.getStringExtra("item");
        //System.out.println(activityName);
        SharedPreferences pref = getSharedPreferences(Special.DDAY_NAME, MODE_PRIVATE);
        ddayStr = pref.getString(activityName, "");
        ddayText=(TextView)findViewById(R.id.dday);
        editText=(EditText) findViewById(R.id.editText4);
        resultText=(TextView)findViewById(R.id.result);
        dateButton= findViewById(R.id.dateButton);
        saveButton= findViewById(R.id.saveButton);
        deleteButton=findViewById(R.id.deleteButton);
        //saveButton.getBackground().setColorFilter(Color.parseColor("#8564CC"), PorterDuff.Mode.MULTIPLY);
        saveButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Special.modifySepcial(getApplicationContext(), Special.DDAY_NAME, activityName, editText.getText().toString(), String.valueOf(d));
                //System.out.println("edit:"+editText.getText().toString());
                Intent backadd = new Intent(DdayModifyActivity.this, DdayActivity.class);
                startActivity(backadd);
                finish();
            }
        });
        deleteButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Special.deleteSpecial(getApplicationContext(), Special.DDAY_NAME, activityName);
                Intent backadd = new Intent(DdayModifyActivity.this, DdayActivity.class);
                startActivity(backadd);
                finish();
            }
        });
        dateButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(0);
            }
        });

        Calendar calendarTo = Calendar.getInstance();
        calendarTo.setTimeInMillis(Long.parseLong(ddayStr));

        dYear = calendarTo.get(Calendar.YEAR);
        dMonth = calendarTo.get(Calendar.MONTH);
        dDay = calendarTo.get(Calendar.DAY_OF_MONTH);

        editText.setText(activityName);

        Calendar calendar =Calendar.getInstance();              //현재 날짜 불러옴
        tYear = calendar.get(Calendar.YEAR);
        tMonth = calendar.get(Calendar.MONTH);
        tDay = calendar.get(Calendar.DAY_OF_MONTH);


        Calendar dCalendar =Calendar.getInstance();
        dCalendar.set(dYear,dMonth, dDay);

        t=calendar.getTimeInMillis();                 //오늘 날짜를 밀리타임으로 바꿈
        d=dCalendar.getTimeInMillis();              //디데이날짜를 밀리타임으로 바꿈
        r=(d-t)/(24*60*60*1000);                 //디데이 날짜에서 오늘 날짜를 뺀 값을 '일'단위로 바꿈

        resultNumber=(int)r+1;
        updateDisplay();

    }//OnCreate end

    private void updateDisplay(){

        ddayText.setText(String.format("%d/%d/%d",dYear, dMonth+1,dDay));

        if(resultNumber>=0){
            resultText.setText(String.format("D-%d", resultNumber));
        }
        else{
            int absR=Math.abs(resultNumber);
            resultText.setText(String.format("D+%d", absR));
        }
    }//디데이 날짜가 오늘날짜보다 뒤에오면 '-', 앞에오면 '+'를 붙인다

    private DatePickerDialog.OnDateSetListener dDateSetListener=new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            dYear=year;
            dMonth=monthOfYear;
            dDay=dayOfMonth;
            final Calendar dCalendar =Calendar.getInstance();
            dCalendar.set(dYear,dMonth, dDay);

            d=dCalendar.getTimeInMillis();
            r=(d-t)/(24*60*60*1000);

            resultNumber=(int)r;
            updateDisplay();
        }
    };

    @Override
    protected Dialog onCreateDialog(int id){
        if(id==DATE_DIALOG_ID){
            return new DatePickerDialog(this,dDateSetListener,tYear,tMonth,tDay);
        }
        return null;
    }


}