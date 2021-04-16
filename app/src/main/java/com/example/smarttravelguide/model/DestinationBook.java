package com.example.smarttravelguide.model;

public class DestinationBook {

    private int placeId;
    private String packageType;
    private int quantity;
    private String date;
    private String price;
    private int bookedBy;

    public DestinationBook(int placeId, String packageType, int quantity, String date, String price, int bookedBy) {
        this.placeId = placeId;
        this.packageType = packageType;
        this.quantity = quantity;
        this.date = date;
        this.price = price;
        this.bookedBy = bookedBy;
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

    @Override
    public String toString() {
        return "DestinationBook{" +
                ", packageType='" + packageType + '\'' +
                ", quantity=" + quantity +
                ", date='" + date + '\'' +
                ", price='" + price + '\'' +

                '}';
    }
}
