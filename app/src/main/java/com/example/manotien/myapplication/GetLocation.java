package com.example.manotien.myapplication;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;

import android.support.annotation.Nullable;


public class GetLocation extends Service implements LocationListener {

    private final Context context;
    boolean isGPSEnabled =false;
    boolean isNetworkEnabled =false;
    boolean canGetLocation=false;
    double lat;
    double lng;
    Location location;
    private static final long MIN_DISTANCE_CHANGED_FOR_UPDATES = 10;
    private static final long MIN_TIME_BW_UPDATES = 1000*60*1;
    protected LocationManager locationManager;

    public GetLocation(Context context){
        this.context=context;
        getLocation();

    }

    public Location getLocation(){

        try{
            locationManager = (LocationManager)context.getSystemService(LOCATION_SERVICE);
            isGPSEnabled =locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            isNetworkEnabled =locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            if(!isGPSEnabled && !isNetworkEnabled){

            }else {
                this.canGetLocation = true;
                if (isNetworkEnabled) {
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGED_FOR_UPDATES, this);

                    if (locationManager == null) {
                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {
                            lat = location.getLatitude();
                            lng = location.getLongitude();
                        }
                    }
                }
                if(isGPSEnabled){
                    if(location==null){
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,MIN_TIME_BW_UPDATES,MIN_DISTANCE_CHANGED_FOR_UPDATES,this);
                        if(locationManager!=null){
                            location=locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                            if(location!=null){
                                lat = location.getLatitude();
                                lng = location.getLongitude();
                            }

                        }
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return location;
    }

    public void stopUsingGPS(){
        if(locationManager!=null){
            locationManager.removeUpdates(GetLocation.this);
        }
    }

    public double getLat(){
        if(location!=null){
            lat=location.getLatitude();
        }
        return lat;
    }

    public double getLng(){
        if(location!=null){
            lng=location.getLongitude();
        }
        return lng;
    }

    public boolean canGetLocation(){
        return this.canGetLocation;
    }

    public void showSettingAlert(){
        AlertDialog.Builder alertDialog= new AlertDialog.Builder(context);
    }


    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
