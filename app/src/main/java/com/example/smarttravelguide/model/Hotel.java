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
    private float latitude;
    private float longitude;


    public Hotel(int hotelId, String hotelName, String location, String price, String description, String picturePath, Room room, boolean breakFastIncluded, float latitude, float longitude) {
        this.hotelId = hotelId;
        this.hotelName = hotelName;
        this.location = location;
        this.price = price;
        this.description = description;
        this.picturePath = picturePath;
        this.room = room;
        this.breakFastIncluded = breakFastIncluded;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    protected Hotel(Parcel in) {
        hotelId = in.readInt();
        hotelName = in.readString();
        location = in.readString();
        price = in.readString();
        description = in.readString();
        picturePath = in.readString();
        breakFastIncluded = in.readByte() != 0;
        latitude = in.readFloat();
        longitude = in.readFloat();
    }

    public static final Creator<Hotel> CREATOR = new Creator<Hotel>() {
        @Override
        public Hotel createFromParcel(Parcel in) {
            return new Hotel(in);
        }

        @Override
        public Hotel[] newArray(int size) {
            return new Hotel[size];
        }
    };

    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
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

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(hotelId);
        dest.writeString(hotelName);
        dest.writeString(location);
        dest.writeString(price);
        dest.writeString(description);
        dest.writeString(picturePath);
        dest.writeByte((byte) (breakFastIncluded ? 1 : 0));
        dest.writeFloat(latitude);
        dest.writeFloat(longitude);
    }
}
