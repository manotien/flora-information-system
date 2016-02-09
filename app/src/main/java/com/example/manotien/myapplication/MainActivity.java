package com.example.manotien.myapplication;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Main");
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        Button button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Main2Activity.class));
            }
        });

        Button button1 = (Button)findViewById(R.id.export);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                File dbFile=getDatabasePath("FLORA");
                DbOperator dbhelper = new DbOperator(getApplicationContext());
                File exportDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "Flora");
                Log.d(exportDir.toString(),"kuy");
                if (!exportDir.exists())
                {
                    exportDir.mkdirs();

                }

                File file = new File(exportDir, "csvname.csv");

                try
                {
                    file.createNewFile();
                    CSVWriter csvWrite = new CSVWriter(new FileWriter(file));
                    SQLiteDatabase db = dbhelper.getReadableDatabase();
                    Cursor curCSV = db.rawQuery("SELECT * FROM FIRST_TABLE",null);
                    csvWrite.writeNext(curCSV.getColumnNames());
                    while(curCSV.moveToNext())
                    {
                        //Which column you want to exprort
                        String arrStr[] ={curCSV.getString(0),curCSV.getString(1), curCSV.getString(2)};
                        csvWrite.writeNext(arrStr);
                    }
                    csvWrite.close();
                    curCSV.close();
                    Toast.makeText(getApplicationContext(), "Export Success", Toast.LENGTH_LONG).show();
                }
                catch(Exception sqlEx)
                {
                    Log.e("MainActivity", sqlEx.getMessage(), sqlEx);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}
