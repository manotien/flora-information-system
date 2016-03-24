package com.example.manotien.myapplication;

/**
 * Created by manotien on 17/3/2559.
 */
import android.content.Context;
import android.content.Intent;

import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;


public class LocationRecycleAdapter extends RecyclerView.Adapter<LocationRecycleViewHolder> {

    Context context;
    private List<LocationData> locationList;
    LayoutInflater inflater;
    public LocationRecycleAdapter(Context context,List<LocationData> locationlist) {
        inflater=LayoutInflater.from(context);
        this.locationList = locationlist;
    }

    @Override
    public LocationRecycleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v=inflater.inflate(R.layout.location_itemlist, parent, false);
        LocationRecycleViewHolder viewHolder=new LocationRecycleViewHolder(v);
        v.setOnClickListener(clickListener);
        v.setTag(viewHolder);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(LocationRecycleViewHolder holder, int position) {
        LocationData ld = locationList.get(position);
        holder.place.setText("Place: "+ld.place);
        holder.protect.setText("Protected: "+ ld.protect);
        holder.date.setText(ld.date);
        holder.collector.setText(ld.collector);

    }

    View.OnClickListener clickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            LocationRecycleViewHolder vholder = (LocationRecycleViewHolder) v.getTag();
            int position = vholder.getPosition();
            LocationData ld = locationList.get(position);
            Intent intent = new Intent(v.getContext(), Survey_Main.class);
            String []date = ld.date.split("/");

            SharedPreferences sp = v.getContext().getSharedPreferences("place_date", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("place_name", ld.place);
            editor.putString("day",date[0] );
            editor.putString("month",date[1]);
            editor.putString("year",date[2]);
            editor.commit();

            v.getContext().startActivity(intent);

        }
    };

    @Override
    public int getItemCount() {
        return locationList.size();
    }
}