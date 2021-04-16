package com.example.smarttravelguide.model;


public class BookedRoomDto {
    private int roomId;
    private String packageType;
    private int roomsBooked;
    private String paymentMethod;
    private String checkInDate;
    private String checkOutDate;
    private int bookedBy;
    private String totalPrice;
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
    private String roomType;
    private int sleepCount;
    private String totalRooms;

    public BookedRoomDto(int roomId, String packageType, int roomsBooked, String paymentMethod, String checkInDate, String checkOutDate, int bookedBy, String totalPrice, int hotelId, String hotelName, String location, String price, String description, String picturePath, Room room, boolean breakFastIncluded, float latitude, float longitude, String roomType, int sleepCount, String totalRooms) {
        this.roomId = roomId;
        this.packageType = packageType;
        this.roomsBooked = roomsBooked;
        this.paymentMethod = paymentMethod;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.bookedBy = bookedBy;
        this.totalPrice = totalPrice;
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
        this.roomType = roomType;
        this.sleepCount = sleepCount;
        this.totalRooms = totalRooms;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getPackageType() {
        return packageType;
    }

    public void setPackageType(String packageType) {
        this.packageType = packageType;
    }

    public int getRoomsBooked() {
        return roomsBooked;
    }

    public void setRoomsBooked(int roomsBooked) {
        this.roomsBooked = roomsBooked;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(String checkInDate) {
        this.checkInDate = checkInDate;
    }

    public String getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(String checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public int getBookedBy() {
        return bookedBy;
    }

    public void setBookedBy(int bookedBy) {
        this.bookedBy = bookedBy;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

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

    public String getTotalRooms() {
        return totalRooms;
    }

    public void setTotalRooms(String totalRooms) {
        this.totalRooms = totalRooms;
    }


}
