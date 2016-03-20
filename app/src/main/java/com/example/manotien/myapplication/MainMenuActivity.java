package com.example.manotien.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;

public class MainMenuActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    DbOperator dbOperator;
    SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainmenu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        setTitle("Main Menu");

        //start
        Button button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainMenuActivity.this, Main2Activity.class));
            }
        });

        //see flora data
        Button test = (Button)findViewById(R.id.information);
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainMenuActivity.this, tab_discover.class));
            }
        });
        //check database
        if(doesDatabaseExist(this,"FLORA"))
        {
            Log.d("checkaa","hi");
        }
        else
        {
            dbOperator = new DbOperator(getApplicationContext());
            sqLiteDatabase = dbOperator.getWritableDatabase();
            InitialData init =new InitialData();
            init.initial(sqLiteDatabase);
            Log.d("checkaa","bye");
            dbOperator.close();
        }

        //export database
        Button button1 = (Button)findViewById(R.id.export);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                File dbFile = getDatabasePath("FLORA");
                DbOperator dbhelper = new DbOperator(getApplicationContext());
                File exportDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "Flora");
                if (!exportDir.exists()) {
                    exportDir.mkdirs();
                }

                File file = new File(exportDir, "csvname.csv");

                try {
                    file.createNewFile();
                    CSVWriter csvWrite = new CSVWriter(new FileWriter(file));
                    SQLiteDatabase db = dbhelper.getReadableDatabase();
                    String query = "SELECT LOCATION_TABLE.dubs,LOCATION_TABLE.barcode,LOCATION_TABLE.accession,LOCATION_TABLE.collector,LOCATION_TABLE.addcoll,LOCATION_TABLE.prefix,LOCATION_TABLE.number,LOCATION_TABLE.suffix,LOCATION_TABLE.collection_day,LOCATION_TABLE.collection_month,LOCATION_TABLE.collection_year,"
                            +"FLORA_TABLE.family,FLORA_TABLE.genus,FLORA_TABLE.cf,FLORA_TABLE.sp1,FLORA_TABLE.author1,FLORA_TABLE.rank1,FLORA_TABLE.sp2,FLORA_TABLE.author2,FLORA_TABLE.rank2,FLORA_TABLE.sp3,FLORA_TABLE.author3,FLORA_TABLE.plant_description,FLORA_TABLE.phenology,FLORA_TABLE.detby,FLORA_TABLE.detdd,FLORA_TABLE.detmm,FLORA_TABLE.detyy,FLORA_TABLE.detnotes,"
                            +"LOCATION_TABLE.country,LOCATION_TABLE.florareg,LOCATION_TABLE.bkfareacod,LOCATION_TABLE.majorarea,LOCATION_TABLE.minorarea,LOCATION_TABLE.tambon,LOCATION_TABLE.protected,LOCATION_TABLE.gazetteer,LOCATION_TABLE.locality_notes,LOCATION_TABLE.habitat,"
                            +"FLORA_TABLE.cultivated,FLORA_TABLE.cultnotes,FLORA_TABLE.notes,FLORA_TABLE.lat,FLORA_TABLE.ns,FLORA_TABLE.long,FLORA_TABLE.ew,FLORA_TABLE.alt,FLORA_TABLE.altmax,FLORA_TABLE.altnote,FLORA_TABLE.vernacular,FLORA_TABLE.language "
                            +"FROM LOCATION_TABLE INNER JOIN FLORA_TABLE ON FLORA_TABLE.location_id=LOCATION_TABLE.id;";

                    String test ="SELECT * FROM habit_table";
                    Cursor curCSV = db.rawQuery(test, null);
                    csvWrite.writeNext(curCSV.getColumnNames());

                    while (curCSV.moveToNext()) {
                        String arrStr[] = {curCSV.getString(0), curCSV.getString(1)};
                        /*
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
*/
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
    }
    private static boolean doesDatabaseExist(Context context, String dbName) {
        File dbFile = context.getDatabasePath(dbName);
        return dbFile.exists();
    }




    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_main) {
            // Handle the camera action
        } else if (id == R.id.nav_info) {

        } else if (id == R.id.nav_database) {

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
