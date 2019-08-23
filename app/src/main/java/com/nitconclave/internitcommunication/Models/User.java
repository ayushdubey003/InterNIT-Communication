package com.nitconclave.internitcommunication.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class User implements Parcelable {
    private String mEmail;
    private String mName;
    private String mProfileUrl;
    private List<Integer> mList=new ArrayList<>();

    public User() {

    }

    public User(String mEmail, String mName, String mProfileUrl, ArrayList<Integer> mList) {
        this.mEmail = mEmail;
        this.mName = mName;
        this.mProfileUrl = mProfileUrl;
        //this.mList = mList;
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

    public List<Integer> getmList() {
        return mList;
    }

    public User(Parcel in) {
        mEmail = in.readString();
        mName = in.readString();
        mProfileUrl = in.readString();
        //in.readList(this.mList,List.class.getClassLoader());
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(mEmail);
        dest.writeString(mName);
        dest.writeString(mProfileUrl);
        //dest.writeList(mList);
    }
}
