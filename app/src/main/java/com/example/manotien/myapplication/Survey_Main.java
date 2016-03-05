package com.example.manotien.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Set;

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

        SharedPreferences sp = getSharedPreferences("place_date", Context.MODE_PRIVATE);
        String placename = sp.getString("place_name", null);
        String day = sp.getString("day", null);
        String month = sp.getString("month",null);
        String year = sp.getString("year",null);
        String[] date = {day,month,year};
        TextView placename_ans = (TextView)findViewById(R.id.placename);
        TextView protect_ans = (TextView)findViewById(R.id.protect);
        TextView province_ans = (TextView)findViewById(R.id.province);
        TextView collector_ans = (TextView)findViewById(R.id.collector);
        TextView date_ans = (TextView)findViewById(R.id.date);

        dbOperator = new DbOperator(getApplicationContext());
        sqLiteDatabase = dbOperator.getReadableDatabase();
        cursor = dbOperator.GetLocationInformation(sqLiteDatabase,placename,date);
        if(cursor.moveToFirst()){
            do {
                placename_ans.setText(cursor.getString(1));
                protect_ans.setText(cursor.getString(2));
                province_ans.setText(cursor.getString(7));
                collector_ans.setText(cursor.getString(10));
                date_ans.setText(cursor.getString(12)+"/"+cursor.getString(13)+"/"+cursor.getString(14));

            } while (cursor.moveToNext());

            cursor.close();
        }

        Button button = (Button) findViewById(R.id.addflora);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentsend = new Intent(Survey_Main.this, tab_discover.class);
                startActivity(intentsend);
            }
        });




    }

}
