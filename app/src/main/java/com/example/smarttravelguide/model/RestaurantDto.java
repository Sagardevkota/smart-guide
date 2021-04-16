package com.example.smarttravelguide.model;

public class RestaurantDto {

    private String name;
    private String location;
    private Double latitude;
    private Double longitude;
    private String picturePath;
    private String description;
    private int totalPartySize;
    private int restaurantId;
    private int partyCount;
    private String date;
    private String time;
    private int bookedBy;

    public RestaurantDto(String name, String location, Double latitude, Double longitude, String picturePath, String description, int totalPartySize, int restaurantId, int partyCount, String date, String time, int bookedBy) {
        this.name = name;
        this.location = location;
        this.latitude = latitude;
        this.longitude = longitude;
        this.picturePath = picturePath;
        this.description = description;
        this.totalPartySize = totalPartySize;
        this.restaurantId = restaurantId;
        this.partyCount = partyCount;
        this.date = date;
        this.time = time;
        this.bookedBy = bookedBy;
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

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    public int getPartyCount() {
        return partyCount;
    }

    public void setPartyCount(int partyCount) {
        this.partyCount = partyCount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getBookedBy() {
        return bookedBy;
    }

    public void setBookedBy(int bookedBy) {
        this.bookedBy = bookedBy;
    }
}
