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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flora__information);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        int flora_id = getIntent().getExtras().getInt("flora_id");

        dbOperator = new DbOperator(getApplicationContext());
        sqLiteDatabase = dbOperator.getReadableDatabase();
        cursor = dbOperator.GetFloraInformation(sqLiteDatabase,flora_id);
        if(cursor.moveToFirst()){
            do {
                //cursor.getString(1);

            } while (cursor.moveToNext());

            cursor.close();
        }


        Button photo = (Button) findViewById(R.id.takephoto);
        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                String imageFileName = "IMG_" + timeStamp + ".jpg";
                File f = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "Flora/"+ imageFileName);

                uri = Uri.fromFile(f);

                Log.d("poppa",String.valueOf(f));
                Intent callCameraApplicationIntent = new Intent();
                callCameraApplicationIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                callCameraApplicationIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                startActivityForResult(callCameraApplicationIntent, ACTIVITY_START_CAMERA_APP);
            }
        });



        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data){

        if (requestCode == ACTIVITY_START_CAMERA_APP && resultCode == RESULT_OK){
            getContentResolver().notifyChange(uri, null);
            ContentResolver cr = getContentResolver();
            Toast.makeText(getApplicationContext(), "เรียบร้อย", Toast.LENGTH_SHORT).show();


            //Bitmap photoCapturedBitmap = (Bitmap) data.getExtras().get("data");
            try {
                Log.d("poppa", String.valueOf(uri));
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
