package com.example.smarttravelguide.model;

public class BookedPlaceDto {


    private int placeId;
    private String packageType;
    private int quantity;
    private String date;
    private String price;
    private int bookedBy;

    private String placeName;
    private String location;
    private String time;
    private String expense;
    private String transport;
    private String description;
    private String picturePath;
    private String type;
    private float longitude;
    private float latitude;

    public BookedPlaceDto(int placeId, String packageType, int quantity, String date, String price, int bookedBy, String placeName, String location, String time, String expense, String transport, String description, String picturePath, String type, float longitude, float latitude) {
        this.placeId = placeId;
        this.packageType = packageType;
        this.quantity = quantity;
        this.date = date;
        this.price = price;
        this.bookedBy = bookedBy;
        this.placeName = placeName;
        this.location = location;
        this.time = time;
        this.expense = expense;
        this.transport = transport;
        this.description = description;
        this.picturePath = picturePath;
        this.type = type;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public int getPlaceId() {
        return placeId;
    }

    public void setPlaceId(int placeId) {
        this.placeId = placeId;
    }

    public String getPackageType() {
        return packageType;
    }

    public void setPackageType(String packageType) {
        this.packageType = packageType;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getBookedBy() {
        return bookedBy;
    }

    public void setBookedBy(int bookedBy) {
        this.bookedBy = bookedBy;
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

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }
}
