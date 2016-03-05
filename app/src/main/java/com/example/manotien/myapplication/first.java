package com.example.manotien.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;

public class first extends Fragment {
    private GoogleApiClient mGoogleApiClient;
    GetLocation gps;
    private static final int ACTIVITY_START_CAMERA_APP = 0;
    private ImageView mPhotoCapturedImageView;

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
        final View view =  inflater.inflate(R.layout.fragment_first, container, false);

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
        RadioGroup radiogroup = (RadioGroup) view.findViewById(R.id.radiogroup);
        int select = radiogroup.getCheckedRadioButtonId();
        RadioButton radiocheck = (RadioButton) view.findViewById(select);

        return view;
//        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    public String toDegreeGps(Double gps)
    {
        String lat_degree = Integer.toString(gps.intValue());
        Double temp = (gps - Math.floor(gps)) * 60;
        String lat_min = Integer.toString(temp.intValue());
        String lat_sec = Double.toString((gps * 3600) % 60).substring(0, 7);
        String all = lat_degree + "°" + lat_min + "'" + lat_sec + "\"";
        return all;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == ACTIVITY_START_CAMERA_APP && resultCode == getActivity().RESULT_OK){
            Toast.makeText(getActivity(),"เรียบร้อย", Toast.LENGTH_SHORT).show();
            Bundle extras = data.getExtras();
            Bitmap photoCapturedBitmap = (Bitmap) extras.get("data");
            mPhotoCapturedImageView.setImageBitmap(photoCapturedBitmap);
        }
    }
}
