package com.example.manotien.myapplication;

/**
 * Created by manotien on 17/3/2559.
 */
import android.content.Context;
import android.content.Intent;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;


import java.io.FileNotFoundException;
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

        final FloraData fd = floraList.get(position);

        Cursor cursor;

        dbOperator = new DbOperator(context1);
        sqLiteDatabase = dbOperator.getReadableDatabase();
        cursor = dbOperator.GetPhoto(sqLiteDatabase, String.valueOf(fd.getId()));
        if(cursor.moveToFirst()){

            Bitmap bitmap = getPhoto(Uri.parse(cursor.getString(2)));
            holder.imageView.setImageBitmap(bitmap);

           // Drawable d = new BitmapDrawable(context1.getResources(), bitmap);

        }
        cursor.close();
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
    public Bitmap getPhoto(Uri uri){
        final BitmapFactory.Options options = new BitmapFactory.Options();

        Bitmap bitmap=null;
        options.inJustDecodeBounds = true;
        try {
            BitmapFactory.decodeStream(context1.getContentResolver().openInputStream(uri), null, options);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options,70, 75);
        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        try {
            bitmap = BitmapFactory.decodeStream(context1.getContentResolver().openInputStream(uri), null, options);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }
}