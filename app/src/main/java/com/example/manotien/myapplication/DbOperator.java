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
    protected static final String PHOTO_TABLE_NAME = "PHOTO_TABLE";

//Attribute in LOCATION_TABLE
    protected static final String KEY_DUBS = "dubs";  //0
    protected static final String KEY_BARCODE = "barcode";  //1
    protected static final String KEY_ACCESSION = "accession";  //1
    protected static final String KEY_COLLECTOR = "collector";  //1
    protected static final String KEY_COCOLLECTOR = "addcoll";  //1
    protected static final String KEY_PREFIX = "prefix";  //1
    protected static final String KEY_NUMBER = "number";  //1
    protected static final String KEY_SUFFIX = "suffix";  //1
    protected static final String KEY_DAY = "collection_day";
    protected static final String KEY_MONTH = "collection_month";
    protected static final String KEY_YEAR = "collection_year";
    protected static final String KEY_COUNTRY = "country";
    protected static final String KEY_REGION = "florareg";
    protected static final String KEY_BKFAREACOD = "bkfareacod";
    protected static final String KEY_PROVINCE = "majorarea";
    protected static final String KEY_DISTRICT = "minorarea";
    protected static final String KEY_SUBDISTRICT = "tambon";
    protected static final String KEY_PROTECTED = "protected";
    protected static final String KEY_PLACE = "gazetteer";
    protected static final String KEY_LOCALITY_NOTE = "locality_notes";
    protected static final String KEY_HABITAT = "habitat";
    protected static final String KEY_TIMESTAMP_LOCATION = "timestamp_location";
    protected static final String  KEY_ISEXPORT = "is_export";


    //Attribute in FLORA_TABLE
    protected static final String KEY_LOCATIONID = "location_id";
    protected static final String KEY_FAMILY = "family";
    protected static final String KEY_GENUS = "genus";
    protected static final String KEY_CF = "cf";
    protected static final String KEY_SP1 = "sp1";
    protected static final String KEY_AUTHOR1 = "author1";
    protected static final String KEY_RANK1 = "rank1";
    protected static final String KEY_SP2 = "sp2";
    protected static final String KEY_AUTHOR2 = "author2";
    protected static final String KEY_RANK2 = "rank2";
    protected static final String KEY_SP3 = "sp3";
    protected static final String KEY_AUTHOR3 = "author3";
    protected static final String KEY_PLANTDESCRIPTION = "plant_description";
    protected static final String KEY_PHENOLOGY = "phenology";
    protected static final String KEY_DETBY = "detby";
    protected static final String KEY_DETDD = "detdd";
    protected static final String KEY_DETMM = "detmm";
    protected static final String KEY_DETYY = "detyy";
    protected static final String KEY_DETNOTES = "detnotes";
    protected static final String KEY_CULTIVATED = "cultivated";
    protected static final String KEY_CULTNOTES = "cultnotes";
    protected static final String KEY_NOTES= "notes";
    protected static final String KEY_LAT = "lat";
    protected static final String KEY_NS = "ns";
    protected static final String KEY_LONG = "long";
    protected static final String KEY_EW = "ew";
    protected static final String KEY_ALT = "alt";
    protected static final String KEY_ALTMAX = "altmax";
    protected static final String KEY_ALTNOTE = "altnote";
    protected static final String KEY_VERNACULAR = "vernacular";
    protected static final String KEY_LANGUAGE = "language";
    protected static final String KEY_TIMESTAMP_FLORA = "timestamp_flora";
    protected static final String KEY_NO = "no";
    //picture
    protected static final String KEY_FLORA_ID = "flora_id";
    protected static final String KEY_NAME = "name";
    protected static final String KEY_TIMESTAMP_PHOTO = "timestamp_photo";

    public static final String CREATE_PHOTO_TABLE = "create table if not exists "
            + PHOTO_TABLE_NAME
            + " ( id integer primary key autoincrement, "+ KEY_FLORA_ID +" TEXT NOT NULL, "
            + KEY_NAME + " TEXT NOT NULL, "
            + KEY_TIMESTAMP_PHOTO + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP);";

    public static final String CREATE_FIRST_TABLE = "create table if not exists "
            + FIRST_TABLE_NAME
            + " ( id integer primary key autoincrement, "+ KEY_DUBS + "  TEXT NOT NULL, " + KEY_BARCODE + " TEXT NOT NULL," + KEY_ACCESSION + " TEXT NOT NULL,"
            + KEY_COLLECTOR + " TEXT NOT NULL," + KEY_COCOLLECTOR + " TEXT NOT NULL," + KEY_PREFIX + " TEXT NOT NULL,"  + KEY_NUMBER + " TEXT NOT NULL,"
            + KEY_SUFFIX + " TEXT NOT NULL," + KEY_DAY + " TEXT NOT NULL," + KEY_MONTH + " TEXT NOT NULL,"
            + KEY_YEAR + " TEXT NOT NULL," + KEY_COUNTRY+ " TEXT NOT NULL," + KEY_REGION + " TEXT NOT NULL,"
            + KEY_BKFAREACOD + " TEXT NOT NULL," + KEY_PROVINCE+ " TEXT NOT NULL," + KEY_DISTRICT + " TEXT NOT NULL,"
            + KEY_SUBDISTRICT + " TEXT NOT NULL," + KEY_PROTECTED+ " TEXT NOT NULL," + KEY_PLACE + " TEXT NOT NULL,"
            + KEY_LOCALITY_NOTE + " TEXT NOT NULL," + KEY_HABITAT + " TEXT NOT NULL,"
            + KEY_TIMESTAMP_LOCATION + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP,"+KEY_ISEXPORT+" BOOLEAN NOT NULL"
            + ");";

    public static final String CREATE_SECOND_TABLE = "create table if not exists "
            + SECOND_TABLE_NAME
            + " ( id integer primary key autoincrement, "+KEY_LOCATIONID+ " integer ," + KEY_FAMILY + "  TEXT NOT NULL, " + KEY_GENUS + " TEXT NOT NULL," + KEY_CF + " TEXT NOT NULL,"
            + KEY_SP1 + " TEXT NOT NULL," + KEY_AUTHOR1 + " TEXT NOT NULL," + KEY_RANK1 + " TEXT NOT NULL,"  + KEY_SP2 + " TEXT NOT NULL,"
            + KEY_AUTHOR2 + " TEXT NOT NULL," + KEY_RANK2 + " TEXT NOT NULL," + KEY_SP3 + " TEXT NOT NULL,"
            + KEY_AUTHOR3 + " TEXT NOT NULL," + KEY_PLANTDESCRIPTION+ " TEXT NOT NULL," + KEY_PHENOLOGY + " TEXT NOT NULL,"
            + KEY_DETBY + " TEXT NOT NULL," + KEY_DETDD+ " TEXT NOT NULL,"
            + KEY_DETMM + " TEXT NOT NULL," + KEY_DETYY + " TEXT NOT NULL," + KEY_DETNOTES + " TEXT NOT NULL,"
            + KEY_CULTIVATED + " TEXT NOT NULL," + KEY_CULTNOTES + " TEXT NOT NULL," + KEY_NOTES + " TEXT NOT NULL,"
            + KEY_LAT + " TEXT NOT NULL," + KEY_NS + " TEXT NOT NULL," + KEY_LONG + " TEXT NOT NULL,"
            + KEY_EW + " TEXT NOT NULL," + KEY_ALT + " TEXT NOT NULL," + KEY_ALTMAX + " TEXT NOT NULL,"
            + KEY_ALTNOTE + " TEXT NOT NULL,"
            + KEY_VERNACULAR + " TEXT NOT NULL," + KEY_LANGUAGE + " TEXT NOT NULL,"+KEY_NO+" TEXT NOT NULL, "
            + KEY_TIMESTAMP_FLORA + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP"
            +");";

    public static final String CREATE_HABIT_TABLE = "create table if not exists habit_table ( id integer primary key autoincrement, name TEXT NOT NULL);";
    public static final String CREATE_FAMILY_TABLE = "create table if not exists family_table ( id integer primary key autoincrement, name TEXT NOT NULL);";
    public static final String CREATE_HABITAT_TABLE = "create table if not exists habitat_table ( id integer primary key autoincrement, name TEXT NOT NULL);";
    public static final String CREATE_GENUS_TABLE = "create table if not exists genus_table ( id integer primary key autoincrement, name TEXT NOT NULL, family_name TEXT NOT NULL);";
    public static final String CREATE_DISTRICT_TABLE = "create table if not exists district_table ( id integer primary key autoincrement, name TEXT NOT NULL, province_name TEXT NOT NULL);";
    public static final String CREATE_PROTECTED_TABLE = "create table if not exists protected_table ( id integer primary key autoincrement, name TEXT NOT NULL);";
    public static final String CREATE_COCOLLECTOR_TABLE = "create table if not exists cocollector_table ( id integer primary key autoincrement, name TEXT NOT NULL);";

    public DbOperator(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_FIRST_TABLE);
        db.execSQL(CREATE_SECOND_TABLE);
        db.execSQL(CREATE_HABIT_TABLE);
        db.execSQL(CREATE_FAMILY_TABLE);
        db.execSQL(CREATE_HABITAT_TABLE);
        db.execSQL(CREATE_GENUS_TABLE);
        db.execSQL(CREATE_PHOTO_TABLE);
        db.execSQL(CREATE_DISTRICT_TABLE);
        db.execSQL(CREATE_PROTECTED_TABLE);
        db.execSQL(CREATE_COCOLLECTOR_TABLE);
        //db.close();
    }
    public long AddPhoto(SQLiteDatabase db,String name,String flora_id){
        ContentValues contentv = new ContentValues();
        contentv.put(KEY_NAME, name);
        contentv.put(KEY_FLORA_ID, flora_id);
        return db.insert(PHOTO_TABLE_NAME, null, contentv);
    }
    public Cursor GetPhoto(SQLiteDatabase db,String flora_id){
        Cursor cursor;
        String WHERE = "flora_id = '"+flora_id+"'";
        cursor = db.query(PHOTO_TABLE_NAME,null,WHERE,null,null,null,null);
        return cursor;
    }

    public Cursor GetProtectedList(SQLiteDatabase db){
        Cursor cursor;
        String[] projection = {"name"};
        cursor = db.query("protected_table",projection,null,null,"name",null,null);
        return cursor;
    }

    public Cursor GetProvinceList(SQLiteDatabase db){
        Cursor cursor;
        String[] projection = {"province_name"};
        cursor = db.query("district_table",projection,null,null,"province_name",null,null);
        return cursor;
    }
    public Cursor GetDistrictList(SQLiteDatabase db){
        Cursor cursor;
        String[] projection = {"province_name","name"};
        cursor = db.query("district_table",projection,null,null,null,null,null);
        return cursor;
    }
    public void AddLocationInformation(String place, String protect, String locality, String habitat, String country,String region,String province,String district,String subdistrict,String collector,String cocollector,String day,String month, String year,SQLiteDatabase db) {
        ContentValues contentv = new ContentValues();
        contentv.put(KEY_DUBS,"");
        contentv.put(KEY_BARCODE,"");
        contentv.put(KEY_ACCESSION,"");
        contentv.put(KEY_PREFIX,"");
        contentv.put(KEY_SUFFIX,"");
        contentv.put(KEY_NUMBER,"");
        contentv.put(KEY_BKFAREACOD,"");
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
        contentv.put(KEY_ISEXPORT,false);
        db.insert(FIRST_TABLE_NAME, null, contentv);
    }
    public long AddFloraInformation(String lat,String longti,String alt,String altmax,String altnote,String genus, String family,String cf,String sp1,String rank1,String sp2,
                                    String rank2,String sp3,String vern,String lang,String culti,String cultnote,String pheno,String plant_des,
                                    String note,String detby,String detdd,String detmm,String detyy,String detnote,int location_id,String no,SQLiteDatabase db)
    {
        ContentValues contentv = new ContentValues();
        contentv.put(KEY_LOCATIONID,location_id);
        contentv.put(KEY_FAMILY,family);
        contentv.put(KEY_GENUS,genus);
        contentv.put(KEY_CF,cf);
        contentv.put(KEY_SP1,sp1);
        contentv.put(KEY_AUTHOR1,"");
        contentv.put(KEY_RANK1,rank1);
        contentv.put(KEY_SP2,sp2);
        contentv.put(KEY_AUTHOR2,"");
        contentv.put(KEY_RANK2,rank2);
        contentv.put(KEY_SP3,sp3);
        contentv.put(KEY_AUTHOR3,"");
        contentv.put(KEY_PLANTDESCRIPTION,plant_des);
        contentv.put(KEY_PHENOLOGY,pheno);
        contentv.put(KEY_DETBY,detby);
        contentv.put(KEY_DETDD,detdd);
        contentv.put(KEY_DETMM,detmm);
        contentv.put(KEY_DETYY,detyy);
        contentv.put(KEY_DETNOTES,detnote);
        contentv.put(KEY_CULTIVATED,culti);
        contentv.put(KEY_CULTNOTES,cultnote);
        contentv.put(KEY_NOTES,note);
        contentv.put(KEY_LAT, lat);
        contentv.put(KEY_NS,"ns");
        contentv.put(KEY_LONG,longti);
        contentv.put(KEY_EW,"ew");
        contentv.put(KEY_ALT,alt);
        contentv.put(KEY_ALTMAX,altmax);
        contentv.put(KEY_ALTNOTE, altnote);
        contentv.put(KEY_VERNACULAR, vern);
        contentv.put(KEY_LANGUAGE,lang);
        contentv.put(KEY_NO,no);
       // contentv.put(KEY_TIMESTAMP_FLORA, "a");

        return db.insert(SECOND_TABLE_NAME, null, contentv);
    }

        public Cursor GetGenusList(SQLiteDatabase db){
        Cursor cursor;
        String[] projection = {"name","family_name"};
        cursor = db.query("genus_table",projection,null,null,null,null,null);
        return cursor;
    }
    public Cursor GetFamilyList(SQLiteDatabase db){
        Cursor cursor;
        String[] projection = {"name"};
        cursor = db.query("family_table",projection,null,null,null,null,null);
        return cursor;
    }

    public void setIsExportLocation(SQLiteDatabase db,int location_id){
        ContentValues contentv = new ContentValues();
        contentv.put(KEY_ISEXPORT, true);
        String selection = "id LIKE ?";
        String []selectionArgs = {String.valueOf(location_id)};
        int a= db.update(FIRST_TABLE_NAME,contentv,selection,selectionArgs);
    }
    public Cursor GetAllLocation(SQLiteDatabase db){
        Cursor cursor;
        cursor = db.query(FIRST_TABLE_NAME,null,null,null,null,null,null);
        return cursor;
    }

    public Cursor GetAllFlora(SQLiteDatabase db,String location_id){
        Cursor cursor;
        String WHERE = "location_id = '"+location_id+"'";
        cursor = db.query(SECOND_TABLE_NAME,null,WHERE,null,null,null,null);
        return cursor;
    }

    public Cursor GetLocationInformation(SQLiteDatabase db,String placename ,String[] date){
        Cursor cursor;
        String[] projection ={"id",KEY_PLACE,KEY_PROTECTED,KEY_LOCALITY_NOTE,KEY_HABITAT,KEY_COUNTRY,KEY_REGION,KEY_PROVINCE,KEY_DISTRICT,KEY_SUBDISTRICT,KEY_COLLECTOR,KEY_COCOLLECTOR,KEY_DAY,KEY_MONTH,KEY_YEAR};
        String WHERE = "gazetteer ='"+placename+"' and collection_day ='"+date[0]+"' and collection_month ='"+date[1]+"' and collection_year='"+date[2]+"'";
        cursor = db.query(FIRST_TABLE_NAME,projection,WHERE,null,null,null,null);
        return cursor;
    }

    public void deleteLocation(SQLiteDatabase db,int location_id){
        String whereClause = "id = ?";
        String[] whereArgs = { String.valueOf(location_id) };

        String[] projection = {"id"};
        String WHERE = "location_id ="+String.valueOf(location_id);
        String orderby = KEY_TIMESTAMP_FLORA+" DESC";
        Cursor cursor =  db.query(SECOND_TABLE_NAME,projection,WHERE,null,null,null,orderby);
        if(cursor.moveToFirst()){
            do {
                deleteFlora(db,cursor.getInt(0));
            } while (cursor.moveToNext());
        }
        db.delete(FIRST_TABLE_NAME,whereClause,whereArgs);
    }

    public void deleteFlora(SQLiteDatabase db,int flora_id){
        String whereClause = "id = ?";
        String[] whereArgs = { String.valueOf(flora_id) };
        db.delete(SECOND_TABLE_NAME,whereClause,whereArgs);
    }


    public Cursor GetLocationListView(SQLiteDatabase db){
        Cursor cursor;
        String[] projection = {"id",KEY_PLACE,KEY_PROTECTED,KEY_DAY,KEY_MONTH,KEY_YEAR,KEY_COLLECTOR,KEY_ISEXPORT};
        String orderby = KEY_TIMESTAMP_LOCATION+" DESC";
        cursor = db.query(FIRST_TABLE_NAME,projection,null,null,null,null,orderby);
        return cursor;
    }
    public Cursor GetFloraForListView(SQLiteDatabase db,Integer location_id){
        Cursor cursor;
        String[] projection = {"id",KEY_LOCATIONID,KEY_FAMILY,KEY_GENUS,KEY_SP1,KEY_LAT,KEY_LONG};
        String WHERE = "location_id ="+String.valueOf(location_id);
        String orderby = KEY_TIMESTAMP_FLORA+" DESC";
        cursor = db.query(SECOND_TABLE_NAME,projection,WHERE,null,null,null,orderby);
        return cursor;
    }

    public Cursor GetFloraInformation(SQLiteDatabase db,Integer flora_id){
        Cursor cursor;
        String[] projection = {"id",KEY_FAMILY ,KEY_GENUS ,KEY_CF ,KEY_SP1 ,KEY_AUTHOR1 ,KEY_RANK1 ,KEY_SP2 ,KEY_AUTHOR2,KEY_RANK2 ,KEY_SP3 ,
                KEY_AUTHOR3 ,KEY_PLANTDESCRIPTION ,KEY_PHENOLOGY ,KEY_DETBY ,KEY_DETDD ,KEY_DETMM ,KEY_DETYY ,KEY_DETNOTES ,KEY_CULTIVATED,
                KEY_CULTNOTES ,KEY_NOTES ,KEY_LAT ,KEY_NS ,KEY_LONG ,KEY_EW ,KEY_ALT ,KEY_ALTMAX ,KEY_ALTNOTE ,KEY_VERNACULAR ,KEY_LANGUAGE};
        String WHERE = "id ="+String.valueOf(flora_id);
        cursor = db.query(SECOND_TABLE_NAME,projection,WHERE,null,null,null,null);
        return cursor;
    }
    public Cursor GetHabitatList(SQLiteDatabase db){
        Cursor cursor;
        String[] projection = {"name"};
        cursor = db.query("habitat_table",projection,null,null,null,null,null);
        return cursor;
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //THIS WILL BE EXECUTED WHEN YOU UPDATED VERSION OF DATABASE_VERSION
        //YOUR DROP AND CREATE QUERIES
    }

}
