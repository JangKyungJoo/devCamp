package com.example.devcamp.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

public class Special {

    public static final String SPECIALCARE_NAME = "specialCareName";
    public static final String DDAY_NAME = "ddayName";
    public static final String NEWPRODUCT_NAME = "newproductName";
    public static final String SPECIAL_CHECK = "specialCheck";
    public static final String DDAY_CHECK = "ddayCheck";
    public static final String CHECKLIST_RESULT = "checkListResult";
    public static final String CURRENT_CLEANSINGLIST = "currentCleasingList";
    public static final String CURRENT_SKINCARELIST = "currentSkinCareList";
    public static final int VERY_GOOD = 2;
    public static final int GOOD = 1;
    public static final int BAD = 0;
    String id;
    String name;
    boolean isCheck;

    //save schedule
    public static void saveSpecialName(Context context, String key, String id, String name){
        SharedPreferences preferences = context.getSharedPreferences(key, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(id, name);
        editor.commit();
    }
    //modify schedule
    public static void modifySepcial(Context context, String key, String prevId, String id, String name){
        SharedPreferences preferences = context.getSharedPreferences(key, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.remove(prevId).apply();
        editor.putString(id, name);
        editor.commit();
    }

    //delete schedule
    public static void deleteSpecial(Context context, String key, String id){
        SharedPreferences preferences = context.getSharedPreferences(key, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.remove(id);

        editor.commit();
    }
    public static void saveSpecialCare(Context context, String key, String id, String name) {

        SharedPreferences preferences = context.getSharedPreferences(key, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString(id, name);

        editor.commit();
    }

    public static void saveItemCheck(Context context, String key, String id, boolean isCheck) {

        SharedPreferences preferences = context.getSharedPreferences(key, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putBoolean(id, isCheck);

        editor.commit();
    }

    public static ArrayList<Special> load(Context context, String date, String type) {

        if(type.equals(DDAY_NAME)) {
            SharedPreferences current = context.getSharedPreferences(DDAY_NAME, Context.MODE_PRIVATE);

            ArrayList<Special> specials = new ArrayList<>();

            Map<String, ?> map = current.getAll();
            for (Map.Entry<String, ?> entry : map.entrySet()) {
                Special special = new Special();
                special.setID(entry.getKey());
                special.setName(entry.getValue().toString());
                //System.out.println("key:"+entry.getKey()+" ,value"+entry.getValue().toString());
                specials.add(special);
            }


            return specials;
        }else if(type.equals(NEWPRODUCT_NAME)){
            SharedPreferences current = context.getSharedPreferences(NEWPRODUCT_NAME, Context.MODE_PRIVATE);

            ArrayList<Special> specials = new ArrayList<>();

            Map<String, ?> map = current.getAll();
            for (Map.Entry<String, ?> entry : map.entrySet()) {
                Special special = new Special();
                special.setID(entry.getKey());
                special.setName(entry.getValue().toString());
                //System.out.println("key:"+entry.getKey()+" ,value"+entry.getValue().toString());
                specials.add(special);
            }
            return specials;
        }else if(type.equals(SPECIALCARE_NAME)){
            SharedPreferences current = context.getSharedPreferences(SPECIALCARE_NAME, Context.MODE_PRIVATE);

            ArrayList<Special> specials = new ArrayList<>();

            Map<String, ?> map = current.getAll();
            for (Map.Entry<String, ?> entry : map.entrySet()) {
                Special special = new Special();
                special.setID(entry.getKey());
                special.setName(entry.getValue().toString());
                specials.add(special);
            }
            return specials;
        }else{
            return null;
        }
    }

    public static void clearData(Context context, String date){
        SharedPreferences cn = context.getSharedPreferences(date + "_" + SPECIALCARE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor ce = cn.edit();
        ce.clear();
        ce.commit();
        SharedPreferences sn = context.getSharedPreferences(date + "_" + SPECIALCARE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor se = sn.edit();
        se.clear();
        se.commit();
        SharedPreferences cc = context.getSharedPreferences(date + "_" + SPECIAL_CHECK, Context.MODE_PRIVATE);
        SharedPreferences.Editor cce = cc.edit();
        cce.clear();
        cce.commit();
        SharedPreferences sc = context.getSharedPreferences(date + "_" + DDAY_CHECK, Context.MODE_PRIVATE);
        SharedPreferences.Editor sce = sc.edit();
        sce.clear();
        sce.commit();

    }

    public void setID(String id){
        this.id = id;
    }

    public void setName(String name){this.name = name;}

    public void setCheck(boolean isCheck){
        this.isCheck = isCheck;
    }

    public String getID(){
        return id;
    }

    public String getName(){return name;}

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
*/
        SharedPreferences preferences = context.getSharedPreferences(CHECKLIST_RESULT, Context.MODE_PRIVATE);
        Log.d("Test" ," result : " + preferences.getInt("2017.2.25", 9999));
        SharedPreferences.Editor editor = preferences.edit();

        editor.putInt("2017.2.25", VERY_GOOD);
        editor.commit();
    }

    public static boolean hasData(Context context, String date){
        if(compareDate(date)) {
            return true;
        }
        SharedPreferences sn = context.getSharedPreferences(date + "_" + DDAY_NAME, Context.MODE_PRIVATE);
        SharedPreferences cn = context.getSharedPreferences(date + "_" + SPECIALCARE_NAME, Context.MODE_PRIVATE);

        if (sn.getAll().size() + cn.getAll().size() > 0)
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
        return preferences.getInt(date, -1);
    }

    public static void saveCurrentCleansingList(Context context, ArrayList<String> list){
        SharedPreferences preferences = context.getSharedPreferences(CURRENT_CLEANSINGLIST, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        int now = preferences.getAll().size();
        for(int i = 0; i < now; i++){
            editor.remove("" + i);
            editor.commit();
        }
        for(int i = 0; i< list.size(); i++){
            editor.putString("" + i, list.get(i));
            editor.commit();
        }
    }

    public static ArrayList<String> getCurrentCleansingList(Context context){
        SharedPreferences preferences = context.getSharedPreferences(CURRENT_CLEANSINGLIST, Context.MODE_PRIVATE);
        ArrayList<String> cleansingList = new ArrayList<>();

        for(int i=0; i<preferences.getAll().size(); i++){
            cleansingList.add(preferences.getString(""+i, ""));
        }
        return cleansingList;
    }

    public static ArrayList<String> getCurrentSkinCareList(Context context){
        SharedPreferences preferences = context.getSharedPreferences(CURRENT_SKINCARELIST, Context.MODE_PRIVATE);
        ArrayList<String> skincareList = new ArrayList<>();

        for(int i=0; i<preferences.getAll().size(); i++){
            skincareList.add(preferences.getString(""+i, ""));
        }
        return skincareList;
    }

    public static boolean compareDate(String date){
        Log.d("TEST", "date : " + date);
        String[] temp = date.split("\\.");
        Calendar c = Calendar.getInstance();
        c.set(Integer.parseInt(temp[0]), Integer.parseInt(temp[1]), Integer.parseInt(temp[2]));
        Calendar d = Calendar.getInstance();
        d.set(d.get(Calendar.YEAR), d.get(Calendar.MONTH) + 1, d.get(Calendar.DAY_OF_MONTH));
        if(c.compareTo(d) >= 0)
            return true;
        else
            return false;
    }
}
