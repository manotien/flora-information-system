package com.example.manotien.myapplication;

/**
 * Created by manotien on 23/3/2559.
 */
public class LocationData {
    protected String place;
    protected String protect;
    protected String date;
    protected int location_id;
    protected String collector;
    protected boolean selected = false;
    protected int isExport ;
    public LocationData(){
    }
    public LocationData(String place,String date,int location_id,int isExport){
        this.place = place;
        this.location_id = location_id;
        this.date = date;
        this.isExport = isExport;
    }
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isSelected(){
        return selected;
    }
    public int getLocationId(){
        return location_id;
    }
    public String getPlace() {
        return place;
    }
    public String getDate(){
        return date;
    }
    public int getisExport(){
        return isExport;
    }
}
