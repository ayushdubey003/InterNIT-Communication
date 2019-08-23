package com.nitconclave.internitcommunication.Models;

import java.util.ArrayList;

public class User {
    private String mEmail;
    private String mName;
    private String mProfileUrl;
    private ArrayList<Integer> mList;

    public User() {

    }

    public User(String mEmail, String mName, String mProfileUrl, ArrayList<Integer> mList) {
        this.mEmail = mEmail;
        this.mName = mName;
        this.mProfileUrl = mProfileUrl;
        this.mList = mList;
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

    public ArrayList<Integer> getmList() {
        return mList;
    }
}
