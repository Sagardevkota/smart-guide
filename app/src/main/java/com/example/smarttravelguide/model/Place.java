package com.example.smarttravelguide.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Place implements Parcelable {
    private int placeId;
    private String placeName;
    private String location;
    private String time;
    private String expense;
    private String transport;
    private String description;
    private String picturePath;
    private String type;

    public Place(int placeId, String placeName, String location, String time, String expense, String transport, String description, String picturePath, String type) {
        this.placeId = placeId;
        this.placeName = placeName;
        this.location = location;
        this.time = time;
        this.expense = expense;
        this.transport = transport;
        this.description = description;
        this.picturePath = picturePath;
        this.type = type;
    }

    protected Place(Parcel in) {
        placeId = in.readInt();
        placeName = in.readString();
        location = in.readString();
        time = in.readString();
        expense = in.readString();
        transport = in.readString();
        description = in.readString();
        picturePath = in.readString();
        type = in.readString();
    }

    public static final Creator<Place> CREATOR = new Creator<Place>() {
        @Override
        public Place createFromParcel(Parcel in) {
            return new Place(in);
        }

        @Override
        public Place[] newArray(int size) {
            return new Place[size];
        }
    };

    public int getPlaceId() {
        return placeId;
    }

    public void setPlaceId(int placeId) {
        this.placeId = placeId;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getExpense() {
        return expense;
    }

    public void setExpense(String expense) {
        this.expense = expense;
    }

    public String getTransport() {
        return transport;
    }

    public void setTransport(String transport) {
        this.transport = transport;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(placeId);
        dest.writeString(placeName);
        dest.writeString(location);
        dest.writeString(time);
        dest.writeString(expense);
        dest.writeString(transport);
        dest.writeString(description);
        dest.writeString(picturePath);
        dest.writeString(type);
    }
}
