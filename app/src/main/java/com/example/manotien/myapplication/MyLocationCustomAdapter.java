package com.example.manotien.myapplication;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by manotien on 11/4/2559.
 */
public class MyLocationCustomAdapter extends ArrayAdapter<LocationData> {

    private ArrayList<LocationData> locationList;

    public MyLocationCustomAdapter(Context context, int textViewResourceId,
                           ArrayList<LocationData> locationList) {
        super(context, textViewResourceId, locationList);
        this.locationList = new ArrayList<LocationData>();
        this.locationList.addAll(locationList);
    }

    private class ViewHolder {
        TextView date;
        CheckBox placename;
        TextView isExport;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;

        if (convertView == null) {
            LayoutInflater vi = (LayoutInflater)this.getContext().getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.location_listview, null);

            holder = new ViewHolder();
            holder.date = (TextView) convertView.findViewById(R.id.date);
            holder.placename = (CheckBox) convertView.findViewById(R.id.checkBox1);
            holder.isExport = (TextView)convertView.findViewById(R.id.isexport);
            convertView.setTag(holder);

            holder.placename.setOnClickListener( new View.OnClickListener() {
                public void onClick(View v) {
                    CheckBox cb = (CheckBox) v ;
                    LocationData country = (LocationData) cb.getTag();
                    country.setSelected(cb.isChecked());
                }
            });
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        LocationData country = locationList.get(position);
        holder.placename.setText(country.getPlace());
        holder.placename.setChecked(country.isSelected());
        holder.placename.setTag(country);
        holder.date.setText(country.getDate());

        if(country.getisExport()==1) {
            holder.isExport.setText("(Exported)");
        }
        else {
            Log.d("istestz",String.valueOf(country.getisExport()));
            holder.isExport.setText("(Not Export)");
        }
        return convertView;

    }

}
