package com.example.devcamp.alarm;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class AlarmDBHelper  extends SQLiteOpenHelper {

    public static final String DB_NAME = "alarm_db";
    public static final String TABLE_NAME = "alarm_table";


    public AlarmDBHelper(Context context) {
//        데이터베이스 파일 생성
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

//        데이터베이스 테이블 생성
        String createTable = "create table " + TABLE_NAME + " ( _id integer primary key autoincrement, time text, sunday INTEGER, " +
                "monday INTEGER, tuesday INTEGER, wednesday INTEGER, thursday INTEGER, friday INTEGER, saturday INTEGER, cancel INTEGER, memo text, ringtone text);";
        sqLiteDatabase.execSQL(createTable);

//        샘플 데이터 추가
//        sqLiteDatabase.execSQL("insert into " + TABLE_NAME + " values (null, '책1', '작가1', '출판사1', '내용요약1');");


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