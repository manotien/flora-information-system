package com.example.manotien.myapplication;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class Survey_Main extends AppCompatActivity {

    DbOperator dbOperator;
    SQLiteDatabase sqLiteDatabase;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey__main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        final String ans_province = intent.getStringExtra("placename");
        final String ans_place = intent.getStringExtra("ans_place");

        String placename ="test";
        String a = "01";
        String b = "02";
        String c = "2016";
        String[] date ={a,b,c};

        dbOperator = new DbOperator(getApplicationContext());
        sqLiteDatabase = dbOperator.getReadableDatabase();
        cursor = dbOperator.GetLocationInformation(sqLiteDatabase,placename,date);
        if(cursor.moveToFirst()){
            do {
                Log.d("sqlkuykuy",cursor.getString(0)+cursor.getString(1)+cursor.getString(2)+cursor.getString(3));
            } while (cursor.moveToNext());

            cursor.close();
        }

        Button button = (Button) findViewById(R.id.addflora);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentsend = new Intent(Survey_Main.this, Discover.class);
                startActivity(intentsend);
            }
        });




    }

}
