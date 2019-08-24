package com.nitconclave.internitcommunication.Models;


import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class User {
    private String mEmail;
    private String mName;
    private String mProfileUrl;
    private List<String> mList = new ArrayList<>();
    private boolean mInterests;
    private List<String> recommendation = new ArrayList<>();
    private HashMap<String, String> userRec = new HashMap<>();

    public User() {

    }

    public User(String mEmail, String mName, String mProfileUrl, ArrayList<String> mList, boolean mInterests, ArrayList<String> recommendation,HashMap<String, String> userRec) {
        this.mEmail = mEmail;
        this.mName = mName;
        this.mProfileUrl = mProfileUrl;
        this.mList = mList;
        this.mInterests = mInterests;
        this.recommendation = recommendation;
        this.userRec=userRec;
    }

    public List<String> getRecommendation() {
        return recommendation;
    }

    public void setRecommendation(List<String> recommendation) {
        this.recommendation = recommendation;
    }

    public HashMap<String, String> getUserRec() {
        return userRec;
    }

    public void setUserRec(HashMap<String, String> userRec) {
        this.userRec = userRec;
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

    public List<String> getmList() {
        return mList;
    }

    public boolean ismInterests() {
        return mInterests;
    }

    public void setmInterests() {
        mInterests = true;
    }

    public void setmEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public void setmProfileUrl(String mProfileUrl) {
        this.mProfileUrl = mProfileUrl;
    }

    public void setmList(List<String> mList) {
        this.mList = mList;
    }

    public void setmInterests(boolean mInterests) {
        this.mInterests = mInterests;
    }

    public List<String> getmRecommendations() {
        return recommendation;
    }

    public void setmRecommendations(List<String> recommendation) {
        this.recommendation = recommendation;
    }
}
