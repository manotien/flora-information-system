package com.example.manotien.myapplication;

import android.app.Activity;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import yuku.ambilwarna.AmbilWarnaDialog;

public class second extends Fragment {
    TextView text1;
    int color = 0xffffff00;

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
        Button buttongps = (Button) view.findViewById(R.id.getgps);
        buttongps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(true,"aaa");
            }
        });


        return view;
    }

    void openDialog(boolean supportsAlpha, final String id_text) {
        AmbilWarnaDialog dialog = new AmbilWarnaDialog(getActivity(), color, supportsAlpha, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                Toast.makeText(getActivity(), "ok", Toast.LENGTH_SHORT).show();
                second.this.color = color;
                displayColor(id_text);
            }

            @Override
            public void onCancel(AmbilWarnaDialog dialog) {
                Toast.makeText(getActivity(), "cancel", Toast.LENGTH_SHORT).show();
            }
        });
        dialog.show();
    }
    void displayColor(String id) {
        int resId = getResources().getIdentifier(id,"id","com.example.manotien.myapplication");
        text1 = (TextView) getView().findViewById(resId);
        text1.setText(String.format("Current color: 0x%08x", color));
    }
}
