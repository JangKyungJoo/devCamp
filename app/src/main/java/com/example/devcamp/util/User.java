package com.example.devcamp.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.devcamp.Entity.CleansingList;
import com.example.devcamp.Entity.SkincareList;

import java.util.ArrayList;
import java.util.Calendar;

public class User {

    public static final String LAST_UPDATE_DATE = "last_update_date";
    public static final String START_DATE = "start_date";

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

    public static int[] getCheckListResultCount(Context context, String date){
        int count[] = new int[2];
        CheckListResultDBHelper dbHelper = new CheckListResultDBHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor c1 = db.rawQuery("SELECT count(*) from checklist_result_table where date='" + date + "'" + " AND result=1", null);
        c1.moveToFirst();
        count[0] = c1.getInt(0);

        Cursor c2 = db.rawQuery("SELECT count(*) from checklist_result_table where date='" + date + "'" + " AND result=0", null);
        c2.moveToFirst();
        count[1] = c2.getInt(0);

        dbHelper.close();

        return count;
    }

    public static boolean compareToDate(String from , String to){
        if(!to.equals("")) {
            String[] temp = from.split("\\.");
            Calendar c1 = Calendar.getInstance();
            c1.set(Integer.parseInt(temp[0]), Integer.parseInt(temp[1]), Integer.parseInt(temp[2]));

            String[] temp2 = to.split("\\.");
            Calendar c2 = Calendar.getInstance();
            c2.set(Integer.parseInt(temp2[0]), Integer.parseInt(temp2[1]), Integer.parseInt(temp2[2]));
            if (c1.compareTo(c2) >= 0)
                return true;
            else
                return false;
        }
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

    public static ArrayList<CleansingList> getCleansingList(Context context) {
        CleansingListDBHelper dbHelper = new CleansingListDBHelper(context);
        ArrayList<CleansingList> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(CleansingList.TABLE_NAME, null, null, null, null, null, null, null);

        while(cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String item = cursor.getString(1);
            CleansingList cleansingList = new CleansingList(id, item);
            list.add(cleansingList);
        }

        cursor.close();
        dbHelper.close();

        return list;
    }

    public static ArrayList<SkincareList> getSkincareList(Context context) {
        SkincareListDBHelper dbHelper = new SkincareListDBHelper(context);
        ArrayList<SkincareList> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(SkincareList.TABLE_NAME, null, null, null, null, null, null, null);

        while(cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String item = cursor.getString(1);
            SkincareList skincareList = new SkincareList(id, item);
            list.add(skincareList);
        }

        cursor.close();
        dbHelper.close();

        return list;
    }
}
