package com.example.manotien.myapplication;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Flora_Information extends AppCompatActivity {

    DbOperator dbOperator;
    SQLiteDatabase sqLiteDatabase;
    Cursor cursor;
    Context context;
    private static final int ACTIVITY_START_CAMERA_APP = 0;
    int i=0;
    Uri uri;

    TextView latlong,alt,altnote,genus,family,sp1,sp2,sp3,vernname,cultivate, cultivatenote, phenology, plantdescription,detby, detdate, detnote, note;
    String latlongS,altS,altnoteS,genusS,familyS,sp1S,sp2S,sp3S,vernnameS,cultivateS, cultivatenoteS, phenologyS,plantdescriptionS, detbyS, detdateS, detnoteS, noteS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flora__information);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        int flora_id = getIntent().getExtras().getInt("flora_id");

        latlong = (TextView)findViewById(R.id.latlong);
        alt = (TextView)findViewById(R.id.alt);
        altnote = (TextView)findViewById(R.id.altnote);
        genus = (TextView)findViewById(R.id.genus);
        family = (TextView)findViewById(R.id.family);
        sp1 = (TextView)findViewById(R.id.sp1);
        sp2 = (TextView)findViewById(R.id.sp2);
        sp3 = (TextView)findViewById(R.id.sp3);
        vernname = (TextView)findViewById(R.id.vernname);
        cultivate = (TextView)findViewById(R.id.cultivate);
        cultivatenote = (TextView)findViewById(R.id.cultivatenote);
        phenology = (TextView)findViewById(R.id.phenology);
        plantdescription = (TextView)findViewById(R.id.plantdescription);
        detby = (TextView)findViewById(R.id.detby);
        detdate = (TextView)findViewById(R.id.detdate);
        detnote = (TextView)findViewById(R.id.detnote);
        note = (TextView)findViewById(R.id.note);

        dbOperator = new DbOperator(getApplicationContext());
        sqLiteDatabase = dbOperator.getReadableDatabase();
        cursor = dbOperator.GetFloraInformation(sqLiteDatabase,flora_id);
        if(cursor.moveToFirst()){
            do {
                /*{"id",KEY_FAMILY ,KEY_GENUS ,KEY_CF ,KEY_SP1 ,KEY_AUTHOR1 ,KEY_RANK1 ,KEY_SP2 ,KEY_AUTHOR2,KEY_RANK2 ,
                KEY_SP3 , KEY_AUTHOR3 ,KEY_PLANTDESCRIPTION ,KEY_PHENOLOGY ,KEY_DETBY ,KEY_DETDD ,KEY_DETMM ,KEY_DETYY ,KEY_DETNOTES ,KEY_CULTIVATED,
                KEY_CULTNOTES ,KEY_NOTES ,KEY_LAT ,KEY_NS ,KEY_LONG ,KEY_EW ,KEY_ALT ,KEY_ALTMAX ,KEY_ALTNOTE ,KEY_VERNACULAR ,
                KEY_LANGUAGE};*/
                latlongS = "Latitude: "+cursor.getString(22)+"  Longitude: "+cursor.getString(24);
                altS = "Alt: "+cursor.getString(26)+"    Alt. Max: "+cursor.getString(27);
                altnoteS = "AltNote: "+cursor.getString(8);
                genusS = "Genus: "+cursor.getString(2);
                familyS = "Family: "+cursor.getString(1);
                sp1S = "SP1: "+cursor.getString(4)+"    Rank1: "+cursor.getString(6);
                sp2S = "SP2: "+cursor.getString(7)+"    Rank2: "+cursor.getString(9);
                sp3S = "SP3: "+cursor.getString(10);
                vernnameS = "Vern. Name: "+cursor.getString(29);
                cultivateS = "Cultivate: "+cursor.getString(19);
                cultivatenoteS = "Cultivate Note: "+cursor.getString(21);
                phenologyS ="Phenology: "+cursor.getString(13);
                plantdescriptionS = "Plant Description: "+cursor.getString(12);
                detbyS= "Det. By: "+cursor.getString(14);
                detdateS = "Det. Date: "+cursor.getString(15)+"/"+cursor.getString(16)+"/"+cursor.getString(17);
                detnoteS = "Det. By: "+cursor.getString(18);
                noteS = "Note: "+cursor.getString(21);

                latlong.setText(latlongS);
                alt.setText(altS);
                altnote.setText(altnoteS);
                genus.setText(genusS);
                family.setText(familyS);
                sp1.setText(sp1S);
                sp2.setText(sp2S);
                sp3.setText(sp3S);
                vernname.setText(vernnameS);
                cultivate.setText(cultivateS);
                cultivatenote.setText(cultivatenoteS);
                phenology.setText(phenologyS);
                plantdescription.setText(plantdescriptionS);
                detby.setText(detbyS);
                detdate.setText(detdateS);
                detnote.setText(detnoteS);
                note.setText(noteS);

            } while (cursor.moveToNext());

            cursor.close();
        }

        //get flora photo


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }



    public void getFloraPhoto(Uri uri){
        getContentResolver().notifyChange(uri, null);
        ContentResolver cr = getContentResolver();
        try {
//////////////////////
            // First decode with inJustDecodeBounds=true to check dimensions
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(getContentResolver().openInputStream(uri), null, options);
            // Calculate inSampleSize
            options.inSampleSize = calculateInSampleSize(options, 500, 600);
            // Decode bitmap with inSampleSize set
            options.inJustDecodeBounds = false;
            Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri), null, options);
/////////////////////

            Drawable d = new BitmapDrawable(getResources(), bitmap);
            LinearLayout layout = (LinearLayout) findViewById(R.id.linear);
            ImageView imageView = new ImageView(this);
            imageView.setId(i);

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(500, 600);

            imageView.setPadding(2, 2, 20, 2);
            imageView.setImageDrawable(d);
            imageView.setLayoutParams(layoutParams);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            i++;
            layout.addView(imageView);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            if (width > height) {
                inSampleSize = Math.round((float)height / (float)reqHeight);
            } else {
                inSampleSize = Math.round((float)width / (float)reqWidth);
            }
        }
        return inSampleSize;
    }

}
