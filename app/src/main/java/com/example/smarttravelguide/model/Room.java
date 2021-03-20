package com.example.smarttravelguide.model;

public class Room {
    private String roomType;
    private int sleepCount;
    private String price;
    private int roomNumber;

    public Room(String roomType, int sleepCount, String price, int roomNumber) {
        this.roomType = roomType;
        this.sleepCount = sleepCount;
        this.price = price;
        this.roomNumber = roomNumber;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public int getSleepCount() {
        return sleepCount;
    }

    public void setSleepCount(int sleepCount) {
        this.sleepCount = sleepCount;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }
}
