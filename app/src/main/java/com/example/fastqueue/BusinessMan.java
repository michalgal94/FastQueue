package com.example.fastqueue;

import com.alamkanak.weekview.WeekViewEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BusinessMan extends User {

    private String businessName;
    private String businessAddress;
    private ActivityTime activityTime;
    private List<Type> types;
    private ArrayList<Contact> clientsList;
    private ArrayList<WeekViewEvent> eventsList;

    public BusinessMan() {
    }

    public BusinessMan(String username, String email, String password, String verify, String phone, boolean isClient ,String businessName, String businessAddress) {
        super(username, email, password, verify, phone, isClient);
        setBusinessName(businessName);
        setBusinessAddress(businessAddress);
        setActivityTime(new ActivityTime("", "", "", "", ""));
        types = new ArrayList<>();
        setClientsList(new ArrayList<Contact>());
        setEventsList(new ArrayList<WeekViewEvent>());

    }

    @Override
    public boolean isClient() {
        return super.isClient();
    }

    @Override
    public void setClient(boolean client) {
        super.setClient(client);
    }

    @Override
    public String getUsername() {
        return super.getUsername();
    }

    @Override
    public void setUsername(String username) {
        super.setUsername(username);
    }

    @Override
    public String getEmail() {
        return super.getEmail();
    }

    @Override
    public void setEmail(String email) {
        super.setEmail(email);
    }

    @Override
    public String getPassword() {
        return super.getPassword();
    }

    @Override
    public void setPassword(String password) {
        super.setPassword(password);
    }

    @Override
    public String getPhone() {
        return super.getPhone();
    }

    @Override
    public void setPhone(String phone) {
        super.setPhone(phone);
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getBusinessAddress() {
        return businessAddress;
    }

    public void setBusinessAddress(String businessType) {
        this.businessAddress = businessType;
    }


    public ActivityTime getActivityTime() {
        return activityTime;
    }

    public void setActivityTime(ActivityTime activityTime) {
        this.activityTime = activityTime;
    }


    public List<Type> getTypes() {
        return types;
    }

    public void setTypes(List<Type> types){
        this.types = types;
    }

    public void setTypesArr(Type[] types) {
        if(this.types == null)
            this.types = new ArrayList<>();
        this.types.clear();
        this.types.addAll(Arrays.asList(types));
    }

    public ArrayList<Contact> getClientsList() {
        return clientsList;
    }

    public void setClientsList(ArrayList<Contact> clientsList) {
        this.clientsList = clientsList;
    }


    public ArrayList<WeekViewEvent> getEventsList() {
        return eventsList;
    }

    public void setEventsList(ArrayList<WeekViewEvent> eventsList) {
        this.eventsList = eventsList;
    }

    public void addClient(Contact contact) {
        getClientsList().add(contact);
    }
}
