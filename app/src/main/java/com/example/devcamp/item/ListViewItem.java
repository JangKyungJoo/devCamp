package com.example.devcamp.item;

/**
 * Created by samdasu on 2017-02-26.
 */

import android.graphics.drawable.Drawable;

public class ListViewItem {

    private Drawable iconDrawable;
    private String titleStr;
    private String ddayStr;
    private String dateStr;

    public Drawable getIconDrawable() {
        return iconDrawable;
    }

    public void setIconDrawable(Drawable iconDrawable) {
        this.iconDrawable = iconDrawable;
    }

    public String getTitleStr() {
        return titleStr;
    }

    public void setTitleStr(String titleStr) {
        this.titleStr = titleStr;
    }

    public String getDdayStr() {
        return ddayStr;
    }

    public void setDdayStr(String ddayStr) {
        this.ddayStr = ddayStr;
    }

    public String getDateStr() {
        return dateStr;
    }

    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }

}
