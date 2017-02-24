package com.example.devcamp;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import sun.bob.mcalendarview.views.BaseMarkView;
import sun.bob.mcalendarview.vo.DayData;

public class MarkCellView extends BaseMarkView {
    public MarkCellView(Context context) {
        super(context);
    }

    public MarkCellView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setDisplayText(DayData day) {
        ((TextView) this.findViewById(R.id.id_cell_text)).setText(day.getText());
    }
}
