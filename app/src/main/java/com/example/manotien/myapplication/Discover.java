package com.example.manotien.myapplication;


import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import android.location.Location;
import android.widget.Toast;

public class Discover extends AppCompatActivity  {

    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private static final int ACTIVITY_START_CAMERA_APP = 0;
    private ImageView mPhotoCapturedImageView;

    GetLocation gps;
    Context context = this;
    DbOperator dbOperator;
    SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discover);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //buildGoogleApiClient();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mPhotoCapturedImageView = (ImageView) findViewById(R.id.photoview);

        Intent intent = getIntent();
        final String ans_province = intent.getStringExtra("ans_province");
        final String ans_place = intent.getStringExtra("ans_place");


        Button buttonsubmit = (Button) findViewById(R.id.submit);
        buttonsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText name = (EditText)findViewById(R.id.name);
                TextView longti = (TextView) findViewById(R.id.longti);
                TextView lati = (TextView) findViewById(R.id.lat);

                dbOperator = new DbOperator(context);
                sqLiteDatabase = dbOperator.getWritableDatabase();
                dbOperator.AddSurveyInformation(ans_place, ans_province, "district name", "subdistrict name", name.getText().toString(), lati.getText().toString(), longti.getText().toString(), sqLiteDatabase);
                dbOperator.close();

                Intent intent = new Intent(Discover.this, Survey_Main.class);
                startActivity(intent);
            }
        });
        Button buttongps = (Button) findViewById(R.id.getgps);
        buttongps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                gps=new GetLocation(Discover.this);
                if(gps.canGetLocation()){
                    TextView longti = (TextView) findViewById(R.id.longti);
                    TextView lati = (TextView) findViewById(R.id.lat);
                    lati.setText(Double.toString(gps.getLat()));
                    longti.setText(Double.toString(gps.getLng()));
                    gps.stopUsingGPS();
                }
            }
        });


    }
    /*
    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    protected synchronized void buildGoogleApiClient()
    {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.d("Connect","I'm connnected!");
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);


        Button buttongps = (Button) findViewById(R.id.getgps);
        buttongps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mLastLocation != null) {
                    TextView longti = (TextView) findViewById(R.id.longti);
                    TextView lati = (TextView) findViewById(R.id.lat);
                    lati.setText(String.valueOf(mLastLocation.getLatitude()));
                    longti.setText(String.valueOf(mLastLocation.getLongitude()));

                }
            }
        });
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
*/

    public void clicktakephoto(View view){
        Intent callCameraApplicationIntent = new Intent();
        callCameraApplicationIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(callCameraApplicationIntent, ACTIVITY_START_CAMERA_APP);

    }

    protected void onActivityResult(int requestCode,int resultCode, Intent data){
        if(requestCode == ACTIVITY_START_CAMERA_APP && resultCode == RESULT_OK){
            Toast.makeText(this,"เรียบร้อย", Toast.LENGTH_SHORT).show();
            Bundle extras = data.getExtras();
            Bitmap photoCapturedBitmap = (Bitmap) extras.get("data");
            mPhotoCapturedImageView.setImageBitmap(photoCapturedBitmap);
        }
    }
}
