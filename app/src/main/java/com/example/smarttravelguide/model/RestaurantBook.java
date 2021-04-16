package com.example.smarttravelguide.model;

public class RestaurantBook {

    private int restaurantId;
    private int partyCount;
    private String date;
    private String time;
    private int bookedBy;

    public RestaurantBook(int restaurantId, int partyCount, String date, String time, int bookedBy) {
        this.restaurantId = restaurantId;
        this.partyCount = partyCount;
        this.date = date;
        this.time = time;
        this.bookedBy = bookedBy;
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
