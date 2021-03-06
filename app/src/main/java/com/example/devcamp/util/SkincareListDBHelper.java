package com.example.devcamp.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.devcamp.entity.SkincareList.TABLE_NAME;

public class SkincareListDBHelper extends SQLiteOpenHelper{

    public SkincareListDBHelper(Context context) {
        super(context, "skin_db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String createTable = "create table IF NOT EXISTS " + TABLE_NAME + " ( _id integer primary key autoincrement, item text);";
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
