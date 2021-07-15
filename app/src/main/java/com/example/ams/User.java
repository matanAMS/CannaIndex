package com.example.ams;

public class User {
    String UserName;
    String UserID;
    String UserPhone;

    public User(String userName, String userID, String userPhone) {
        UserName = userName;
        UserID = userID;
        UserPhone = userPhone;
    }

    public User() {
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getUserPhone() {
        return UserPhone;
    }

    public void setUserPhone(String userPhone) {
        UserPhone = userPhone;
    }
}
