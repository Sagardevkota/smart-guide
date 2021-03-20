package com.example.smarttravelguide.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Hotel implements Parcelable {
    private int hotelId;
    private String hotelName;
    private String location;
    private String price;
    private String description;
    private String picturePath;
    private Room room;
    private boolean breakFastIncluded;




    //for hotel adapter list
    public Hotel(int hotelId,String hotelName, String location, String price, String description, String picturePath, Boolean breakFastIncluded) {
        this.hotelId = hotelId;
        this.hotelName = hotelName;
        this.location = location;
        this.price = price;
        this.description = description;
        this.picturePath = picturePath;
        this.breakFastIncluded = breakFastIncluded;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public boolean isBreakFastIncluded() {
        return breakFastIncluded;
    }

    public void setBreakFastIncluded(boolean breakFastIncluded) {
        this.breakFastIncluded = breakFastIncluded;
    }

    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
