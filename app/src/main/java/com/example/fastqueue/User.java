package com.example.fastqueue;

public class User {

    private String username;
    private String email;
    private String password;
    private String verify;
    private String phone;
    private boolean isClient;

    public User() {

    }

    public User(String username, String email, String password, String verify, String phone, boolean isClient) {
        setUsername(username);
        setEmail(email);
        setPassword(password);
        setVerify(verify);
        setPhone(phone);
        setClient(isClient);
    }

    public boolean isClient() {
        return isClient;
    }

    public void setClient(boolean client) {
        isClient = client;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getVerify() {
        return verify;
    }

    public void setVerify(String verify) {
        this.verify= verify;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    static class MyUser{
        private static User user;
        public static void setUser(User user){
            MyUser.user = user;
        }
        public static User getUser(){
            return user;
        }
    }
}
