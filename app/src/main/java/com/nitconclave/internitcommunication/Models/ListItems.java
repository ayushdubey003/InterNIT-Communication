package com.nitconclave.internitcommunication.Models;

import android.graphics.drawable.Drawable;

public class ListItems {
    private String mName;
    private String mCollegeName;
    private Drawable mDrawable;

    public ListItems(String mName, String mCollegeName, Drawable mDrawable) {
        this.mName = mName;
        this.mCollegeName = mCollegeName;
        this.mDrawable = mDrawable;
    }

    public String getmName() {
        return mName;
    }

    public String getmCollegeName() {
        return mCollegeName;
    }

    public Drawable getmDrawable() {
        return mDrawable;
    }
}
