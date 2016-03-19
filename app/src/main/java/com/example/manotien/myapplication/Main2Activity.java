package com.example.manotien.myapplication;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.app.DatePickerDialog.OnDateSetListener;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;


public class Main2Activity extends AppCompatActivity {

    private EditText fromDateEtxt;
    private DatePickerDialog fromDatePickerDialog;
    private SimpleDateFormat dateFormatter;

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
//date
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        fromDateEtxt = (EditText) findViewById(R.id.dateedit);
        fromDateEtxt.setInputType(InputType.TYPE_NULL);
       //
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
        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

//spinner
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

                String[] date = fromDateEtxt.getText().toString().split("-");
                String day = date[0];
                String month = date[1];
                String year = date [2];

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
                dbOperator.AddLocationInformation(placename.getText().toString(), protect.getSelectedItem().toString(), locality.getText().toString(), habitat.getText().toString(), country.getText().toString(),
                        region.getText().toString(), province.getText().toString(), district.getText().toString(), subdistrict.getText().toString(), collector.getText().toString(), cocollector, date[0], date[1], date[2], sqLiteDatabase);
                dbOperator.close();

                SharedPreferences sp = getSharedPreferences("place_date",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("place_name", placename.getText().toString());
                editor.putString("day",day );
                editor.putString("month",month);
                editor.putString("year",year);
                editor.commit();
                Intent intent = new Intent(Main2Activity.this, Survey_Main.class);
                startActivity(intent);
            }
        });

    }

}
