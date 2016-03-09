package com.example.manotien.myapplication;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.TextView;

public class third extends Fragment {

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
        View view =inflater.inflate(R.layout.fragment_third, container, false);

        tab_discover activity = (tab_discover) getActivity();
        String strtext = activity.getMydata();
        TextView str = (TextView) view.findViewById(R.id.aaa);
        str.setText(strtext);
        return view;
    }

}
