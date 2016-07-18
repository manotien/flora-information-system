package com.example.manotien.myapplication;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class first extends Fragment {
    private GoogleApiClient mGoogleApiClient;
    GetLocation gps;
    private static final int ACTIVITY_START_CAMERA_APP = 0;
    String imageFileName;
    DbOperator dbOperator;
    SQLiteDatabase sqLiteDatabase;
    Cursor cursor;

    Uri uri;
    int i=0;
    RadioGroup radiogroup;
    RadioButton radiocheck;
    String lat,longti,alt,altmax,altnote,genus,family,sp1,rank1,sp2,rank2,sp3,vern,cultnote,pheno,culti,cf,lang;
    EditText Elat,Elongti,Ealt,Ealtmax,Ealtnote,Esp1,Esp2,Esp3,Evern,Ecultnote,Epheno;
    AutoCompleteTextView Egenus,Efamily;
    Spinner cf_spinner, pheno_spinner, lang_spinner, rank1_spinner, rank2_spinner;
    private View view;
    ArrayList<String> photolist = new ArrayList<>();
    public first() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        super.onCreateView(inflater, container, savedInstanceState);
        view =  inflater.inflate(R.layout.fragment_first, container, false);
        Log.d("testz","a1");
        //photo

        Button photo = (Button) view.findViewById(R.id.takephoto);
        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                imageFileName = "IMG_" + timeStamp + ".jpg";
                File f = new File(Environment.getExternalStorageDirectory(), "DCIM/Flora/" + imageFileName);
                uri = Uri.fromFile(f);
                Intent callCameraApplicationIntent = new Intent();
                callCameraApplicationIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                callCameraApplicationIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);


                startActivityForResult(callCameraApplicationIntent, ACTIVITY_START_CAMERA_APP);

            }
        });
        //gps
        Button buttongps = (Button) view.findViewById(R.id.getgps);
        buttongps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gps = new GetLocation(getActivity());
                if (gps.canGetLocation()) {
                    String getLat = toDegreeGps(gps.getLat());
                    String getLong = toDegreeGps(gps.getLng());
                    EditText lati = (EditText) view.findViewById(R.id.latedit);
                    EditText longti = (EditText) view.findViewById(R.id.longedit);
                    lati.setText(getLat);
                    longti.setText(getLong);
                    gps.stopUsingGPS();
                }
            }
        });


        //cultivate radio box
        radiogroup = (RadioGroup) view.findViewById(R.id.radiogroup);
        int select = radiogroup.getCheckedRadioButtonId();
        radiocheck = (RadioButton) view.findViewById(select);

        //get value from Edittext
        Elat = (EditText)view.findViewById(R.id.latedit);
        Elat = ((EditText)view.findViewById(R.id.latedit));
        Elongti = ((EditText)view.findViewById(R.id.longedit));
        Ealt = ((EditText)view.findViewById(R.id.altedit));
        Ealtmax = (EditText)view.findViewById(R.id.altmaxedit);
        Ealtnote = ((EditText)view.findViewById(R.id.altnoteedit));
        Egenus = ((AutoCompleteTextView)view.findViewById(R.id.genusedit));
        Efamily = ((AutoCompleteTextView)view.findViewById(R.id.familyedit));
        Esp1 = ((EditText)view.findViewById(R.id.species1edit));
        Esp2 = ((EditText)view.findViewById(R.id.species2edit));
        Esp3 = ((EditText)view.findViewById(R.id.species3edit));
        Evern = ((EditText)view.findViewById(R.id.vernnameedit));
        cf_spinner = ((Spinner)view.findViewById(R.id.cfspinner));
        pheno_spinner = ((Spinner)view.findViewById(R.id.phenospinner));
        lang_spinner = (Spinner)view.findViewById(R.id.vern_language);
        rank1_spinner = (Spinner)view.findViewById(R.id.rank1spinner);
        rank2_spinner = (Spinner)view.findViewById(R.id.rank2spinner);
        //vernlang;
        Ecultnote = ((EditText)view.findViewById(R.id.cultinoteedit));

        String[] cf_list = new String[]{"None","cf.","aff."};
        ArrayAdapter<String> cf_array = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, cf_list);
        cf_spinner.setAdapter(cf_array);

        String[] pheno_list = new String[]{"None","fw","fr","fw&fr"};
        ArrayAdapter<String> pheno_array = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, pheno_list);
        pheno_spinner.setAdapter(pheno_array);

        String[] lang_list = new String[]{"EN","TH"};
        ArrayAdapter<String> lang_array = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, lang_list);
        lang_spinner.setAdapter(lang_array);

        String[] rank1_list = new String[]{"None","var.","subsp.","forma"};
        ArrayAdapter<String> rank1_array = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, rank1_list);
        rank1_spinner.setAdapter(rank1_array);

        String[] rank2_list = new String[]{"None","var.","subsp.","forma"};
        ArrayAdapter<String> rank2_array = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, rank2_list);
        rank2_spinner.setAdapter(rank2_array);


        ArrayList<String> genusList = new ArrayList<>();
        dbOperator = new DbOperator(getContext());
        sqLiteDatabase = dbOperator.getReadableDatabase();
        cursor = dbOperator.GetGenusList(sqLiteDatabase);
        final JSONObject genus_family = new JSONObject();
        if (cursor.moveToFirst()) {
            do {
                try {
                    genus_family.put(cursor.getString(0),cursor.getString(1));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                genusList.add(cursor.getString(0));
            } while (cursor.moveToNext());

            cursor.close();
        }
        ArrayAdapter<String> genus_array = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, genusList);
        Egenus.setAdapter(genus_array);
        Egenus.setThreshold(1);

        ArrayList<String> familyList = new ArrayList<>();
        dbOperator = new DbOperator(getContext());
        sqLiteDatabase = dbOperator.getReadableDatabase();
        cursor = dbOperator.GetFamilyList(sqLiteDatabase);

        if (cursor.moveToFirst()) {
            do {
                familyList.add(cursor.getString(0));
            } while (cursor.moveToNext());

            cursor.close();
        }
        ArrayAdapter<String> family_array = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, familyList);
        Efamily.setAdapter(family_array);
        Efamily.setThreshold(1);

        Egenus.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                String genus= Egenus.getText().toString();
                if(!genus_family.isNull(genus))
                    try {
                        Efamily.setText(genus_family.getString(genus));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
        return view;
    }

    public String toDegreeGps(Double gps)
    {
        if(gps!=0){
            String lat_degree = Integer.toString(gps.intValue());
            Double temp = (gps - Math.floor(gps)) * 60;
            String lat_min = Integer.toString(temp.intValue());
            String lat_sec = Double.toString( Math.round((gps * 3600) % 60.0 *10 )/10.0);
            String all = lat_degree + "°" + lat_min + "'" + lat_sec + "\"";
            return all;
        }
        else
            return "0";
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == ACTIVITY_START_CAMERA_APP && resultCode == getActivity().RESULT_OK){
            getActivity().getContentResolver().notifyChange(uri, null);
            ContentResolver cr = getActivity().getContentResolver();
            try {
//////////////////////
                // First decode with inJustDecodeBounds=true to check dimensions
                final BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(uri), null, options);
                // Calculate inSampleSize
                options.inSampleSize = calculateInSampleSize(options, 400, 580);
                // Decode bitmap with inSampleSize set
                options.inJustDecodeBounds = false;

                Bitmap bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(uri), null, options);
/////////////////////
                ByteArrayOutputStream outStream = new ByteArrayOutputStream();
                // compress to the format you want, JPEG, PNG...
                // 70 is the 0-100 quality percentage
                bitmap.compress(Bitmap.CompressFormat.JPEG,100 , outStream);
                // we save the file, at least until we have made use of it
                File f = new File(Environment.getExternalStorageDirectory(), "DCIM/Flora/"+ imageFileName);
                f.createNewFile();
                //write the bytes in file
                FileOutputStream fo = new FileOutputStream(f);
                fo.write(outStream.toByteArray());
                // remember close de FileOutput
                fo.close();
                //////////////////

                //Drawable d = new BitmapDrawable(getResources(), bitmap);
                LinearLayout layout = (LinearLayout) view.findViewById(R.id.linear);
                ImageView imageView = new ImageView(getContext());
                imageView.setId(i);

                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(500, 600);

                imageView.setPadding(2, 2, 20, 2);
                imageView.setImageBitmap(bitmap);
                imageView.setLayoutParams(layoutParams);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                i++;

                photolist.add(String.valueOf(uri));

                layout.addView(imageView);
            }catch (Exception e)
            {
                e.printStackTrace();
            }
            Toast.makeText(getActivity().getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
        }
    }
    public ArrayList<String> getMyPhoto(){
        return photolist;
    }
    public String[] getMyText() {
        lat = (Elat).getText().toString();
        longti = (Elongti).getText().toString();
        alt = (Ealt).getText().toString();
        altnote = (Ealtnote).getText().toString();
        genus = (Egenus).getText().toString();
        family = (Efamily).getText().toString();
        sp1 = (Esp1).getText().toString();
        altmax = (Ealtmax).getText().toString();
        sp2 = (Esp2).getText().toString();
        sp3 = (Esp3).getText().toString();
        vern = (Evern).getText().toString();
        radiogroup = (RadioGroup) view.findViewById(R.id.radiogroup);
        int select = radiogroup.getCheckedRadioButtonId();
        radiocheck = (RadioButton) view.findViewById(select);
        if(radiogroup.getCheckedRadioButtonId() != -1) {
            culti = radiocheck.getText().toString();
        }
        else
            culti = "No";

        cultnote = Ecultnote.getText().toString();
        pheno = pheno_spinner.getSelectedItem().toString();
        cf = cf_spinner.getSelectedItem().toString();
        lang = lang_spinner.getSelectedItem().toString();
        rank1 = rank1_spinner.getSelectedItem().toString();
        rank2 = rank2_spinner.getSelectedItem().toString();
        String [] first = {lat,longti,alt,altmax,altnote,genus,family,cf,sp1,rank1,sp2,rank2,sp3,vern,lang,culti,cultnote,pheno};

        return first;
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            if (width > height) {
                inSampleSize = Math.round((float)height / (float)reqHeight);
            } else {
                inSampleSize = Math.round((float)width / (float)reqWidth);
            }
        }
        return inSampleSize;
    }

}
