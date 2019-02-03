package com.arvind.ratemysingingdemo;

public class User {
    public String userName;
    public String userAge;

    public User() {
    }

    public User(String userName, String userAge) {
        this.userName = userName;
        this.userAge = userAge;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAge() {
        return userAge;
    }

    public void setUserAge(String userAge) {
        this.userAge = userAge;
    }
}
