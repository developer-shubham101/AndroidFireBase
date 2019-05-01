package com.devpoint.firebasedb.model;

public class UserModel {
    public boolean isOnline;
    public String email;
    public String profileDisplayName;
    public String FCMToken;

    public UserModel(boolean isOnline, String email, String profileDisplayName, String FCMToken) {
        this.isOnline = isOnline;
        this.email = email;
        this.profileDisplayName = profileDisplayName;
        this.FCMToken = FCMToken;
    }


}
