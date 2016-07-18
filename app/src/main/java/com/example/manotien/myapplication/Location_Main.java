package com.example.manotien.myapplication;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.manotien.myapplication.DbOperator;
import com.example.manotien.myapplication.LocationRecycleAdapter;
import com.example.manotien.myapplication.Main2Activity;
import com.example.manotien.myapplication.R;

import java.util.ArrayList;
import java.util.List;

import static android.database.DatabaseUtils.dumpCursorToString;

public class Location_Main extends AppCompatActivity {

    DbOperator dbOperator;
    SQLiteDatabase sqLiteDatabase;
    Cursor cursor;
    Context context;
    RecyclerView recyclerView;
    int MY_PERMISSIONS_REQUEST_FINE_LOCATION = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location__main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Location List");

        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(Location_Main.this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
            } else {
                ActivityCompat.requestPermissions(Location_Main.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_FINE_LOCATION);
            }
        }


        Button button = (Button)findViewById(R.id.addlocation);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Location_Main.this, Main2Activity.class));
            }
        });

        recyclerView= (RecyclerView) findViewById(R.id.location_recycler_view);

        LocationRecycleAdapter adapter=new LocationRecycleAdapter(this,createList());
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private List<LocationData> createList() {

        List<LocationData> result = new ArrayList<LocationData>();
        dbOperator = new DbOperator(getApplicationContext());
        sqLiteDatabase = dbOperator.getReadableDatabase();
        cursor = dbOperator.GetLocationListView(sqLiteDatabase);
        if(cursor.moveToFirst()){
            do {
                LocationData ld = new LocationData();
                ld.place = cursor.getString(1);
                ld.protect = cursor.getString(2);
                ld.date =  cursor.getString(3)+"/"+cursor.getString(4)+"/"+cursor.getString(5);
                ld.location_id = cursor.getInt(0);
                ld.collector = "By: "+cursor.getString(6);
                result.add(ld);
            } while (cursor.moveToNext());

            cursor.close();
        }

        return result;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                /*HERE PERMISSION IS ALLOWED.
                * YOU SHOULD CODE HERE*/
                } else {

                    Toast.makeText(Location_Main.this, "Permission deny to write external storag", Toast.LENGTH_SHORT).show();
                }
                return;
            }
            case 2: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                /*HERE PERMISSION IS ALLOWED.
                * YOU SHOULD CODE HERE*/
                } else {

                    Toast.makeText(Location_Main.this, "Permission deny to request fine location", Toast.LENGTH_SHORT).show();
                }
                return;
            }
            case 3: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                /*HERE PERMISSION IS ALLOWED.
                * YOU SHOULD CODE HERE*/
                } else {

                    Toast.makeText(Location_Main.this, "Permission deny to request internet", Toast.LENGTH_SHORT).show();
                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
}
