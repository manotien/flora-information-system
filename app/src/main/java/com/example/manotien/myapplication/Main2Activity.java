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
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {

    DbOperator dbOperator;
    Context context = this;
    SQLiteDatabase sqLiteDatabase;

    private final String[] list =  {
            "AABC DDD", "AAAB","AACB DDD","ABCD CCC","ABDS","ABCS"};

    private final String[] protect_item = new String[]{"Test", "BBBB", "AAAA"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Exploration Place");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, protect_item);
        Spinner province = (Spinner)findViewById(R.id.protectedspin);
        province.setAdapter(adapter);

        AutoCompleteTextView habitat = (AutoCompleteTextView)findViewById(R.id.habitatedit);
        ArrayAdapter habit_array = new ArrayAdapter(this,android.R.layout.simple_list_item_1,list);
        habitat.setAdapter(habit_array);


        Button button = (Button)findViewById(R.id.nextbut);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText placename = (EditText) findViewById(R.id.placenameedit);
                Spinner protect = (Spinner) findViewById(R.id.protectedspin);
                EditText locality = (EditText) findViewById(R.id.localnoteedit);
                AutoCompleteTextView habitat = (AutoCompleteTextView)findViewById(R.id.habitatedit);
                EditText country = (EditText)findViewById(R.id.countryedit);
                EditText region = (EditText)findViewById(R.id.regionedit);
                EditText province = (EditText)findViewById(R.id.provinceedit);
                EditText district = (EditText)findViewById(R.id.districtedit);
                EditText subdistrict = (EditText)findViewById(R.id.subdisedit);
                EditText collector = (EditText)findViewById(R.id.collectoredit);
                String cocollector = "Tien, AAA";
                dbOperator = new DbOperator(context);
                sqLiteDatabase = dbOperator.getWritableDatabase();
                dbOperator.AddLocationInformation(placename.getText().toString(), protect.getSelectedItem().toString(), locality.getText().toString(), habitat.getText().toString(), country.getText().toString(), region.getText().toString(), province.getText().toString(), district.getText().toString(), subdistrict.getText().toString(), collector.getText().toString(), cocollector, "01", "02", "2016", sqLiteDatabase);
                dbOperator.close();
                Intent intent = new Intent(Main2Activity.this, Survey_Main.class);
                startActivity(intent);
            }
        });

    }

}
