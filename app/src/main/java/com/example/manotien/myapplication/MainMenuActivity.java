package com.example.manotien.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.opencsv.CSVWriter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainMenuActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Cursor cursor;
    DbOperator dbOperator;
    SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainmenu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //check database

        if(doesDatabaseExist(getApplicationContext(),"FLORA"))
        {
            Log.d("checkaa","hi");
        }
        else
        {
            dbOperator = new DbOperator(getApplicationContext());
            sqLiteDatabase = dbOperator.getWritableDatabase();
            InitialData init =new InitialData();
            init.initial(sqLiteDatabase);
            Log.d("checkaa","bye");
            dbOperator.close();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        setTitle("Main Menu");
//
        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }


        //start
        ImageButton button = (ImageButton)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainMenuActivity.this, Location_Main.class));
            }
        });

        //see flora data
        /*
        Button test = (Button)findViewById(R.id.information);
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainMenuActivity.this, tab_discover.class));
            }
        });
*/
        //
        File flora_photo = new File(Environment.getExternalStorageDirectory(), "DCIM/Flora");

        if (!flora_photo.exists()) {
            flora_photo.mkdirs();
        }

        //export database
        ImageButton button1 = (ImageButton)findViewById(R.id.export);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainMenuActivity.this, Export_Data.class));

            }
        });

        //upload data
        ImageButton upload = (ImageButton)findViewById(R.id.upload);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor;
                String url = "http://192.168.1.2:8080/flora/create/location";
                String flora_url = "http://192.168.1.2:8080/flora/create/flora";
                String flora_response= "[]";

                OkHttpClient client = new OkHttpClient();
                dbOperator = new DbOperator(getApplicationContext());
                sqLiteDatabase = dbOperator.getReadableDatabase();
                cursor = dbOperator.GetAllLocation(sqLiteDatabase);
                if(cursor.moveToFirst()){
                    String[] columnNames = cursor.getColumnNames();
                    do {
                        JSONObject locationJSON = new JSONObject();
                        for(int i=1;i<columnNames.length;i++)
                        {
                            try {
                                locationJSON.put(columnNames[i], cursor.getString(i));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        RequestBody formBody = new FormBody.Builder()
                                .add("location", String.valueOf(locationJSON) )
                                .build();
                        Request request = new Request.Builder()
                                .url(url)
                                .post(formBody)
                                .build();
                        try {
                            Response response = client.newCall(request).execute();
                            String body = response.body().string();

                            sendPostLocation post = new sendPostLocation();

                            try {
                                flora_response = post.send(getApplicationContext(),flora_url,body,cursor.getString(0));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            // Do something with the response.
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    } while (cursor.moveToNext());

                    cursor.close();
                }
                Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();

            }
        });
    }
    private static boolean doesDatabaseExist(Context context, String dbName) {
        File dbFile = context.getDatabasePath(dbName);
        return dbFile.exists();
    }




    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_main) {
            // Handle the camera action
        } else if (id == R.id.nav_info) {

        } else if (id == R.id.nav_database) {

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private static final MediaType MEDIA_TYPE_JPG = MediaType.parse("image/jpg");

    private final OkHttpClient client = new OkHttpClient();
/*
    public void run() throws Exception {
        // Use the imgur image upload API as documented at https://api.imgur.com/endpoints/image
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("title", "IMG_20160417_012637.jpg")
                .addFormDataPart("image", "IMG_20160417_012637.jpg", RequestBody.create(MEDIA_TYPE_JPG, new File("/storage/emulated/0/DCIM/Flora/IMG_20160417_012637.jpg")))
                .build();
        Log.d("testaa",requestBody.toString());
        Request request = new Request.Builder()
                .url("http://192.168.1.2:8080/flora/create/photo")
                .post(requestBody)
                .build();
        //Log.d("testaa",String.valueOf(request.body()));
        Response response = client.newCall(request).execute();
        Log.d("testaa","end "+response.body().string());
        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);


    }#*/
}
