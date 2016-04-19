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
import java.io.FileNotFoundException;
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

    TextView lat,longE,alt,altnote,genus,family,sp1,sp2,sp3,vernname,cultivate, cultivatenote, phenology, plantdescription,detby, detdate, detnote, note;
    String latS,longS,altS,altnoteS,genusS,familyS,sp1S,sp2S,sp3S,vernnameS,cultivateS, cultivatenoteS, phenologyS,plantdescriptionS, detbyS, detdateS, detnoteS, noteS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flora__information);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        int flora_id = getIntent().getExtras().getInt("flora_id");

        lat = (TextView)findViewById(R.id.lat);
        longE = (TextView)findViewById(R.id.longi);
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
        cursor = dbOperator.GetFloraInformation(sqLiteDatabase, flora_id);
        if(cursor.moveToFirst()){
            do {
                /*{"id",KEY_FAMILY ,KEY_GENUS ,KEY_CF ,KEY_SP1 ,KEY_AUTHOR1 ,KEY_RANK1 ,KEY_SP2 ,KEY_AUTHOR2,KEY_RANK2 ,
                KEY_SP3 , KEY_AUTHOR3 ,KEY_PLANTDESCRIPTION ,KEY_PHENOLOGY ,KEY_DETBY ,KEY_DETDD ,KEY_DETMM ,KEY_DETYY ,KEY_DETNOTES ,KEY_CULTIVATED,
                KEY_CULTNOTES ,KEY_NOTES ,KEY_LAT ,KEY_NS ,KEY_LONG ,KEY_EW ,KEY_ALT ,KEY_ALTMAX ,KEY_ALTNOTE ,KEY_VERNACULAR ,
                KEY_LANGUAGE};*/
                if (cursor.getString(22).equals(""))
                    latS = "Latitude: -";
                else
                    latS = "Latitude: " + cursor.getString(22);

                if (!cursor.getString(24).equals(""))
                    longS = "Longitude: " + cursor.getString(24);
                else
                    longS = "Longitude: -";

                if (!cursor.getString(27).equals(""))
                    altS = "Alt: " + cursor.getString(26) + "    Alt. Max: " + cursor.getString(27);
                else
                    altS = "Alt: " + cursor.getString(26);

                if (!cursor.getString(8).equals(""))
                    altnoteS = "AltNote: " + cursor.getString(8);
                else
                    altnoteS = "AltNote: -";

                if (!cursor.getString(2).equals(""))
                    genusS = "Genus: " + cursor.getString(2);
                else
                    genusS = "Genus: -";

                if (!cursor.getString(1).equals(""))
                    familyS = "Family: " + cursor.getString(1);
                else
                    familyS = "Family: -";

                if (!cursor.getString(4).equals(""))
                    sp1S = "SP1: " + cursor.getString(4) + "    Rank1: " + cursor.getString(6);
                else
                    sp1S = "SP1: " + cursor.getString(4);

                if (!cursor.getString(7).equals(""))
                    sp2S = "SP2: " + cursor.getString(7) + "    Rank2: " + cursor.getString(9);
                else
                    sp3S = "SP3: -";

                if (!cursor.getString(10).equals(""))
                    sp3S = "SP3: " + cursor.getString(10);
                else
                    sp3S = "SP3: -";

                if (!cursor.getString(29).equals(""))
                    vernnameS = "Vernacular Name: " + cursor.getString(29);
                else
                    vernnameS = "Vernacular Name: -";

                if (!cursor.getString(19).equals(""))
                    cultivateS = "Cultivate: " + cursor.getString(19);
                else
                    cultivateS = "Cultivate: -";

                if (!cursor.getString(21).equals(""))
                    cultivatenoteS = "Cultivate Note: " + cursor.getString(21);
                else
                    cultivatenoteS = "Cultivate Note: -";

                if (!cursor.getString(13).equals(""))
                    phenologyS = "Phenology: " + cursor.getString(13);
                else
                    phenologyS = "Phenology: -";

                if (!cursor.getString(12).equals(""))
                    plantdescriptionS = "Plant Description: " + cursor.getString(12);
                else
                    plantdescriptionS = "Plant Description: -";

                if (!cursor.getString(14).equals(""))
                    detbyS = "Det. By: " + cursor.getString(14);
                else
                    detbyS = "Det. By: -";

                if (!cursor.getString(15).equals(""))
                    detdateS = "Det. Date: " + cursor.getString(15) + "/" + cursor.getString(16) + "/" + cursor.getString(17);
                else
                    detdateS = "Det. Date: -";

                if (!cursor.getString(18).equals(""))
                    detnoteS = "Det. By: " + cursor.getString(18);
                else
                    detnoteS = "Det. By: -";

                if (!cursor.getString(21).equals(""))
                    noteS = "Note: " + cursor.getString(21);
                else
                    noteS = "Note: -";

                lat.setText(latS);
                longE.setText(longS);
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


                //get Photo
                //get flora photo
                Cursor cursor_photo;


                LinearLayout layout = (LinearLayout) findViewById(R.id.linear);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(500, 600);

                dbOperator = new DbOperator(this);
                sqLiteDatabase = dbOperator.getReadableDatabase();
                cursor_photo = dbOperator.GetPhoto(sqLiteDatabase, String.valueOf(cursor.getString(0)));
                if (cursor_photo.moveToFirst()) {
                    do {
                        Bitmap bitmap = getPhotoByUri(Uri.parse(cursor_photo.getString(2)));

                        ImageView imageView = new ImageView(this);
                        imageView.setId(i);
                        imageView.setPadding(2, 2, 20, 2);
                        imageView.setImageBitmap(bitmap);
                        imageView.setLayoutParams(layoutParams);
                        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                        i++;
                        layout.addView(imageView);

                    } while (cursor_photo.moveToNext());
                }
                cursor_photo.close();
            } while (cursor.moveToNext());

            cursor.close();
        }



        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public Bitmap getPhotoByUri(Uri uri){
        final BitmapFactory.Options options = new BitmapFactory.Options();

        Bitmap bitmap=null;
        options.inJustDecodeBounds = true;
        try {
            BitmapFactory.decodeStream(this.getContentResolver().openInputStream(uri), null, options);
        } catch (FileNotFoundException e) {
            Log.d("test", "Error"+String.valueOf(e));
            e.printStackTrace();
        }
        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options,400, 500);
        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        try {
            bitmap = BitmapFactory.decodeStream(this.getContentResolver().openInputStream(uri), null, options);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return bitmap;
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
