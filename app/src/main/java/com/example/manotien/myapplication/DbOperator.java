package com.example.manotien.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;


public class DbOperator extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "FLORA";
    protected static final String FIRST_TABLE_NAME = "LOCATION_TABLE";
    protected static final String SECOND_TABLE_NAME = "FLORA_TABLE";

//Attribute in LOCATION_TABLE
    protected static final String KEY_PLACE = "place";
    protected static final String KEY_PROTECTED = "protected";
    protected static final String KEY_LOCALITY_NOTE = "locality_note";
    protected static final String KEY_HABITAT = "habitat";
    protected static final String KEY_COUNTRY = "country";
    protected static final String KEY_REGION = "region";
    protected static final String KEY_PROVINCE = "province";
    protected static final String KEY_DISTRICT = "district";
    protected static final String KEY_SUBDISTRICT = "subdistrict";
    protected static final String KEY_COLLECTOR = "collector";
    protected static final String KEY_COCOLLECTOR = "cocollector";
    protected static final String KEY_DAY = "day";
    protected static final String KEY_MONTH = "month";
    protected static final String KEY_YEAR = "year";


//Attribute in FLORA_TABLE
    protected static final String KEY_DUBS = "dubs";


    public static final String CREATE_FIRST_TABLE = "create table if not exists "
            + FIRST_TABLE_NAME
            + " ( id integer primary key autoincrement, " + KEY_PLACE + "  TEXT NOT NULL, " + KEY_PROTECTED + " TEXT NOT NULL," + KEY_LOCALITY_NOTE + " TEXT NOT NULL,"
            + KEY_HABITAT + " TEXT NOT NULL," + KEY_COUNTRY + " TEXT NOT NULL," + KEY_REGION + " TEXT NOT NULL,"  + KEY_PROVINCE + " TEXT NOT NULL,"
            + KEY_DISTRICT + " TEXT NOT NULL," + KEY_SUBDISTRICT + " TEXT NOT NULL," + KEY_COLLECTOR + " TEXT NOT NULL,"
            + KEY_COCOLLECTOR + " TEXT NOT NULL," + KEY_DAY+ " TEXT NOT NULL," + KEY_MONTH + " TEXT NOT NULL,"
            + KEY_YEAR + " TEXT NOT NULL);";


    public static final String CREATE_SECOND_TABLE ="create table if not exists";

    public DbOperator(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_FIRST_TABLE);

        //db.close();
    }

    public void AddLocationInformation(String place, String protect, String locality, String habitat, String country,String region,String province,String district,String subdistrict,String collector,String cocollector,String day,String month, String year,SQLiteDatabase db) {
        ContentValues contentv = new ContentValues();
        contentv.put(KEY_PLACE, place);
        contentv.put(KEY_PROTECTED,protect);
        contentv.put(KEY_LOCALITY_NOTE,locality);
        contentv.put(KEY_HABITAT,habitat);
        contentv.put(KEY_COUNTRY,country);
        contentv.put(KEY_REGION,region);
        contentv.put(KEY_PROVINCE, province);
        contentv.put(KEY_DISTRICT, district);
        contentv.put(KEY_SUBDISTRICT, subdistrict);
        contentv.put(KEY_COLLECTOR,collector);
        contentv.put(KEY_COCOLLECTOR,cocollector);
        contentv.put(KEY_DAY,day);
        contentv.put(KEY_MONTH,month);
        contentv.put(KEY_YEAR, year);
        db.insert(FIRST_TABLE_NAME, null, contentv);
    }

    public Cursor GetLocationInformation(SQLiteDatabase db,String placename ,String[] date){
        Cursor cursor;
        String[] projection ={"id",KEY_PLACE,KEY_PROTECTED,KEY_LOCALITY_NOTE,KEY_HABITAT,KEY_COUNTRY,KEY_REGION,KEY_PROVINCE,KEY_DISTRICT,KEY_SUBDISTRICT,KEY_COLLECTOR,KEY_COCOLLECTOR,KEY_DAY,KEY_MONTH,KEY_YEAR};
        String WHERE = "place ='"+placename+"' and day ='"+date[0]+"' and month ='"+date[1]+"' and year='"+date[2]+"'";
        cursor = db.query(FIRST_TABLE_NAME,projection,WHERE,null,null,null,null);
        return cursor;

    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //THIS WILL BE EXECUTED WHEN YOU UPDATED VERSION OF DATABASE_VERSION
        //YOUR DROP AND CREATE QUERIES
    }

}
