package com.example.manotien.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
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
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;

public class first extends Fragment {
    private GoogleApiClient mGoogleApiClient;
    GetLocation gps;
    private static final int ACTIVITY_START_CAMERA_APP = 0;
    private ImageView mPhotoCapturedImageView;

    RadioGroup radiogroup;
    RadioButton radiocheck;
    String lat,longti,alt,altmax,altnote,genus,family,sp1,rank1,sp2,rank2,sp3,vern,cultnote,pheno,culti,cf,lang;
    EditText Elat,Elongti,Ealt,Ealtmax,Ealtnote,Egenus,Efamily,Esp1,Erank1,Esp2,Erank2,Esp3,Evern,Ecultnote,Epheno;
    private View view;

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
        mPhotoCapturedImageView = (ImageView) view.findViewById(R.id.photoview);
        Button photo = (Button) view.findViewById(R.id.takephoto);
        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callCameraApplicationIntent = new Intent();
                callCameraApplicationIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
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
        Erank1 = ((EditText)view.findViewById(R.id.rank1edit));
        Esp2 = ((EditText)view.findViewById(R.id.species2edit));
        Erank2 = ((EditText)view.findViewById(R.id.rank2edit));
        Esp3 = ((EditText)view.findViewById(R.id.species3edit));
        Evern = ((EditText)view.findViewById(R.id.vernnameedit));
        //vernlang;
        Ecultnote = ((EditText)view.findViewById(R.id.cultinoteedit));
        Epheno = ((EditText)view.findViewById(R.id.phenoedit));


        return view;
    }

    public String toDegreeGps(Double gps)
    {
        if(gps!=0){
            String lat_degree = Integer.toString(gps.intValue());
            Double temp = (gps - Math.floor(gps)) * 60;
            String lat_min = Integer.toString(temp.intValue());
            String lat_sec = Double.toString((gps * 3600) % 60).substring(0, 7);
            String all = lat_degree + "°" + lat_min + "'" + lat_sec + "\"";
            return all;
        }
        else
            return "0";
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == ACTIVITY_START_CAMERA_APP && resultCode == getActivity().RESULT_OK){
            Toast.makeText(getActivity(),"เรียบร้อย", Toast.LENGTH_SHORT).show();
            Bundle extras = data.getExtras();
            Bitmap photoCapturedBitmap = (Bitmap) extras.get("data");
            mPhotoCapturedImageView.setImageBitmap(photoCapturedBitmap);
        }
    }

    public String[] getMyText() {
        lat = (Elat).getText().toString();
        longti = (Elongti).getText().toString();
        alt = (Ealt).getText().toString();
        altnote = (Ealtnote).getText().toString();
        genus = (Egenus).getText().toString();
        family = (Efamily).getText().toString();
        sp1 = (Esp1).getText().toString();
        rank1 = (Erank1).getText().toString();
        altmax = (Ealtmax).getText().toString();
        sp2 = (Esp2).getText().toString();
        rank2 = (Erank2).getText().toString();
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
        cultnote="";
        pheno="";
        cf="";
        lang="";
        String [] first = {lat,longti,alt,altmax,altnote,genus,family,cf,sp1,rank1,sp2,rank2,sp3,vern,lang,culti,cultnote,pheno};
        return first;
    }
}
