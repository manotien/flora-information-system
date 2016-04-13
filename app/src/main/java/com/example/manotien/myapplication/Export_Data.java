package com.example.manotien.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

import butterknife.OnClick;

public class Export_Data extends AppCompatActivity {

    MyLocationCustomAdapter locationAdapter = null;
    DbOperator dbOperator;
    SQLiteDatabase sqLiteDatabase;
    ArrayList<LocationData> locationList = new ArrayList<LocationData>();
    Cursor cursor;
    EditText filename;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_export__data);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        displayListView();


        Button export_button = (Button) findViewById(R.id.export_button);
        export_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectID ="(";
                dbOperator = new DbOperator(getApplicationContext());
                sqLiteDatabase = dbOperator.getWritableDatabase();
                for(LocationData location:locationList){
                    if(location.isSelected()){
                        dbOperator.setIsExportLocation(sqLiteDatabase,location.getLocationId());
                        selectID=selectID+String.valueOf(location.getLocationId())+",";
                    }
                }
                selectID=selectID.substring(0,selectID.length()-1)+")";

                dbOperator.close();

                DbOperator dbhelper = new DbOperator(getApplicationContext());
                File exportDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "Flora");
                if (!exportDir.exists()) {
                    exportDir.mkdirs();
                }
                filename = (EditText)findViewById(R.id.filenameedit);
                File file = new File(exportDir, filename.getText().toString()+".csv");

                try {
                    file.createNewFile();
                    CSVWriter csvWrite = new CSVWriter(new FileWriter(file));
                    SQLiteDatabase db = dbhelper.getReadableDatabase();
                    String query = "SELECT LOCATION_TABLE.dubs,LOCATION_TABLE.barcode,LOCATION_TABLE.accession,LOCATION_TABLE.collector,LOCATION_TABLE.addcoll,LOCATION_TABLE.prefix,LOCATION_TABLE.number,LOCATION_TABLE.suffix,LOCATION_TABLE.collection_day,LOCATION_TABLE.collection_month,LOCATION_TABLE.collection_year,"
                            +"FLORA_TABLE.family,FLORA_TABLE.genus,FLORA_TABLE.cf,FLORA_TABLE.sp1,FLORA_TABLE.author1,FLORA_TABLE.rank1,FLORA_TABLE.sp2,FLORA_TABLE.author2,FLORA_TABLE.rank2,FLORA_TABLE.sp3,FLORA_TABLE.author3,FLORA_TABLE.plant_description,FLORA_TABLE.phenology,FLORA_TABLE.detby,FLORA_TABLE.detdd,FLORA_TABLE.detmm,FLORA_TABLE.detyy,FLORA_TABLE.detnotes,"
                            +"LOCATION_TABLE.country,LOCATION_TABLE.florareg,LOCATION_TABLE.bkfareacod,LOCATION_TABLE.majorarea,LOCATION_TABLE.minorarea,LOCATION_TABLE.tambon,LOCATION_TABLE.protected,LOCATION_TABLE.gazetteer,LOCATION_TABLE.locality_notes,LOCATION_TABLE.habitat,"
                            +"FLORA_TABLE.cultivated,FLORA_TABLE.cultnotes,FLORA_TABLE.notes,FLORA_TABLE.lat,FLORA_TABLE.ns,FLORA_TABLE.long,FLORA_TABLE.ew,FLORA_TABLE.alt,FLORA_TABLE.altmax,FLORA_TABLE.altnote,FLORA_TABLE.vernacular,FLORA_TABLE.language "
                            +"FROM LOCATION_TABLE INNER JOIN FLORA_TABLE ON FLORA_TABLE.location_id=LOCATION_TABLE.id WHERE LOCATION_TABLE.id in "+selectID;
                    Log.d("ggg",query);
                    //String test ="SELECT * FROM LOCATION_TABLE";
                    Cursor curCSV = db.rawQuery(query, null);
                    csvWrite.writeNext(curCSV.getColumnNames());

                    while (curCSV.moveToNext()) {
                        //String arrStr[] = {curCSV.getString(0), curCSV.getString(1),curCSV.getString(23)};

                        Log.d("ggg",curCSV.getString(4));
                        String arrStr[] = {curCSV.getString(0), curCSV.getString(1), curCSV.getString(2), curCSV.getString(3),
                                curCSV.getString(4), curCSV.getString(5), curCSV.getString(6), curCSV.getString(7), curCSV.getString(8),
                                curCSV.getString(9), curCSV.getString(10), curCSV.getString(11), curCSV.getString(12), curCSV.getString(13),
                                curCSV.getString(14), curCSV.getString(15), curCSV.getString(16), curCSV.getString(17), curCSV.getString(18),
                                curCSV.getString(19), curCSV.getString(20), curCSV.getString(21), curCSV.getString(22), curCSV.getString(23),
                                curCSV.getString(24), curCSV.getString(25), curCSV.getString(26), curCSV.getString(27), curCSV.getString(28),
                                curCSV.getString(29), curCSV.getString(30), curCSV.getString(31), curCSV.getString(32), curCSV.getString(33),
                                curCSV.getString(34), curCSV.getString(35), curCSV.getString(36), curCSV.getString(37), curCSV.getString(38),
                                curCSV.getString(39), curCSV.getString(40), curCSV.getString(41), curCSV.getString(42), curCSV.getString(43),
                                curCSV.getString(44), curCSV.getString(45), curCSV.getString(46), curCSV.getString(47), curCSV.getString(48),
                                curCSV.getString(49), curCSV.getString(50)};

                        csvWrite.writeNext(arrStr);
                    }
                    csvWrite.close();
                    curCSV.close();
                    Toast.makeText(getApplicationContext(), "Export Success", Toast.LENGTH_LONG).show();
                } catch (Exception sqlEx) {
                    Log.d("MainActivity", sqlEx.getMessage(), sqlEx);
                }
            }
        });



        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    private void displayListView() {
        dbOperator = new DbOperator(getApplicationContext());
        sqLiteDatabase = dbOperator.getReadableDatabase();
        cursor = dbOperator.GetLocationListView(sqLiteDatabase);
        if (cursor.moveToFirst()) {
            do {

                LocationData location = new LocationData(cursor.getString(1),cursor.getString(3)+"/"+cursor.getString(4)+"/"+cursor.getString(5),cursor.getInt(0),cursor.getInt(7));
                locationList.add(location);
            } while (cursor.moveToNext());

            cursor.close();
        }

        //create an ArrayAdaptar from the String Array
        locationAdapter = new MyLocationCustomAdapter(this,
                R.layout.location_listview, locationList);
        ListView listView = (ListView) findViewById(R.id.location_view);
        // Assign adapter to ListView
        listView.setAdapter(locationAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // When clicked, show a toast with the TextView text
                LocationData location = (LocationData) parent.getItemAtPosition(position);
                CheckBox cb = (CheckBox) view.findViewById(R.id.checkBox1) ;
                cb.setChecked(!cb.isChecked());
                location.setSelected(cb.isChecked());
            }
        });

    }
}
