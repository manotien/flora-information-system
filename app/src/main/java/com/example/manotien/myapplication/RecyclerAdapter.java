package com.example.manotien.myapplication;

/**
 * Created by manotien on 17/3/2559.
 */
import android.content.Context;
import android.content.Intent;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;


import java.util.List;

import static android.database.DatabaseUtils.dumpCursorToString;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {

    DbOperator dbOperator;
    SQLiteDatabase sqLiteDatabase;
    Context context1;
    private List<FloraData> floraList;
    LayoutInflater inflater;
    public RecyclerAdapter(Context context,List<FloraData> floraList) {
        context1 = context;
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
    public void onBindViewHolder(final RecyclerViewHolder holder, final int position) {
        dbOperator = new DbOperator(context1);
        sqLiteDatabase = dbOperator.getReadableDatabase();

        final FloraData fd = floraList.get(position);
        holder.flora_name.setText(fd.name);
        holder.genus.setText(fd.genus);
        //holder.family
       // holder.imageView.setOnClickListener(clickListener);
       // holder.imageView.setTag(holder);

        holder.setting.setTag(position);
        holder.setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PopupMenu popup = new PopupMenu(context1, holder.setting);
                popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        if(item.getTitle().equals("Delete")){
                            floraList.remove(position);
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position, floraList.size());
                            dbOperator.deleteFlora(sqLiteDatabase, fd.getId());
                        }
                        else
                        {

                        }
                        return true;
                    }
                });
                popup.show();
            }
        });
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