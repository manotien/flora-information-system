package com.example.manotien.myapplication;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by manotien on 15/4/2559.
 */
public class sendPostLocation {
    DbOperator dbOperator;
    SQLiteDatabase sqLiteDatabase;
    Cursor cursor;
    public boolean send(Context context,String url,JSONObject location) {
        OkHttpClient client = new OkHttpClient();

        dbOperator = new DbOperator(context);
        sqLiteDatabase = dbOperator.getReadableDatabase();
        cursor = dbOperator.GetAllFlora(sqLiteDatabase);

        JSONArray floraAll = new JSONArray();
        if(cursor.moveToFirst()){
            String[] columnNames = cursor.getColumnNames();
            JSONObject flora = new JSONObject();
            do {
                for(int i=1;i<columnNames.length;i++)
                {
                    try {
                        flora.put(columnNames[i], cursor.getString(i));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                floraAll.put(flora);
            } while (cursor.moveToNext());

            cursor.close();
        }

        RequestBody formBody = new FormBody.Builder()
                .add("location",String.valueOf(location) )
                .add("flora",String.valueOf(floraAll))
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        try {
            Response response = client.newCall(request).execute();


            Log.d("showthat",String.valueOf(response.body().string()));
            return true;
            // Do something with the response.
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
