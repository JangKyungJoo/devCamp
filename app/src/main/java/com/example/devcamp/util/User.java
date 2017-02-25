package com.example.devcamp.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.ArrayList;

public class User {

    public static final String CLEANSING_NAME = "cleanItemName";
    public static final String SKINCARE_NAME = "skinItemName";
    public static final String CLEANSING_CHECK = "cleanCheck";
    public static final String SKINCARE_CHECK = "skinCheck";
    public static final String CHECKLIST_RESULT = "checkListResult";
    public static final int VERY_GOOD = 2;
    public static final int GOOD = 1;
    public static final int BAD = 0;
    String id;
    boolean isCheck;

    public static void saveItemName(Context context, String key, String id, String name) {
        // type : yyyy.mm.dd_name

        SharedPreferences preferences = context.getSharedPreferences(key, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString(id, name);

        editor.commit();
    }

    public static void saveItemCheck(Context context, String key, String id, boolean isCheck) {

        SharedPreferences preferences = context.getSharedPreferences(key, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putBoolean(id, isCheck);
        Log.d("TEST", "save " + id +", " + isCheck);

        editor.commit();
    }

    public static ArrayList<User> load(Context context, String date, String type) {

        if(type.equals(CLEANSING_NAME)){

            SharedPreferences itemName = context.getSharedPreferences(date + "_" + CLEANSING_NAME, Context.MODE_PRIVATE);
            SharedPreferences itemCheck = context.getSharedPreferences(date + "_" + CLEANSING_CHECK, Context.MODE_PRIVATE);

            ArrayList<User> users = new ArrayList<>();

            for(int i=0; i<itemName.getAll().size(); i++){
                User user = new User();
                user.setID(itemName.getString(""+i, null));
                user.setCheck(itemCheck.getBoolean(""+i, false));
                users.add(user);
            }

            return users;
        }

        SharedPreferences itemName = context.getSharedPreferences(date + "_" + SKINCARE_NAME, Context.MODE_PRIVATE);
        SharedPreferences itemCheck = context.getSharedPreferences(date + "_" + SKINCARE_CHECK, Context.MODE_PRIVATE);
        ArrayList<User> users = new ArrayList<>();

        for(int i=0; i<itemName.getAll().size(); i++){
            User user = new User();
            user.setID(itemName.getString(""+i, null));
            user.setCheck(itemCheck.getBoolean(""+i, false));
            users.add(user);
        }

        return users;
    }

    public static void clearData(Context context, String date){
        SharedPreferences cn = context.getSharedPreferences(date + "_" + CLEANSING_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor ce = cn.edit();
        ce.clear();
        ce.commit();
        SharedPreferences sn = context.getSharedPreferences(date + "_" + SKINCARE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor se = sn.edit();
        se.clear();
        se.commit();
        SharedPreferences cc = context.getSharedPreferences(date + "_" + CLEANSING_CHECK, Context.MODE_PRIVATE);
        SharedPreferences.Editor cce = cc.edit();
        cce.clear();
        cce.commit();
        SharedPreferences sc = context.getSharedPreferences(date + "_" + SKINCARE_CHECK, Context.MODE_PRIVATE);
        SharedPreferences.Editor sce = sc.edit();
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
        /*
        String date = "2017.2.25";

        saveItemName(context, date + "_" + SKINCARE_NAME, ""+0, "use eye remover");
        saveItemCheck(context, date + "_" + SKINCARE_CHECK, ""+0, false);
        saveItemName(context, date + "_" + SKINCARE_NAME, ""+1, "hello world");
        saveItemCheck(context, date + "_" + SKINCARE_CHECK, ""+1, false);

        saveItemName(context, date + "_" + CLEANSING_NAME, ""+0, "use eye remover");
        saveItemCheck(context, date + "_" + CLEANSING_CHECK, ""+0, false);
        saveItemName(context, date + "_" + CLEANSING_NAME, ""+1, "hello world");
        saveItemCheck(context, date + "_" + CLEANSING_CHECK, ""+1, false);
        saveItemName(context, date + "_" + CLEANSING_NAME, ""+2, "use bubble bubble");
        saveItemCheck(context, date + "_" + CLEANSING_CHECK, ""+2, false);
        */

        saveCheckList(context, "2017.2.24", GOOD);
        saveCheckList(context, "2017.2.23", VERY_GOOD);
        saveCheckList(context, "2017.2.22", GOOD);
        saveCheckList(context, "2017.2.21", GOOD);
        saveCheckList(context, "2017.2.20", BAD);
        saveCheckList(context, "2017.2.19", BAD);
        saveCheckList(context, "2017.2.18", GOOD);
        saveCheckList(context, "2017.2.17", BAD);
        saveCheckList(context, "2017.2.16", GOOD);
        saveCheckList(context, "2017.2.15", VERY_GOOD);
        saveCheckList(context, "2017.2.14", VERY_GOOD);
        saveCheckList(context, "2017.2.13", VERY_GOOD);
        saveCheckList(context, "2017.2.12", VERY_GOOD);
    }

    public static boolean hasData(Context context, String date){
        SharedPreferences sn = context.getSharedPreferences(date + "_" + SKINCARE_NAME, Context.MODE_PRIVATE);
        SharedPreferences cn = context.getSharedPreferences(date + "_" + CLEANSING_NAME, Context.MODE_PRIVATE);

        if(sn.getAll().size() + cn.getAll().size() > 0)
            return true;
        return false;
    }

    public static void saveCheckList(Context context, String date, int flag){
        SharedPreferences preferences = context.getSharedPreferences(CHECKLIST_RESULT, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putInt(date, flag);

        editor.commit();
    }

    public static int getCheckListResult(Context context, String date){
        SharedPreferences preferences = context.getSharedPreferences(CHECKLIST_RESULT, Context.MODE_PRIVATE);
        return preferences.getInt(date, 0);
    }
}
