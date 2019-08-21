package com.nitconclave.internitcommunication.Models;

public class User {
    private String mEmail;
    private String mName;
    private String mProfileUrl;

    public User(String mEmail, String mName, String mProfileUrl) {
        this.mEmail = mEmail;
        this.mName = mName;
        this.mProfileUrl = mProfileUrl;
    }

    public String getmEmail() {
        return mEmail;
    }

    public String getmName() {
        return mName;
    }

    public String getmProfileUrl() {
        return mProfileUrl;
    }
}
