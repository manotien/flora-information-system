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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static android.database.DatabaseUtils.dumpCursorToString;

public class Survey_Main extends AppCompatActivity {

    DbOperator dbOperator;
    SQLiteDatabase sqLiteDatabase;
    Cursor cursor,cursor1;
    Context context;
    RecyclerView recyclerView;
    private TextView collector_ans;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey__main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Location Main");

        SharedPreferences sp = getSharedPreferences("place_date", Context.MODE_PRIVATE);
        String placename = sp.getString("place_name", null);
        String day = sp.getString("day", null);
        String month = sp.getString("month",null);
        String year = sp.getString("year",null);
        String[] date = {day,month,year};
        Log.d("qwer",placename+day+month+year);
        TextView placename_ans = (TextView)findViewById(R.id.placename);
        TextView protect_ans = (TextView)findViewById(R.id.protect);
        TextView province_ans = (TextView)findViewById(R.id.province);
        collector_ans = (TextView)findViewById(R.id.collector);
        TextView date_ans = (TextView)findViewById(R.id.date);

        dbOperator = new DbOperator(getApplicationContext());
        sqLiteDatabase = dbOperator.getReadableDatabase();
        cursor = dbOperator.GetLocationInformation(sqLiteDatabase,placename,date);
        if(cursor.moveToFirst()){
            do {
                SharedPreferences.Editor editor = sp.edit();
                int location_id = cursor.getInt(0);
                editor.putInt("location_id",location_id  );
                editor.commit();

                placename_ans.setText(cursor.getString(1));
                protect_ans.setText(cursor.getString(2));
                province_ans.setText(cursor.getString(7));
                collector_ans.setText(cursor.getString(10));
                date_ans.setText(cursor.getString(12)+"/"+cursor.getString(13)+"/"+cursor.getString(14));

            } while (cursor.moveToNext());

            cursor.close();
        }

        //recycle view
        recyclerView= (RecyclerView) findViewById(R.id.my_recycler_view);

        RecyclerAdapter adapter=new RecyclerAdapter(this,createList());
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



        Button button = (Button) findViewById(R.id.addflora);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentsend = new Intent(Survey_Main.this, tab_discover.class);
                startActivity(intentsend);
            }
        });


    }
    private List<FloraData> createList() {

        List<FloraData> result = new ArrayList<FloraData>();
        SharedPreferences sp = getSharedPreferences("place_date", Context.MODE_PRIVATE);

        cursor1 = dbOperator.GetFloraForListView(sqLiteDatabase, sp.getInt("location_id", -1));
        if(cursor1.moveToFirst()){
            do {
                //cursor1.getString(4) = specie , cursor1.getString(3) = genus
                Log.d("tesa",dumpCursorToString(cursor1));
                FloraData fd = new FloraData();
                fd.name = cursor1.getString(3)+" "+cursor1.getString(4);
                fd.genus = collector_ans.getText().toString() ;
                fd.family = "Family: "+ cursor1.getString(2);
                fd.flora_id = cursor1.getInt(0);
                result.add(fd);
            } while (cursor1.moveToNext());

            cursor1.close();
        }

        return result;
    }
}
