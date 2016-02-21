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
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
        final String ans_province = intent.getStringExtra("ans_province");
        final String ans_place = intent.getStringExtra("ans_place");
        TextView province = (TextView) findViewById(R.id.provincename);
        TextView place = (TextView)findViewById(R.id.placename);
        province.setText(ans_province);
        place.setText(ans_place);

        dbOperator = new DbOperator(getApplicationContext());
        sqLiteDatabase = dbOperator.getReadableDatabase();
        cursor = dbOperator.GetSurveyInformation(sqLiteDatabase);
        if(cursor.moveToFirst()){
            TextView name = (TextView)findViewById(R.id.textView4);
            name.setText(cursor.getString(0));
            cursor.close();
        }

        Button button = (Button) findViewById(R.id.addflora);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentsend = new Intent(Survey_Main.this, Discover.class);
                intentsend.putExtra("ans_province", ans_province);
                intentsend.putExtra("ans_place", ans_place);
                startActivity(intentsend);
            }
        });




    }

}
