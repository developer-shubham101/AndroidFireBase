package com.devpoint.firebasedb.model;

import com.google.firebase.database.DataSnapshot;

public class UsersListModel {

    private String name;
    private String id;
    private String title = "";
    private boolean isOnline  = false;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UsersListModel(DataSnapshot ds) {
        this.name = ds.child("profileDisplayName").getValue(String.class) ;
        this.isOnline = ds.child("isOnline").getValue(boolean.class) ;
        this.id = ds.getKey();
	}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
