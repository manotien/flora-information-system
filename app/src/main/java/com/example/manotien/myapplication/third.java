package com.example.manotien.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class third extends Fragment {

    DbOperator dbOperator;
    SQLiteDatabase sqLiteDatabase;
    public third() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final View view =inflater.inflate(R.layout.fragment_third, container, false);

        SharedPreferences sp = this.getActivity().getSharedPreferences("place_date", Context.MODE_PRIVATE);
        final int location_id = sp.getInt("location_id", -1);

        Button but = (Button)view.findViewById(R.id.submit);
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String detby="";
                String detdd="";
                String detmm="";
                String detyy="";
                String detnote="";

                tab_discover activity = (tab_discover) getActivity();
                String[] first = activity.getMydata();
                String[] second = activity.getMydata2();
                ArrayList<String> photolist = activity.getMyphoto();

                DbOperator dbOperator = new DbOperator(getActivity());
                sqLiteDatabase = dbOperator.getWritableDatabase();
                long flora_id = dbOperator.AddFloraInformation(first[0],first[1],first[2],first[3],first[4],first[5],first[6],first[7],first[8],first[9],first[10],
                        first[11],first[12],first[13],first[14],first[15],first[16],first[17],second[0],second[1],detby,detdd,detmm,detyy,detnote,location_id,sqLiteDatabase);
                int i=0;
                for (i=0;i<photolist.size();i++) {
                    dbOperator.AddPhoto(sqLiteDatabase, photolist.get(i), String.valueOf(flora_id));
                }
                dbOperator.close();

                Intent intentsend = new Intent(getActivity(), Survey_Main.class);
                startActivity(intentsend);
                //{lat,longti,alt,altmax,altnote,genus,family,cf,sp1,rank1,sp2,rank2,sp3,vern,lang,culti,cultnote,pheno};
                //{plant_des,note,location_id

            }
        });
        return view;
    }

}
