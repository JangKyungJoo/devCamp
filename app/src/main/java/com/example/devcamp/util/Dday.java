package com.example.devcamp.util;

/**
 * Created by samdasu on 2017-02-25.
 */

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;

public class Dday {

    public static final String CLEANSING_NAME = "cleansing";
    public static final String SKINCARE_NAME = "skincare";
    public static final String CLEANSING_CHECK = "cleansing_check";
    public static final String SKINCARE_CHECK = "skincare_check";
    public static final String DDAY_NAME = "dday";
    public static final String DDAY_CHECK = "dday_check";
    String id;
    boolean isCheck;

    public static void saveItemName(Context context, String type, String id, String name) {

        if(type.equals(DDAY_NAME)){

            SharedPreferences preferences = context.getSharedPreferences(DDAY_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();

            editor.putString(id, name);

            editor.commit();
            return;
        }

        SharedPreferences preferences = context.getSharedPreferences(SKINCARE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString(id, name);

        editor.commit();
    }

    public static void saveItemCheck(Context context, String type, String id, boolean isCheck) {

        if(type.equals(DDAY_CHECK)){

            SharedPreferences preferences = context.getSharedPreferences(DDAY_CHECK, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();

            editor.putBoolean(id, isCheck);

            editor.commit();
            return;
        }

        SharedPreferences preferences = context.getSharedPreferences(SKINCARE_CHECK, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putBoolean(id, isCheck);

        editor.commit();
    }

    public static ArrayList<Dday> load(Context context, String type) {

        if(type.equals(DDAY_NAME)){

            SharedPreferences itemName = context.getSharedPreferences(DDAY_NAME, Context.MODE_PRIVATE);
            SharedPreferences itemCheck = context.getSharedPreferences(DDAY_CHECK, Context.MODE_PRIVATE);

            ArrayList<Dday> ddays = new ArrayList<>();

            for(int i=0; i<itemName.getAll().size(); i++){
                Dday dday = new Dday();
                dday.setID(itemName.getString(""+i, null));
                dday.setCheck(itemCheck.getBoolean(""+i, false));
                ddays.add(dday);
            }

            return ddays;
        }

        SharedPreferences itemName = context.getSharedPreferences(SKINCARE_NAME, Context.MODE_PRIVATE);
        SharedPreferences itemCheck = context.getSharedPreferences(SKINCARE_CHECK, Context.MODE_PRIVATE);
        ArrayList<Dday> ddays = new ArrayList<>();

        for(int i=0; i<itemName.getAll().size(); i++){
            Dday dday = new Dday();
            dday.setID(itemName.getString(""+i, null));
            dday.setCheck(itemCheck.getBoolean(""+i, false));
            ddays.add(dday);
        }

        return ddays;
    }

    public static void clearData(Context context){
        SharedPreferences cn = context.getSharedPreferences(DDAY_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor ce = cn.edit();
        ce.clear();
        ce.commit();
        SharedPreferences sn = context.getSharedPreferences(SKINCARE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor se = sn.edit();
        se.clear();
        se.commit();
        SharedPreferences cc = context.getSharedPreferences(DDAY_CHECK, Context.MODE_PRIVATE);
        SharedPreferences.Editor cce = cc.edit();
        cce.clear();
        cce.commit();
        SharedPreferences sc = context.getSharedPreferences(SKINCARE_CHECK, Context.MODE_PRIVATE);
        SharedPreferences.Editor sce = sn.edit();
        sce.clear();
        sce.commit();
    }

    public void setID(String id){
        this.id = id;
    }

    public void setCheck(boolean isCheck){
        this.isCheck = isCheck;
    }

    public String getID(){
        return id;
    }

    public boolean getCheck(){
        return isCheck;
    }

    public static void mockUpData(Context context){

        saveItemName(context, Dday.SKINCARE_NAME, ""+0, "use eye remover");
        saveItemCheck(context, Dday.SKINCARE_NAME, ""+0, false);
        saveItemName(context, Dday.SKINCARE_NAME, ""+1, "hello world");
        saveItemCheck(context, Dday.SKINCARE_NAME, ""+1, false);

        saveItemName(context, Dday.DDAY_NAME, ""+0, "use eye remover");
        saveItemCheck(context, Dday.DDAY_CHECK, ""+0, false);
        saveItemName(context, Dday.DDAY_NAME, ""+1, "hello world");
        saveItemCheck(context, Dday.DDAY_CHECK, ""+1, false);
        saveItemName(context, Dday.DDAY_NAME, ""+2, "use bubble bubble");
        saveItemCheck(context, Dday.DDAY_CHECK, ""+2, false);
    }
}