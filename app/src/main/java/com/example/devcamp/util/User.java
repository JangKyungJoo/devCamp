package com.example.devcamp.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.devcamp.CheckListResultDBHelper;

import java.util.ArrayList;
import java.util.Calendar;

public class User {

    public static final String CLEANSING_NAME = "cleanItemName";
    public static final String SKINCARE_NAME = "skinItemName";
    public static final String CLEANSING_CHECK = "cleanCheck";
    public static final String SKINCARE_CHECK = "skinCheck";
    public static final String CHECKLIST_RESULT = "checkListResult";
    public static final String CURRENT_CLEANSINGLIST = "currentCleasingList";
    public static final String CURRENT_SKINCARELIST = "currentSkinCareList";
    public static final String LAST_UPDATE_DATE = "last_update_date";
    public static final String START_DATE = "start_date";
    public static final int VERY_GOOD = 2;
    public static final int GOOD = 1;
    public static final int BAD = 0;
    String id;
    boolean isCheck;

    public static void saveItemCheck(Context context, String key, String id, boolean isCheck) {

        SharedPreferences preferences = context.getSharedPreferences(key, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putBoolean(id, isCheck);

        editor.commit();
    }

    public static ArrayList<User> load(Context context, String date, String type) {

        if(type.equals(CLEANSING_NAME)){
            if(compareDate(date)){
                SharedPreferences current = context.getSharedPreferences(CURRENT_CLEANSINGLIST, Context.MODE_PRIVATE);
                SharedPreferences currentCheck = context.getSharedPreferences(date + "_" + CLEANSING_CHECK, Context.MODE_PRIVATE);

                ArrayList<User> users = new ArrayList<>();

                for(int i=0; i<current.getAll().size(); i++){
                    User user = new User();
                    user.setID(current.getString(""+i, null));
                    user.setCheck(currentCheck.getBoolean(""+i, false));
                    users.add(user);
                }
                return users;
            }

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

    public static boolean hasData(Context context, String date){
        if(compareDate(date)) {
            return true;
        }
            SharedPreferences sn = context.getSharedPreferences(date + "_" + SKINCARE_NAME, Context.MODE_PRIVATE);
            SharedPreferences cn = context.getSharedPreferences(date + "_" + CLEANSING_NAME, Context.MODE_PRIVATE);

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


    public static String getStartDate(Context context){
        SharedPreferences preferences = context.getSharedPreferences(START_DATE, Context.MODE_PRIVATE);
        return preferences.getString(START_DATE, "");
    }

    public static void setStartDate(Context context, String date){
        SharedPreferences preferences = context.getSharedPreferences(START_DATE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        if(preferences.getString(START_DATE, "").equals("")) {
            editor.putString(START_DATE, date);
            editor.commit();
        }
    }

    public static int getCheckListResultCount(Context context, String date){
        CheckListResultDBHelper dbHelper = new CheckListResultDBHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor c = db.rawQuery("SELECT count(*) from checklist_result_table where date='" + date + "'" + " AND result=1", null);
        c.moveToFirst();
        int count= c.getInt(0);
        Log.d("TEST", "date : " + date + ", cnt : " + count);
        dbHelper.close();

        return count;
    }

    public static boolean compareToDate(String from , String to){
        if(to.equals(""))
            return false;

        String[] temp = from.split("\\.");
        Calendar c1 = Calendar.getInstance();
        c1.set(Integer.parseInt(temp[0]), Integer.parseInt(temp[1]), Integer.parseInt(temp[2]));

        String[] temp2 = to.split("\\.");
        Calendar c2 = Calendar.getInstance();
        c2.set(Integer.parseInt(temp2[0]), Integer.parseInt(temp2[1]), Integer.parseInt(temp2[2]));
        if(c1.compareTo(c2) >= 0)
            return true;
        else
            return false;
    }

    public static boolean compareDate(String date){
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

    public static ArrayList<String> getBetweenDate(String from, String to){
        ArrayList<String> list = new ArrayList<>();
        String[] temp = from.split("\\.");
        String[] temp2 = to.split("\\.");

        Calendar start = Calendar.getInstance();
        start.set(Integer.parseInt(temp[0]), Integer.parseInt(temp[1]), Integer.parseInt(temp[2]));
        Calendar end = Calendar.getInstance();
        end.set(Integer.parseInt(temp2[0]), Integer.parseInt(temp2[1]), Integer.parseInt(temp2[2]));

        while(start.compareTo(end) < 0){
            list.add(calenderToString(start));
            start.add(Calendar.DAY_OF_MONTH, 1);
        }

        return list;
    }

    public static String calenderToString(Calendar c){
        return "" + c.get(Calendar.YEAR) + "." + c.get(Calendar.MONTH) + "." + c.get(Calendar.DAY_OF_MONTH);
    }

    public static void saveLastUpdateDate(Context context, String date){
        SharedPreferences preferences = context.getSharedPreferences(LAST_UPDATE_DATE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        if(!preferences.getString(LAST_UPDATE_DATE, "").equals("")) {
            editor.remove(LAST_UPDATE_DATE);
        }

        editor.putString(LAST_UPDATE_DATE, date);
        editor.commit();
    }

    public static String getLastUpdateDate(Context context){
        SharedPreferences preferences = context.getSharedPreferences(LAST_UPDATE_DATE, Context.MODE_PRIVATE);
        return preferences.getString(LAST_UPDATE_DATE, "");
    }
}
