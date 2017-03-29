package com.example.devcamp.entity;

import java.io.Serializable;

public class CleansingList implements Serializable{
    public static final String TABLE_NAME = "cleansing_list_table";
    private int _id;
    private String item;

    public CleansingList(int _id, String item){
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
