package com.example.devcamp;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.devcamp.util.User;

import sun.bob.mcalendarview.CellConfig;
import sun.bob.mcalendarview.utils.CurrentCalendar;
import sun.bob.mcalendarview.views.BaseCellView;
import sun.bob.mcalendarview.vo.DayData;

public class DateCellView extends BaseCellView {
    // c + Date is Current Date (Real Today's Date)
    // n + Date is date which rendering DateCell's date
    String nDate;
    int nYear, nMonth, nDay, cYear, cMonth, cDay;
    ImageView imageView;
    private AbsListView.LayoutParams matchParentParams;
    Context context;

    public DateCellView(Context context) {
        super(context);
        this.context = context;
        initLayout();
    }

    public DateCellView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initLayout();
    }

    @Override
    public void setDisplayText(DayData day) {
        setDate(day);
        ((TextView) this.findViewById(R.id.id_cell_text)).setText(day.getText());
        getResult();
/*
        if(nMonth == MainActivity.nowMonth){
            ((TextView) this.findViewById(R.id.id_cell_text)).setTextColor(Color.BLACK);
        }else if(nMonth == MainActivity.nowMonth + 1 || nMonth == MainActivity.nowMonth - 1){
            ((TextView) this.findViewById(R.id.id_cell_text)).setTextColor(Color.DKGRAY);
        }else{
            ((TextView) this.findViewById(R.id.id_cell_text)).setTextColor(Color.DKGRAY);
        }*/

        if(isThisMonth())
            ((TextView) this.findViewById(R.id.id_cell_text)).setTextColor(Color.BLACK);
/*        else
            ((TextView) this.findViewById(R.id.id_cell_text)).setTextColor(Color.DKGRAY);
*/
        if(isToday())
            this.findViewById(R.id.today).setVisibility(VISIBLE);
    }

    private void initLayout() {
        matchParentParams = new AbsListView.LayoutParams((int) CellConfig.cellWidth, (int) (CellConfig.cellHeight * 1.35) );
        this.setLayoutParams(matchParentParams);
        this.setOrientation(VERTICAL);
    }

    @Override
    protected void onMeasure(int measureWidthSpec, int measureHeightSpec) {
        super.onMeasure(measureWidthSpec, measureHeightSpec);
    }

    public boolean isToday(){
        if(nYear == cYear && nMonth == cMonth && nDay == cDay)
            return true;
        return false;
    }

    public boolean isThisMonth(){
        Log.d("TESTTEST", "nMonth : " + nMonth + ", cMonth : " + cMonth + ", nowMonth" + MainActivity.nowMonth);
        if(cYear == nYear && MainActivity.nowMonth == nMonth) {
            //Log.d("TEST", "this month. "+ day.getDate().getMonth() + ", " + day.getText());
            return true;
        }else {
            //Log.d("TEST", "not this month. "+ day.getDate().getMonth() + ", " + day.getText());
            return false;
        }
    }
    public void getResult(){
        int result = User.getCheckListResult(getContext(), nDate);
        imageView = (ImageView) this.findViewById(R.id.clean_result);

        if(result == User.VERY_GOOD) {
            imageView.setImageResource(R.mipmap.icon_clean_very_good);
            imageView.setVisibility(VISIBLE);
        }else if(result == User.GOOD) {
            imageView.setImageResource(R.mipmap.icon_clean_good);
            imageView.setVisibility(VISIBLE);
        }else if(result == User.BAD){
            imageView.setImageResource(R.mipmap.icon_clean_bad);
            imageView.setVisibility(VISIBLE);
        }else{
            imageView.setVisibility(GONE);
        }
    }

    public void setDate(DayData day){
        nYear = day.getDate().getYear();
        nMonth = day.getDate().getMonth();
        nDay = day.getDate().getDay();
        cYear = CurrentCalendar.getCurrentDateData().getYear();
        cMonth = CurrentCalendar.getCurrentDateData().getMonth();
        cDay = CurrentCalendar.getCurrentDateData().getDay();
        nDate = nYear + "." + nMonth + "." + nDay;
    }
}
