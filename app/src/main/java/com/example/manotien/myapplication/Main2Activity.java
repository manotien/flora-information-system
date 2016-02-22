package com.example.manotien.myapplication;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Exploration Place");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String[] protect_item = new String[]{"Test", "Yoyo", "AAAA"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, protect_item);
        Spinner province = (Spinner)findViewById(R.id.spinner);
        province.setAdapter(adapter);


        Button button = (Button)findViewById(R.id.nextbut);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Spinner province = (Spinner) findViewById(R.id.spinner);
                //EditText place = (EditText) findViewById(R.id.place);

                Intent intent = new Intent(Main2Activity.this, Survey_Main.class);
                //intent.putExtra("ans_province", province.getSelectedItem().toString());
                //intent.putExtra("ans_place", place.getText().toString());
                startActivity(intent);
            }
        });

    }

}
