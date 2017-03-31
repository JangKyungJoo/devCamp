package com.example.devcamp.Entity;

public class SkincareList {
    public static final String TABLE_NAME = "skincare_list_table";
    private int _id;
    private String item;

    public SkincareList(int _id, String item){
        this._id = _id;
        this.item = item;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int get_id() {
        return _id;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }
}
