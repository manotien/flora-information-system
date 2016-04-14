package com.example.manotien.myapplication;

/**
 * Created by manotien on 17/3/2559.
 */
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class RecyclerViewHolder extends RecyclerView.ViewHolder {

    TextView flora_name,genus;
    ImageView imageView;
    ImageButton setting;

    public RecyclerViewHolder(View itemView) {
        super(itemView);

        flora_name= (TextView) itemView.findViewById(R.id.flora_name);
        genus= (TextView) itemView.findViewById(R.id.flora_genus);
        imageView= (ImageView) itemView.findViewById(R.id.flora_photo);
        setting = (ImageButton)itemView.findViewById(R.id.setting);
    }
}
