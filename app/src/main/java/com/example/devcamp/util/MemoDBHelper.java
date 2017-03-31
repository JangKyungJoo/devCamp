package com.example.devcamp.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.devcamp.Entity.Memo;

public class MemoDBHelper extends SQLiteOpenHelper{

    public MemoDBHelper(Context context) {
        super(context, "memo_db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String createTable = "create table IF NOT EXISTS " + Memo.TABLE_NAME + " ( _id integer primary key autoincrement, memo text, date text);";
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
