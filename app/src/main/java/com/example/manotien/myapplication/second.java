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
    AutoCompleteTextView Ehabit,E8_leave_upper_main_color,E8_leave_upper_sub_color,E8_leave_lower_main_color,
            E8_leave_lower_sub_color,E8_LBF_main_color,E8_LBF_sub_color,E8_YL_main_color,E8_YL_sub_color,
            E8_sepals_main_color, E8_sepals_sub_color,E8_petals_main_color, E8_petals_sub_color,E8_tepals_main_color,
            E8_tepals_sub_color,E8_stamens_main_color,E8_stamens_sub_color,E8_pistils_main_color,E8_pistils_sub_color,
            E8_immature_main_color,E8_immature_sub_color,E8_mature_main_color,E8_mature_sub_color,E8_ripen_main_color,
            E8_ripen_sub_color;
    EditText E8_height,E8_bark_outer,E8_outer_thick,E8_bark_inner,E8_bark_inner_main_color,E8_sap,
            E8_bark_inner_sub_color,E8_inner_thick,E8_bark_outer_sub_color,E8_bark_outer_main_color,
            E8_leave_upper,E8_leave_lower,E8_LBF,E8_YL,E8_leave_scent;

    EditText E8_sepals,E8_petals,E8_tepals,E8_stamens,E8_pistils,E8_flower_scent,E8_immature,E8_mature,E8_ripen,E8_fruit_scent;
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
        //================= Bark ==================
        E8_bark_outer_main_color = (AutoCompleteTextView)view.findViewById(R.id.bark_outer_main_color);
        E8_bark_outer_sub_color = (AutoCompleteTextView)view.findViewById(R.id.bark_outer_sub_color);
        E8_bark_outer = (EditText)view.findViewById(R.id.outeredit);
        E8_outer_thick = (EditText)view.findViewById(R.id.out_thicknessedit);
        E8_bark_inner = (EditText)view.findViewById(R.id.bark_inneredit);
        E8_inner_thick = (EditText)view.findViewById(R.id.in_thicknessedit);
        E8_bark_inner_main_color = (AutoCompleteTextView)view.findViewById(R.id.bark_inner_main_color);
        E8_bark_inner_sub_color = (AutoCompleteTextView)view.findViewById(R.id.bark_inner_sub_color);
        E8_sap = (EditText)view.findViewById(R.id.sapedit);

        //=============== Leave  ====================
        E8_leave_upper = (EditText)view.findViewById(R.id.leave_upper_edit);
        E8_leave_upper_main_color = (AutoCompleteTextView)view.findViewById(R.id.leave_upper_color_edit);
        E8_leave_upper_sub_color = (AutoCompleteTextView)view.findViewById(R.id.leave_upper_sub_color_edit);
        E8_leave_lower = (EditText)view.findViewById(R.id.leave_lower_edit);
        E8_leave_lower_main_color = (AutoCompleteTextView)view.findViewById(R.id.leave_lower_color_edit);
        E8_leave_lower_sub_color = (AutoCompleteTextView)view.findViewById(R.id.leave_lower_sub_color_edit);
        E8_LBF = (EditText)view.findViewById(R.id.lbf_color_edit);
        E8_LBF_main_color = (AutoCompleteTextView)view.findViewById(R.id.lbf_color_edit);
        E8_LBF_sub_color = (AutoCompleteTextView)view.findViewById(R.id.lbf_sub_color_edit);
        E8_YL = (EditText)view.findViewById(R.id.yledit);
        E8_YL_main_color = (AutoCompleteTextView)view.findViewById(R.id.yl_color_edit);
        E8_YL_sub_color = (AutoCompleteTextView)view.findViewById(R.id.yl_sub_color_edit);
        E8_leave_scent = (EditText)view.findViewById(R.id.leave_scentedit);

        //============ Flowers ================
        E8_sepals = (EditText)view.findViewById(R.id.sepals_edit);
        E8_sepals_main_color = (AutoCompleteTextView)view.findViewById(R.id.sepals_color_edit);
        E8_sepals_sub_color = (AutoCompleteTextView)view.findViewById(R.id.sepals_sub_color_edit);
        E8_petals = (EditText)view.findViewById(R.id.petals_edit);
        E8_petals_main_color = (AutoCompleteTextView)view.findViewById(R.id.petals_color_edit);
        E8_petals_sub_color = (AutoCompleteTextView)view.findViewById(R.id.petals_sub_color_edit);
        E8_tepals = (EditText)view.findViewById(R.id.tepals_edit);
        E8_tepals_main_color = (AutoCompleteTextView)view.findViewById(R.id.tepals_color_edit);
        E8_tepals_sub_color = (AutoCompleteTextView)view.findViewById(R.id.tepals_sub_color_edit);
        E8_stamens = (EditText)view.findViewById(R.id.stamens_edit);
        E8_stamens_main_color = (AutoCompleteTextView) view.findViewById(R.id.stamens_color_edit);
        E8_stamens_sub_color = (AutoCompleteTextView)view.findViewById(R.id.stamens_sub_color_edit);
        E8_pistils = (EditText)view.findViewById(R.id.pistils_edit);
        E8_pistils_main_color = (AutoCompleteTextView)view.findViewById(R.id.pistils_color_edit);
        E8_pistils_sub_color = (AutoCompleteTextView)view.findViewById(R.id.pistils_sub_color_edit);
        E8_flower_scent = (EditText)view.findViewById(R.id.flowerscentedit);

        //============ Fruits ==================
        E8_immature = (EditText)view.findViewById(R.id.immature_edit);
        E8_immature_main_color = (AutoCompleteTextView)view.findViewById(R.id.immature_color_edit);
        E8_immature_sub_color = (AutoCompleteTextView)view.findViewById(R.id.immature_sub_color_edit);
        E8_mature = (EditText)view.findViewById(R.id.mature_edit);
        E8_mature_main_color = (AutoCompleteTextView)view.findViewById(R.id.mature_color_edit);
        E8_mature_sub_color = (AutoCompleteTextView)view.findViewById(R.id.mature_sub_color_edit);
        E8_ripen = (EditText)view.findViewById(R.id.ripen_edit);
        E8_ripen_main_color = (AutoCompleteTextView)view.findViewById(R.id.ripen_color_edit);
        E8_ripen_sub_color =(AutoCompleteTextView)view.findViewById(R.id.ripen_sub_color_edit);
        E8_fruit_scent = (EditText)view.findViewById(R.id.fruitscentedit);


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

        plant_description = plantDescBark(plant_description);
        Log.d("outyo",plant_description);



        String note="";
        String[] all = {plant_description,note};
        return all;
    }
    public String plantDescBark(String plantdes){
        String temp = "";
        //============= Bark ============
        if( !Ehabit.getText().toString().trim().equals("") ){
            plantdes += "habit " + Ehabit.getText().toString() + "; ";
        }
        if( !E8_height.getText().toString().trim().equals("")  ){
            plantdes += E8_height.getText().toString() + " m tall; ";
        }
        if(!E8_outer_thick.getText().toString().trim().equals("")|| !E8_bark_outer_main_color.getText().toString().trim().equals("")||
           !E8_bark_outer.getText().toString().trim().equals("")|| !E8_bark_outer_sub_color.getText().toString().trim().equals("")){
             temp = E8_outer_thick.getText().toString().trim()+" "+E8_bark_outer_sub_color.getText().toString().trim()+" "+
                    E8_bark_outer_main_color.getText().toString().trim()+" "+E8_bark_outer.getText().toString().trim()+" outer bark";

            plantdes += temp.trim()+", ";
        }
        if( !E8_bark_inner.getText().toString().trim().equals("") || !E8_inner_thick.getText().toString().trim().equals("") ||
                !E8_bark_inner_main_color.getText().toString().trim().equals("") || !E8_bark_inner_sub_color.getText().toString().trim().equals("")){
            temp = E8_inner_thick.getText().toString().trim()+" "+E8_bark_inner_sub_color.getText().toString().trim()+" "+
                    E8_bark_inner_main_color.getText().toString().trim()+" "+E8_bark_inner.getText().toString().trim()+" inner bark";
            plantdes += temp.trim()+", ";
        }
        if( !E8_sap.getText().toString().trim().equals("") ){
            plantdes += E8_sap.getText().toString().trim()+" sap;";
        }
        plantdes = plantdes.trim();
        if(plantdes.length() != 0)
            plantdes = plantdes.substring(0,plantdes.length()-1)+";";

        //============ Leaves ==============
        if(!E8_leave_upper.getText().toString().trim().equals("") || !E8_leave_upper_main_color.getText().toString().trim().equals("") ||
        !E8_leave_upper_sub_color.getText().toString().trim().equals("")){
            temp = E8_leave_upper_sub_color.getText().toString().trim()+" "+E8_leave_upper_main_color.getText().toString().trim()+" "+
                    E8_leave_upper.getText().toString().trim()+" upper leaves";
            plantdes += temp.trim()+", ";
        }
        if(!E8_leave_lower.getText().toString().trim().equals("") || !E8_leave_lower_main_color.getText().toString().trim().equals("")||
                !E8_leave_lower_sub_color.getText().toString().trim().equals("") ){
            temp = E8_leave_lower_sub_color.getText().toString().trim()+" "+E8_leave_lower_main_color.getText().toString().trim()+" "+
                    E8_leave_lower.getText().toString().trim()+" lower leaves";
            plantdes += temp.trim()+", ";
        }
        if(!E8_LBF.getText().toString().trim().equals("") || !E8_LBF_main_color.getText().toString().trim().equals("")||
                !E8_LBF_sub_color.getText().toString().trim().equals("")){
            temp = E8_LBF_sub_color.getText().toString().trim()+" "+E8_LBF_main_color.getText().toString().trim()+" "+
                    E8_LBF.getText().toString().trim()+" leaves before fall";
            plantdes += temp.trim()+", ";
        }
        if(!E8_YL.getText().toString().trim().equals("") || !E8_YL_main_color.getText().toString().trim().equals("")||
                !E8_YL_sub_color.getText().toString().trim().equals("")){
            temp = E8_YL_sub_color.getText().toString().trim()+" "+E8_YL_main_color.getText().toString().trim()+" "+
                    E8_YL.getText().toString().trim()+" young leaves";
            plantdes += temp.trim()+", ";
        }
        if(!E8_leave_scent.getText().toString().trim().equals("") ){
            plantdes += E8_leave_scent.getText().toString().trim()+" ;";
        }
        plantdes = plantdes.trim();
        if(plantdes.length() != 0)
            plantdes = plantdes.substring(0,plantdes.length()-1)+";";

        //============ Flowers ===========
        if(!E8_sepals.getText().toString().trim().equals("") || !E8_sepals_main_color.getText().toString().trim().equals("") ||
                !E8_sepals_sub_color.getText().toString().trim().equals("") ){
            temp = E8_sepals_sub_color.getText().toString().trim()+" "+E8_sepals_main_color.getText().toString().trim()+" "+
                    E8_sepals.getText().toString().trim()+" sepals";
            plantdes += temp.trim()+", ";
        }
        if(!E8_petals.getText().toString().trim().equals("")||!E8_petals_main_color.getText().toString().trim().equals("")||
                !E8_petals_sub_color.getText().toString().trim().equals("")){
            temp = E8_petals_sub_color.getText().toString().trim()+" "+E8_petals_main_color.getText().toString().trim()+" "+
                    E8_petals.getText().toString().trim()+" petals";
            plantdes += temp.trim()+", ";
        }
        if(!E8_tepals.getText().toString().trim().equals("")||!E8_tepals_main_color.getText().toString().trim().equals("")||
                !E8_tepals_sub_color.getText().toString().trim().equals("") ){
            temp = E8_tepals_sub_color.getText().toString().trim()+" "+E8_tepals_main_color.getText().toString().trim()+" "+
                    E8_tepals.getText().toString().trim()+" tepals";
            plantdes += temp.trim()+", ";
        }
        if(!E8_stamens.getText().toString().trim().equals("")||!E8_stamens_main_color.getText().toString().trim().equals("")||
                !E8_stamens_sub_color.getText().toString().trim().equals("") ){
            temp = E8_stamens_sub_color.getText().toString().trim()+" "+E8_stamens_main_color.getText().toString().trim()+" "+
                    E8_stamens.getText().toString().trim()+" stamens";
            plantdes += temp.trim()+", ";
        }
        if(!E8_pistils.getText().toString().trim().equals("")||!E8_pistils_main_color.getText().toString().trim().equals("")||
                !E8_pistils_sub_color.getText().toString().trim().equals("") ){
            temp = E8_pistils_sub_color.getText().toString().trim()+" "+E8_pistils_main_color.getText().toString().trim()+" "+
                    E8_pistils.getText().toString().trim()+" pistils";
            plantdes += temp.trim()+", ";
        }
        if(!E8_flower_scent.getText().toString().trim().equals("")){
            plantdes += E8_flower_scent.getText().toString().trim()+" ;";
        }
        plantdes = plantdes.trim();
        if(plantdes.length() != 0)
            plantdes = plantdes.substring(0,plantdes.length()-1)+";";

        //============ Fruit =================
        if(!E8_immature.getText().toString().trim().equals("")||!E8_immature_main_color.getText().toString().trim().equals("")||
                !E8_immature_sub_color.getText().toString().trim().equals("")){
            temp = E8_immature_sub_color.getText().toString().trim()+" "+E8_immature_main_color.getText().toString().trim()+" "+
                    E8_immature.getText().toString().trim()+" immature fruit";
            plantdes += temp.trim()+", ";
        }
        if(!E8_mature.getText().toString().trim().equals("")||!E8_mature_main_color.getText().toString().trim().equals("")||
                !E8_mature_sub_color.getText().toString().trim().equals("") ){
            temp = E8_mature_sub_color.getText().toString().trim()+" "+E8_mature_main_color.getText().toString().trim()+" "+
                    E8_mature.getText().toString().trim()+" mature fruit";
            plantdes += temp.trim()+", ";
        }
        if(!E8_ripen.getText().toString().trim().equals("")||!E8_ripen_main_color.getText().toString().trim().equals("")||
                !E8_ripen_sub_color.getText().toString().trim().equals("") ){
            temp = E8_ripen_sub_color.getText().toString().trim()+" "+E8_ripen_main_color.getText().toString().trim()+" "+
                    E8_ripen.getText().toString().trim()+" ripen fruit";
            plantdes += temp.trim()+", ";
        }
        if(!E8_fruit_scent.getText().toString().trim().equals("") ){
            plantdes += E8_fruit_scent.getText().toString().trim()+" ;";
        }
        plantdes = plantdes.trim();
        if(plantdes.length() != 0)
            plantdes = plantdes.substring(0,plantdes.length()-1)+";";

        return plantdes;
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