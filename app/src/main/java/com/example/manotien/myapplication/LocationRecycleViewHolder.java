package com.example.manotien.myapplication;

/**
 * Created by manotien on 17/3/2559.
 */
        import android.support.v7.widget.RecyclerView;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ImageView;
        import android.widget.TextView;

public class LocationRecycleViewHolder extends RecyclerView.ViewHolder {

    TextView place,protect,date,collector;


    public LocationRecycleViewHolder(View itemView) {
        super(itemView);

        place= (TextView) itemView.findViewById(R.id.place);
        protect= (TextView) itemView.findViewById(R.id.protect);
        date= (TextView) itemView.findViewById(R.id.date);
        collector= (TextView) itemView.findViewById(R.id.collector);

    }
}
