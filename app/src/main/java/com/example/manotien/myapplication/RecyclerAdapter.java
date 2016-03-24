package com.example.manotien.myapplication;

/**
 * Created by manotien on 17/3/2559.
 */
import android.content.Context;
import android.content.Intent;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.List;

import static android.database.DatabaseUtils.dumpCursorToString;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {

   // Context context;
    private List<FloraData> floraList;
    LayoutInflater inflater;
    public RecyclerAdapter(Context context,List<FloraData> floraList) {
        inflater=LayoutInflater.from(context);
        this.floraList = floraList;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v=inflater.inflate(R.layout.item_list, parent, false);
        RecyclerViewHolder viewHolder=new RecyclerViewHolder(v);
        v.setOnClickListener(clickListener);
        v.setTag(viewHolder);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        FloraData fd = floraList.get(position);
        holder.flora_name.setText(fd.name);
        holder.genus.setText(fd.genus);
        //holder.family
       // holder.imageView.setOnClickListener(clickListener);
       // holder.imageView.setTag(holder);
    }

    View.OnClickListener clickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            RecyclerViewHolder vholder = (RecyclerViewHolder) v.getTag();
            int position = vholder.getPosition();
            FloraData fd = floraList.get(position);
            Intent intent = new Intent(v.getContext(), Flora_Information.class);
            intent.putExtra("flora_id",fd.flora_id);
            v.getContext().startActivity(intent);

        }
    };

    @Override
    public int getItemCount() {
        return floraList.size();
    }
}