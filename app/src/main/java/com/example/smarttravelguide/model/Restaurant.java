package com.example.smarttravelguide.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Restaurant implements Parcelable {
    private int id;
    private String name;
    private String location;
    private Double latitude;
    private Double longitude;
    private String picturePath;
    private String description;
    private int totalPartySize;

    public Restaurant(int id, String name, String location, Double latitude, Double longitude, String picturePath, String description, int totalPartySize) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.latitude = latitude;
        this.longitude = longitude;
        this.picturePath = picturePath;
        this.description = description;
        this.totalPartySize = totalPartySize;
    }

    protected Restaurant(Parcel in) {
        id = in.readInt();
        name = in.readString();
        location = in.readString();
        if (in.readByte() == 0) {
            latitude = null;
        } else {
            latitude = in.readDouble();
        }
        if (in.readByte() == 0) {
            longitude = null;
        } else {
            longitude = in.readDouble();
        }
        picturePath = in.readString();
        description = in.readString();
        totalPartySize = in.readInt();
    }

    public static final Creator<Restaurant> CREATOR = new Creator<Restaurant>() {
        @Override
        public Restaurant createFromParcel(Parcel in) {
            return new Restaurant(in);
        }

        @Override
        public Restaurant[] newArray(int size) {
            return new Restaurant[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTotalPartySize() {
        return totalPartySize;
    }

    public void setTotalPartySize(int totalPartySize) {
        this.totalPartySize = totalPartySize;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(location);
        if (latitude == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(latitude);
        }
        if (longitude == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(longitude);
        }
        dest.writeString(picturePath);
        dest.writeString(description);
        dest.writeInt(totalPartySize);
    }
}
