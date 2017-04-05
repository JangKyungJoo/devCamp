package com.example.devcamp;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.devcamp.util.User;

import java.util.Calendar;

import sun.bob.mcalendarview.CellConfig;
import sun.bob.mcalendarview.views.BaseCellView;
import sun.bob.mcalendarview.vo.DayData;

public class DateCellView extends BaseCellView {
    // n + Date is date which rendering DateCell's date
    String nDate, today;
    int nYear, nMonth, nDay, cYear, cMonth, cDay;
    ImageView imageView;
    TextView textView;
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
        textView = (TextView) findViewById(R.id.id_cell_text);
        textView.setText(day.getText());
        getUserData();
        setMonthDayText();
        if(isToday()) {
            this.findViewById(R.id.today).setVisibility(VISIBLE);
        }
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

    // set date text, now month text color is black and prev/next month text color is gray
    public void setMonthDayText(){
        // initially added (4 + 42(date num)) + (1 + 42) + (1 + 42 + 4)
        // after month change, added 3 + 42
        // check only date num(42)
        if(MainActivity.monthCheckQueue.peek() != null){
            if(MainActivity.monthCount < MainActivity.monthCheckQueue.peek()){
                // except previous num 42
                MainActivity.monthCount++;
            }else if(MainActivity.monthCount == MainActivity.MAX_DATE_NUM + MainActivity.monthCheckQueue.peek()){
                // last num
                MainActivity.monthCheckQueue.poll();
                MainActivity.nextMonth.poll();
                MainActivity.monthCount = 0;
            }else{
                MainActivity.monthCount++;
                if(nMonth == MainActivity.nextMonth.peek()){
                    textView.setTextColor(Color.BLACK);
                }else{
                    textView.setTextColor(Color.GRAY);
                }
            }
        }
    }

    public boolean isToday(){
        if(nYear == cYear && nMonth == cMonth && nDay == cDay) {
            return true;
        }
        return false;
    }

    // get user's checklist data
    public void getUserData(){
        String start = User.getStartDate(getContext());
        imageView = (ImageView) this.findViewById(R.id.clean_result);

        // get result from startDate to Today
        if(User.compareToDate(nDate, start) && User.compareToDate(today, nDate)){
            // result[0] : count of true, result[1] : count of false;
            int result[] = User.getCheckListResultCount(getContext(), nDate);
            int total = result[0] + result[1];

            if(total > 0) {
                if (result[0] == total) {
                    imageView.setImageResource(R.mipmap.icon_clean_very_good);
                    imageView.setVisibility(VISIBLE);
                } else if (result[0] > 0 && result[0] < total) {
                    imageView.setImageResource(R.mipmap.icon_clean_good);
                    imageView.setVisibility(VISIBLE);
                } else if (result[0] == 0) {
                    imageView.setImageResource(R.mipmap.icon_clean_bad);
                    imageView.setVisibility(VISIBLE);
                } else {
                    imageView.setVisibility(GONE);
                }
            }
        }
    }

    public void setDate(DayData day){
        Calendar c = Calendar.getInstance();
        nYear = day.getDate().getYear();
        nMonth = day.getDate().getMonth();
        nDay = day.getDate().getDay();
        nDate = nYear + "." + nMonth + "." + nDay;

        cYear = c.get(Calendar.YEAR);
        cMonth = c.get(Calendar.MONTH) + 1;
        cDay = c.get(Calendar.DATE);
        today = cYear + "." + cMonth + "." + cDay;
    }
}
