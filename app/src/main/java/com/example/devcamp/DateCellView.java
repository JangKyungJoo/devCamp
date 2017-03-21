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
    // c + Date is Current Date (Real Today's Date)
    // n + Date is date which rendering DateCell's date
    String nDate, cDate;
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

        // get data from startDate to Today
        if(User.compareToDate(nDate, start) && User.compareToDate(cDate, nDate)){
            int result = User.getCheckListResultCount(getContext(), nDate);

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
        cDate = cYear + "." + cMonth + "." + (cDay - 1);
    }
}
