package com.example.devcamp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.Calendar;

import sun.bob.mcalendarview.listeners.OnExpDateClickListener;
import sun.bob.mcalendarview.listeners.OnMonthScrollListener;
import sun.bob.mcalendarview.views.ExpCalendarView;

public class MainActivity extends AppCompatActivity {

    private TextView YearMonthTv;
    private ExpCalendarView expCalendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);

//      Get instance.
        expCalendarView = ((ExpCalendarView) findViewById(R.id.calendar_exp));
        YearMonthTv = (TextView) findViewById(R.id.main_YYMM_Tv);
        YearMonthTv.setText(Calendar.getInstance().get(Calendar.YEAR) + "年" + (Calendar.getInstance().get(Calendar.MONTH) + 1) + "月");

//      Set up listeners.
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
        //expCalendarView.setDateCell(R.layout.layout_date_cell);

        expCalendarView.setDateCell(R.layout.layout_date_cell);
        expCalendarView.markDate(2016, 10, 16);
    }

}

