package com.example.devcamp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.devcamp.Entity.CheckListResult.TABLE_NAME;


public class CheckListResultDBHelper extends SQLiteOpenHelper{

    public CheckListResultDBHelper(Context context) {
        super(context, "result_db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String createTable = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (_id integer primary key autoincrement, item text, date text, type int, result int);";
        sqLiteDatabase.execSQL(createTable);

    }

    public void update(String _query){
        SQLiteDatabase db = getReadableDatabase();
        db.execSQL(_query);
        db.close();
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
