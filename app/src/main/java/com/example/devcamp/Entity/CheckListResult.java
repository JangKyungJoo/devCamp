package com.example.devcamp.entity;

import java.io.Serializable;

public class CheckListResult implements Serializable {
    public static final String TABLE_NAME = "checklist_result_table";
    private int _id;
    private String item, date;
    private int type;   // 1 : cleansing, 2 : skincare
    private int result;

    public CheckListResult(int _id, String item, int type, int result, String date){
        this.item = item;
        this.type = type;
        this.result = result;
        this.date = date;
        this._id = _id;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getResult(){
        return result;
    }

    public void setResult(int result){
        this.result = result;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
