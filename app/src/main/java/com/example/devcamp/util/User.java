package com.example.devcamp.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;

public class User {

    public static final String CLEANSING_NAME = "cleansing";
    public static final String SKINCARE_NAME = "skincare";
    public static final String CLEANSING_CHECK = "cleansing_check";
    public static final String SKINCARE_CHECK = "skincare_check";
    String id;
    boolean isCheck;

    public static void saveItemName(Context context, String type, String id, String name) {

        if(type.equals(CLEANSING_NAME)){

            SharedPreferences preferences = context.getSharedPreferences(CLEANSING_NAME, Context.MODE_PRIVATE);
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

        if(type.equals(CLEANSING_CHECK)){

            SharedPreferences preferences = context.getSharedPreferences(CLEANSING_CHECK, Context.MODE_PRIVATE);
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

    public static ArrayList<User> load(Context context, String type) {

        if(type.equals(CLEANSING_NAME)){

            SharedPreferences itemName = context.getSharedPreferences(CLEANSING_NAME, Context.MODE_PRIVATE);
            SharedPreferences itemCheck = context.getSharedPreferences(CLEANSING_CHECK, Context.MODE_PRIVATE);

            ArrayList<User> users = new ArrayList<>();

            for(int i=0; i<itemName.getAll().size(); i++){
                User user = new User();
                user.setID(itemName.getString(""+i, null));
                user.setCheck(itemCheck.getBoolean(""+i, false));
                users.add(user);
            }

            return users;
        }

        SharedPreferences itemName = context.getSharedPreferences(SKINCARE_NAME, Context.MODE_PRIVATE);
        SharedPreferences itemCheck = context.getSharedPreferences(SKINCARE_CHECK, Context.MODE_PRIVATE);
        ArrayList<User> users = new ArrayList<>();

        for(int i=0; i<itemName.getAll().size(); i++){
            User user = new User();
            user.setID(itemName.getString(""+i, null));
            user.setCheck(itemCheck.getBoolean(""+i, false));
            users.add(user);
        }

        return users;
    }

    public static void clearData(Context context){
        SharedPreferences cn = context.getSharedPreferences(CLEANSING_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor ce = cn.edit();
        ce.clear();
        ce.commit();
        SharedPreferences sn = context.getSharedPreferences(SKINCARE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor se = sn.edit();
        se.clear();
        se.commit();
        SharedPreferences cc = context.getSharedPreferences(CLEANSING_CHECK, Context.MODE_PRIVATE);
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

        saveItemName(context, User.SKINCARE_NAME, ""+0, "use eye remover");
        saveItemCheck(context, User.SKINCARE_NAME, ""+0, false);
        saveItemName(context, User.SKINCARE_NAME, ""+1, "hello world");
        saveItemCheck(context, User.SKINCARE_NAME, ""+1, false);

        saveItemName(context, User.CLEANSING_NAME, ""+0, "use eye remover");
        saveItemCheck(context, User.CLEANSING_CHECK, ""+0, false);
        saveItemName(context, User.CLEANSING_NAME, ""+1, "hello world");
        saveItemCheck(context, User.CLEANSING_CHECK, ""+1, false);
        saveItemName(context, User.CLEANSING_NAME, ""+2, "use bubble bubble");
        saveItemCheck(context, User.CLEANSING_CHECK, ""+2, false);
    }
}
