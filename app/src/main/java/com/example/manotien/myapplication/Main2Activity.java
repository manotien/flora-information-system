package com.example.manotien.myapplication;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.app.DatePickerDialog.OnDateSetListener;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import static android.database.DatabaseUtils.dumpCursorToString;


public class Main2Activity extends AppCompatActivity {

    private EditText fromDateEtxt;
    private DatePickerDialog fromDatePickerDialog;
    private SimpleDateFormat dateFormatter;


    DbOperator dbOperator;
    Context context = this;
    SQLiteDatabase sqLiteDatabase;
    Cursor cursor;
    String cocollector = "";
    private final String[] protect_item = new String[]{"None","Mu Ko Lanta","Chae Son National Park", "Doi Chong National Park", "Mae Tho National Park","Phu Pha Man National Park","Kaeng Krachan National Park","Khao Luang National Park","Thale Ban National Park"};
    private final String[] region_list = new String[]{"Northern","Northeastern","Southwestern","Central","Peninsular","Southeastern","Eastern"};
    private final String[] province_list = new String[]{"Bangkok","Chachoengsao","Chaiyaphum","Trat","Trang","Chumphon","Chonburi","Kanchanaburi","Lamphun","Krabi","","Buriram","Nonthaburi","Nan","Narathiwat"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Add Location");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//get layout
        final EditText placename = (EditText) findViewById(R.id.placenameedit);
        final Spinner protect = (Spinner) findViewById(R.id.protectedspin);
        final EditText locality = (EditText) findViewById(R.id.localnoteedit);
        final AutoCompleteTextView habitat = (AutoCompleteTextView) findViewById(R.id.habitatedit);
        final AutoCompleteTextView country = (AutoCompleteTextView) findViewById(R.id.countryedit);
        final AutoCompleteTextView region = (AutoCompleteTextView) findViewById(R.id.regionedit);
        final AutoCompleteTextView province = (AutoCompleteTextView) findViewById(R.id.provinceedit);
        final AutoCompleteTextView district = (AutoCompleteTextView) findViewById(R.id.districtedit);
        final AutoCompleteTextView subdistrict = (AutoCompleteTextView) findViewById(R.id.subdisedit);
        final EditText collector = (EditText) findViewById(R.id.collectoredit);

//date
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        fromDateEtxt = (EditText) findViewById(R.id.dateedit);
        fromDateEtxt.setInputType(InputType.TYPE_NULL);
        fromDateEtxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fromDatePickerDialog.show();
            }
        });

        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(this, new OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                fromDateEtxt.setText(dateFormatter.format(newDate.getTime()));
            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

//spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, protect_item);
        protect.setAdapter(adapter);

        //Autocomplete Habitat
        ArrayList<String> habitatList = new ArrayList<>();
        dbOperator = new DbOperator(getApplicationContext());
        sqLiteDatabase = dbOperator.getReadableDatabase();
        cursor = dbOperator.GetHabitatList(sqLiteDatabase);
        if (cursor.moveToFirst()) {
            do {

                habitatList.add(cursor.getString(0));
            } while (cursor.moveToNext());

            cursor.close();
        }

        ArrayAdapter<String> habitat_array = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, habitatList);
        habitat.setAdapter(habitat_array);
        habitat.setThreshold(1);

        //Autocomplete Region
        ArrayAdapter<String> region_array = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, region_list);
        region.setAdapter(region_array);
        region.setThreshold(1);

        //Autocomplete province
        ArrayAdapter<String> province_array = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, province_list);
        province.setAdapter(province_array);
        province.setThreshold(1);


//click button to show co-collector dialog
        final EditText cobut = (EditText) findViewById(R.id.cocollecbut);
        cobut.setInputType(InputType.TYPE_NULL);
        cobut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] items = {"Manotien Yuyanyong ", "Surote Wongpaiboon", "Chayutpong Prompak", "Danai Wilaieak"};

// arraylist to keep the selected items
                final ArrayList seletedItems = new ArrayList();
                AlertDialog dialog = new AlertDialog.Builder(Main2Activity.this)
                        .setTitle("Select The Difficulty Level")
                        .setMultiChoiceItems(items, null, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int indexSelected, boolean isChecked) {
                                if (isChecked) {
                                    // If the user checked the item, add it to the selected items
                                    seletedItems.add(indexSelected);
                                } else if (seletedItems.contains(indexSelected)) {
                                    // Else, if the item is already in the array, remove it
                                    seletedItems.remove(Integer.valueOf(indexSelected));
                                }
                            }
                        }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                cocollector="";
                                for (Object item : seletedItems) {
                                    //cocol_item.add();
                                    cocollector = cocollector + items[Integer.valueOf((Integer) item)] + ",";
                                }

                                if (cocollector.length() != 0)
                                    cocollector = cocollector.substring(0, cocollector.length() - 1);

                                cobut.setText(cocollector);

                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        }).create();
                dialog.show();

            }
        });

//submit data
        Button button = (Button) findViewById(R.id.nextbut);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String day = "";
                String month = "";
                String year = "";
                if(fromDateEtxt.getText().toString().length()!=0) {
                    String[] date = fromDateEtxt.getText().toString().split("-");
                    day = date[0];
                    month = date[1];
                    year = date[2];
                }

                dbOperator = new DbOperator(context);
                sqLiteDatabase = dbOperator.getWritableDatabase();
                dbOperator.AddLocationInformation(placename.getText().toString(), protect.getSelectedItem().toString(), locality.getText().toString(), habitat.getText().toString(), country.getText().toString(),
                        region.getText().toString(), province.getText().toString(), district.getText().toString(), subdistrict.getText().toString(), collector.getText().toString(), cocollector, day, month, year, sqLiteDatabase);
                dbOperator.close();

                SharedPreferences sp = getSharedPreferences("place_date", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("place_name", placename.getText().toString());
                editor.putString("day", day);
                editor.putString("month", month);
                editor.putString("year", year);
                editor.commit();
                Intent intent = new Intent(Main2Activity.this, Survey_Main.class);
                startActivity(intent);
            }
        });

    }
}

