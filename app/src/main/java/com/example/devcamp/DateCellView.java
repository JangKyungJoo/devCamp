package com.example.devcamp;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.AbsListView;
import android.widget.TextView;

import sun.bob.mcalendarview.CellConfig;
import sun.bob.mcalendarview.MarkStyleExp;
import sun.bob.mcalendarview.views.BaseCellView;
import sun.bob.mcalendarview.vo.DayData;

/**
 * Created by bob.sun on 15/8/30.
 */
public class DateCellView extends BaseCellView {
    TextView textView;
    private AbsListView.LayoutParams matchParentParams;

    public DateCellView(Context context) {
        super(context);
        initLayout();
    }

    public DateCellView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initLayout();
    }

    @Override
    public void setDisplayText(DayData day) {
        //textView.setText(day.getText());
        ((TextView) this.findViewById(R.id.id_cell_text)).setText(day.getText());
    }

    private void initLayout() {
        matchParentParams = new AbsListView.LayoutParams((int) CellConfig.cellWidth, (int) CellConfig.cellHeight);
        this.setLayoutParams(matchParentParams);
        this.setOrientation(VERTICAL);
        /*
        textView = (TextView) findViewById(R.id.id_cell_text);
        textView.setGravity(Gravity.CENTER);
        textView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, (float) 1.0));
        this.addView(textView);*/
    }

    @Override
    protected void onMeasure(int measureWidthSpec, int measureHeightSpec) {
        super.onMeasure(measureWidthSpec, measureHeightSpec);
    }

    public boolean setDateChoose() {
        setBackgroundDrawable(MarkStyleExp.choose);
        textView.setTextColor(Color.WHITE);
        return true;
    }

    public void setDateToday() {
        setBackgroundDrawable(MarkStyleExp.today);
        textView.setTextColor(Color.rgb(105, 75, 125));
    }

    public void setDateNormal() {
        textView.setTextColor(Color.BLACK);
        setBackgroundDrawable(null);
    }

    public void setTextColor(String text, int color) {
        textView.setText(text);
        if (color != 0) {
            textView.setTextColor(color);
        }

    }

}
