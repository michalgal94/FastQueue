package com.example.fastqueue;

public class BusinessAddress extends BusinessMan {

    private double latitude;
    private double longitude;


    public BusinessAddress() {

    }

    public BusinessAddress(String username, String email, String password, String verify, String phone, boolean isClient, String businessName, String businessAddress, double latitude, double longitude) {
        super(username, email, password, verify, phone, isClient, businessName, businessAddress);
        setLatitude(latitude);
        setLongitude(longitude);
    }


    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    }

