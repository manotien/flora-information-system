package com.example.manotien.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DbOperator extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "FLORA";
    protected static final String FIRST_TABLE_NAME = "FIRST_TABLE";
    protected static final String SECOND_TABLE_NAME = "SECOND_TABLE";


    protected static final String KEY_PLACE = "place";
    protected static final String KEY_PROVINCE = "province";
    protected static final String KEY_DISTRICT = "district";
    protected static final String KEY_SUBDISTRICT = "subdistrict";
    protected static final String KEY_NAME = "name";
    protected static final String KEY_LATITUDE = "latitude";
    protected static final String KEY_LONGTITUDE = "longtitude";


    public static final String CREATE_FIRST_TABLE = "create table if not exists "
            + FIRST_TABLE_NAME
            + " ( _id integer primary key autoincrement, " + KEY_PLACE + "  TEXT NOT NULL, " + KEY_PROVINCE + " TEXT NOT NULL," + KEY_DISTRICT +
            " TEXT NOT NULL," + KEY_SUBDISTRICT + " TEXT NOT NULL," + KEY_NAME + " TEXT NOT NULL," + KEY_LATITUDE + " TEXT NOT NULL," + KEY_LONGTITUDE + " TEXT NOT NULL"
            + ");";


    public DbOperator(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_FIRST_TABLE);
        //db.close();
    }

    public void AddSurveyInformation(String place, String province, String district, String subdistrict, String name,String latitude,String longtitude,SQLiteDatabase db) {
        ContentValues contentv = new ContentValues();
        contentv.put(KEY_PLACE, place);
        contentv.put(KEY_PROVINCE, province);
        contentv.put(KEY_DISTRICT, district);
        contentv.put(KEY_SUBDISTRICT, subdistrict);
        contentv.put(KEY_NAME,name);
        contentv.put(KEY_LATITUDE,latitude);
        contentv.put(KEY_LONGTITUDE,longtitude);
        db.insert(FIRST_TABLE_NAME, null, contentv);
    }

    public Cursor GetSurveyInformation(SQLiteDatabase db){
        Cursor cursor;
        String[] projection ={KEY_NAME,KEY_LATITUDE,KEY_LONGTITUDE};
        cursor = db.query(FIRST_TABLE_NAME,projection,null,null,null,null,null);
        return cursor;
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //THIS WILL BE EXECUTED WHEN YOU UPDATED VERSION OF DATABASE_VERSION
        //YOUR DROP AND CREATE QUERIES
    }

}
