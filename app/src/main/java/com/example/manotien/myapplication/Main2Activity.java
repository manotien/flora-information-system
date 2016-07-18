package com.example.manotien.myapplication;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.app.DatePickerDialog.OnDateSetListener;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
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
import android.widget.Toast;

import org.json.JSONException;

import java.io.File;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
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
    private final String[] region_list = new String[]{"Northern","Northeastern","Southwestern","Central","Peninsular","Southeastern","Eastern"};
    private final String[] region_province = new String[]{"Peninsular","Krabi","Southwestern","Kanchanaburi","Northeastern","Kalasin","Northern","Kampaeng Phet","Northeastern","Khon Kaen","Southeastern","Chanthaburi","Southeastern","Chachoengsao","Southeastern","Chonburi","Central","Chainat","Eastern","Chaiyaphum","Peninsular","Chumphon","Northern","Chiangrai","Northern","Chiang Mai","Peninsular","Trang","Southeastern","Trat","Northern","Tak","Central","Nakhon Nayok","Central","Nakhon Prathom","Northeastern","Nakhon Phanom","Eastern","Nakhon Ratchasima","Peninsular","Nakhon Si Thammarat","Northern","Nakhon Sawan","Central","Nonthaburi","Peninsular","Narathiwat","Northern","Nan","Northeastern","Bung Kan","Eastern","Burirum","Central","Pathum Thani","Southwestern","Prachuap Khilikhan","Southeastern","Prachinburi","Peninsular","Pattani","Central","Phra Nakhon Si Ayudhya","Northern","Phayao","Peninsular","Phang Nga","Peninsular","Phatthalung","Northern","Phichit","Northern","Phitsanu Lok","Southwestern","Phetchaburi","Northeastern","Phetchabun","Northern","Phrae","Peninsular","Phuket","Northeastern","Mahasarakham","Northeastern","Mukdahan","Northern","Mae Hong Son","Eastern","Yasothon","Peninsular","Yala","Eastern","Roi Et","Peninsular","Ranong","Southeastern","Rayong","Southwestern","Ratchaburi","Central","Lopburi","Northern","Lampang","Northern","Lamphun","Northeastern","Loei","Eastern","Sisaket","Northeastern","Sakon Nakhon","Peninsular","Songkhla","Peninsular","Satun","Central","Samut Prakarn","Central","Samut Songkham","Central","Samut Sakhon","Southeastern","Srakaeo","Central","Saraburi","Central","Singburi","Northern","Sukhothai","Central","Suphan Buri","Peninsular","Surat Thani","Eastern","Surin","Northeastern","Nong Khai","Northeastern","Nong Bua Lamphu","Central","Ang Thong","Eastern","Amnaj Charoen","Northeastern","Udon Thani","Northern","Auttaradit","Southwestern","Uthai Thani","Eastern","Ubon Ratchathani","Central","Bangkok"};
    private String[] district_list = new String[]{};
    int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Request permission
        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(Main2Activity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(Main2Activity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
        final Map<String, String> map_region = new HashMap<String, String>();
        int index=0;
        for(String name:region_province){
            if(index%2 == 1) {
                map_region.put(name, region_province[index-1]);
            }
            index++;
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Add Location");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//get layout
        final EditText placename = (EditText) findViewById(R.id.placenameedit);
        final AutoCompleteTextView protect = (AutoCompleteTextView) findViewById(R.id.protectedspin);
        final EditText locality = (EditText) findViewById(R.id.localnoteedit);
        final AutoCompleteTextView habitat = (AutoCompleteTextView) findViewById(R.id.habitatedit);
        final AutoCompleteTextView country = (AutoCompleteTextView) findViewById(R.id.countryedit);
        final AutoCompleteTextView region = (AutoCompleteTextView) findViewById(R.id.regionedit);
        final AutoCompleteTextView province = (AutoCompleteTextView) findViewById(R.id.provinceedit);
        final AutoCompleteTextView district = (AutoCompleteTextView) findViewById(R.id.districtedit);
        final AutoCompleteTextView subdistrict = (AutoCompleteTextView) findViewById(R.id.subdisedit);
        final EditText collector = (EditText) findViewById(R.id.collectoredit);
        country.setText("Thailand");


        File flora_photo = new File(Environment.getExternalStorageDirectory(), "DCIM/Flora");

        if (!flora_photo.exists()) {
            flora_photo.mkdirs();
        }
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

        //Autocomplete address
        final Map<String, String> address = new HashMap<String, String>();

        dbOperator = new DbOperator(getApplicationContext());
        sqLiteDatabase = dbOperator.getReadableDatabase();
        cursor = dbOperator.GetDistrictList(sqLiteDatabase);
        if (cursor.moveToFirst()) {
            do {
                if(address.get(cursor.getString(1)) == null)
                    address.put(cursor.getString(1), cursor.getString(0));
                else
                    address.put(cursor.getString(1)+" ("+cursor.getString(0)+")", cursor.getString(0));
            } while (cursor.moveToNext());
            cursor.close();
        }
        Set temp = address.keySet();
        district_list = (String[]) temp.toArray((new String[0]));
        ArrayAdapter<String> district_array = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, district_list);
        district.setAdapter(district_array);
        district.setThreshold(1);

        ArrayList<String> province_list = new ArrayList<>();
        dbOperator = new DbOperator(getApplicationContext());
        sqLiteDatabase = dbOperator.getReadableDatabase();
        cursor = dbOperator.GetProvinceList(sqLiteDatabase);
        if (cursor.moveToFirst()) {
            do {
                province_list.add(cursor.getString(0));
            } while (cursor.moveToNext());
            cursor.close();
        }
        ArrayAdapter<String> province_array = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, province_list);
        province.setAdapter(province_array);
        province.setThreshold(1);

        ArrayList<String> protected_list = new ArrayList<>();
        dbOperator = new DbOperator(getApplicationContext());
        sqLiteDatabase = dbOperator.getReadableDatabase();
        cursor = dbOperator.GetProtectedList(sqLiteDatabase);
        if (cursor.moveToFirst()) {
            do {
                protected_list.add(cursor.getString(0));
            } while (cursor.moveToNext());
            cursor.close();
        }
        ArrayAdapter<String> protected_array = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, protected_list);
        protect.setAdapter(protected_array);
        protect.setThreshold(1);

        //Autocomplete Region
        ArrayAdapter<String> region_array = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, region_list);
        region.setAdapter(region_array);
        region.setThreshold(1);

        //text local change listener
        district.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                String district_txt= district.getText().toString();
                if(address.get(district_txt)!=null)
                    province.setText(address.get(district_txt));
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        province.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                String province_txt= province.getText().toString();
                if(map_region.get(province_txt)!=null)
                    region.setText(map_region.get(province_txt));
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

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
                        .setTitle("Select Cocollector")
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
                dbOperator.AddLocationInformation(placename.getText().toString(), protect.getText().toString(), locality.getText().toString(), habitat.getText().toString(), country.getText().toString(),
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
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                /*HERE PERMISSION IS ALLOWED.
                * YOU SHOULD CODE HERE*/
                } else {

                    Toast.makeText(Main2Activity.this, "Permission deny to write external storag", Toast.LENGTH_SHORT).show();
                }
                return;
            }
            case 2: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                /*HERE PERMISSION IS ALLOWED.
                * YOU SHOULD CODE HERE*/
                } else {

                    Toast.makeText(Main2Activity.this, "Permission deny to request fine location", Toast.LENGTH_SHORT).show();
                }
                return;
            }
            case 3: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                /*HERE PERMISSION IS ALLOWED.
                * YOU SHOULD CODE HERE*/
                } else {

                    Toast.makeText(Main2Activity.this, "Permission deny to request internet", Toast.LENGTH_SHORT).show();
                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
}

