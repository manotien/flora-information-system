package com.example.manotien.myapplication;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
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
    private static final MediaType MEDIA_TYPE_JPG = MediaType.parse("image/jpg");
    Cursor cursor;
    public String send(Context context,String url,String location_id_web,String location_id_app) throws JSONException {

        OkHttpClient client = new OkHttpClient();

        dbOperator = new DbOperator(context);
        sqLiteDatabase = dbOperator.getReadableDatabase();
        cursor = dbOperator.GetAllFlora(sqLiteDatabase,location_id_app);
        String[] columnNames = cursor.getColumnNames();
        JSONArray floraAll = new JSONArray();
        if(cursor.moveToFirst()){

            do {
                JSONObject flora = new JSONObject();
                //location id
                for(int i=0;i<columnNames.length;i++)
                {
                    if(i!=1)
                        flora.put(columnNames[i], cursor.getString(i));
                    else
                        flora.put(columnNames[1], location_id_web);
                }

                floraAll.put(flora);

            } while (cursor.moveToNext());

            cursor.close();
        }
        //Log.d("showthat",String.valueOf(cursor.getCount()));
        if(cursor.getCount()!=0){
            RequestBody formBody = new FormBody.Builder()
                    .add("flora",String.valueOf(floraAll))
                    .build();
            Request request = new Request.Builder()
                    .url(url)
                    .post(formBody)
                    .build();
            try {
                Response response = client.newCall(request).execute();
                String res= response.body().string();
                String response_str = res.substring(1, res.length() - 1);

                String [] flora_id_server = response_str.split(",");
                int [] flora_int = new int[flora_id_server.length];
                for(int i = 0;i<flora_id_server.length;i++){
                    flora_int[i] = Integer.valueOf(flora_id_server[i]);
                }
                for (int i=0;i<floraAll.length();i++){
                    String app_id = floraAll.getJSONObject(i).getString("id");
                    cursor = dbOperator.GetPhoto(sqLiteDatabase, app_id);
                    if(cursor.moveToFirst()){

                        do {
                            String flora_name = cursor.getString(2).split("Flora")[1].substring(1);
                            String flora_path = cursor.getString(2);
                            sendPhoto(flora_name, String.valueOf(flora_int[i]));


                        } while (cursor.moveToNext());

                        cursor.close();
                    }

                }

                return res;
                // Do something with the response.
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return "[]";
    }
    public boolean sendPhoto(String file_name,String flora_id){
        OkHttpClient client_photo = new OkHttpClient();
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("title", file_name)
                .addFormDataPart("id", flora_id)
                .addFormDataPart("image", file_name, RequestBody.create(MEDIA_TYPE_JPG, new File(Environment.getExternalStorageDirectory(), "DCIM/Flora/"+file_name)))
                .build();

        Request request_flora = new Request.Builder()
                .url("http://192.168.1.2:8080/flora/create/photo")
                .post(requestBody)
                .build();


        try {
            Response response_flora = client_photo.newCall(request_flora).execute();
            Log.d("testaa","end"+response_flora.body().string());
            if (!response_flora.isSuccessful()) throw new IOException("Unexpected code " + response_flora);
            return true;

        } catch (IOException e) {
            Log.d("testaa",e.toString());
            e.printStackTrace();
        }
        return false;
    }

}
