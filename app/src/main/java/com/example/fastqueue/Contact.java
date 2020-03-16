package com.example.fastqueue;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Contact {

    private String name;
    private String email;
    private String id;
    private String phone_number;



    public Contact() {
    }



    public Contact(String name, String email, String id, String phone_number) {
        setName(name);
        setEmail(email);
        setId(id);
        setPhone_number(phone_number);


    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



}
