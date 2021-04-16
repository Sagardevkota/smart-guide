package com.example.smarttravelguide.model;

public class RoomBook {
    private int roomId;
    private String packageType;
    private int roomsBooked;
    private String paymentMethod;
    private String checkInDate;
    private String checkOutDate;
    private int bookedBy;
    private String totalPrice;


    public RoomBook(int roomId, String packageType, int roomsBooked, String paymentMethod, String checkInDate, String checkOutDate, int bookedBy, String totalPrice) {
        this.roomId = roomId;
        this.packageType = packageType;
        this.roomsBooked = roomsBooked;
        this.paymentMethod = paymentMethod;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.bookedBy = bookedBy;
        this.totalPrice = totalPrice;
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

    @Override
    public String toString() {
        return
                "RoomsBooked: " + roomsBooked + "\n \n "+
                "PaymentMethod: " + paymentMethod  + "\n \n"+
                "CheckInDate: " + checkInDate + "\n \n "+
                "CheckOutDate: " + checkOutDate + "\n \n "+
                "TotalPrice: " + totalPrice ;
    }
}
