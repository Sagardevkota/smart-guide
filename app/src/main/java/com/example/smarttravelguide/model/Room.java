package com.example.smarttravelguide.model;

public class Room {
    private int roomId;
    private int hotelId;
    private String roomType;
    private int sleepCount;
    private String price;
    private String totalRooms;


    public Room(int roomId, int hotelId, String roomType, int sleepCount, String price, String totalRooms) {
        this.roomId = roomId;
        this.hotelId = hotelId;
        this.roomType = roomType;
        this.sleepCount = sleepCount;
        this.price = price;
        this.totalRooms = totalRooms;

    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }

    public void setTotalRooms(String totalRooms) {
        this.totalRooms = totalRooms;
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

    public String getTotalRooms() {
        return totalRooms;
    }

}
