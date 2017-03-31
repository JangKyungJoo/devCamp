package com.example.devcamp.Entity;

import java.io.Serializable;

public class Memo implements Serializable{
    public static final String TABLE_NAME = "memo_table";
    private int _id;
    private String memo, date;

    public Memo(int _id, String memo, String date){
        this._id = _id;
        this.memo = memo;
        this.date = date;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
