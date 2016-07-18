package com.example.manotien.myapplication;

import android.app.Activity;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

import yuku.ambilwarna.AmbilWarnaDialog;

public class second extends Fragment {

    int color = 0xffffffff;
    //general
    AutoCompleteTextView Ehabit;
    EditText E8_height,E8_bark_outer,E8_outer_thick,E8_bark_inner,E8_bark_inner_main_color,
            E8_bark_inner_sub_color,E8_inner_thick,E8_bark_outer_sub_color,E8_bark_outer_main_color,E8_sap,E8_lbf,E8_yl,E8_leave_scent;
    EditText E8_sepals,E8_detal,E8_tepal,E8_sstamen,E8_pistil,E8_flower_scent,E8_mature,E8_ripen,E8_fruit_scent;
    TextView Tfamily;
    String s8_habit,s8_height,s8_bark,s8_leave,s8_flower,s8_fruit;

    ScrollView general,x,y;


    public second() {
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

        View view = inflater.inflate(R.layout.fragment_second, container, false);

        general = (ScrollView) view.findViewById(R.id.general_form);
        x = (ScrollView) view.findViewById(R.id.x_form);
        y = (ScrollView) view.findViewById(R.id.y_form);
        Tfamily = (TextView)view.findViewById(R.id.family);

        Ehabit = (AutoCompleteTextView)view.findViewById(R.id.habit);
        E8_height = (EditText)view.findViewById(R.id.height);
        E8_bark_inner_sub_color = (AutoCompleteTextView)view.findViewById(R.id.bark_inner_sub_color);
        E8_bark_inner_main_color = (AutoCompleteTextView)view.findViewById(R.id.bark_inner_main_color);
        E8_bark_outer_main_color = (AutoCompleteTextView)view.findViewById(R.id.bark_outer_main_color);
        E8_bark_inner_sub_color = (AutoCompleteTextView)view.findViewById(R.id.bark_inner_sub_color);
        E8_bark_outer = (EditText)view.findViewById(R.id.outeredit);
        E8_outer_thick = (EditText)view.findViewById(R.id.out_thicknessedit);
        E8_bark_inner = (EditText)view.findViewById(R.id.bark_inneredit);
        E8_sap = (EditText)view.findViewById(R.id.sapedit);

        Ehabit = (AutoCompleteTextView)view.findViewById(R.id.habit);
        return view;
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            tab_discover activity = (tab_discover) getActivity();
            String[] first = activity.getMydata();
            //{lat,longti,alt,altmax,altnote,genus,family,cf,sp1,rank1,sp2,rank2,sp3,vern,lang,culti,cultnote,pheno};
            Log.d("tabtab","aa");
            if(first!=null){
                Tfamily.setText("Family: "+first[6]);
                if (first[6].equals("General")) {
                    x.setVisibility(View.VISIBLE);
                    general.setVisibility(View.GONE);
                    y.setVisibility(View.GONE);
                } else {
                    general.setVisibility(View.VISIBLE);
                    x.setVisibility(View.GONE);
                    y.setVisibility(View.GONE);
                }
            }
            else
                Tfamily.setText("Family: ");

        }
    }

    void openDialog(boolean supportsAlpha, final String id_text) {
        AmbilWarnaDialog dialog = new AmbilWarnaDialog(getActivity(), color, supportsAlpha, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                Toast.makeText(getActivity(), "Ok", Toast.LENGTH_SHORT).show();
                second.this.color = color;
                displayColor(id_text);
            }

            @Override
            public void onCancel(AmbilWarnaDialog dialog) {
                Toast.makeText(getActivity(), "Cancel", Toast.LENGTH_SHORT).show();
            }
        });
        dialog.show();
    }
    void displayColor(String id) {
        int resId = getResources().getIdentifier(id,"id","com.example.manotien.myapplication");
        Log.d("testt",String.format("#%06X", (0xFFFFFF & color)) );
        ImageView barkcolor = (ImageView) getView().findViewById(resId);
        barkcolor.setBackgroundColor(Color.parseColor(String.format("#%06X", (0xFFFFFF & color))));
    }

    public String[] getMyText() {

        String plant_description="";
        String note="";
        String[] all = {plant_description,note};
        return all;
    }
}
    /*
        Efamily = (EditText)view.findViewById(R.id.familyedit);
        Efamily.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if (Efamily.getText().toString().equals("Test")) {
                    x.setVisibility(View.VISIBLE);
                    general.setVisibility(View.GONE);
                    y.setVisibility(View.GONE);
                } else {
                    general.setVisibility(View.VISIBLE);
                    x.setVisibility(View.GONE);
                    y.setVisibility(View.GONE);
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

*/