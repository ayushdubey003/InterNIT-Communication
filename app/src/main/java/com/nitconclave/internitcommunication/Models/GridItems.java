package com.nitconclave.internitcommunication.Models;

import android.graphics.drawable.Drawable;

public class GridItems {
    private String mInterest;
    private Drawable mRemove;

    public GridItems(String mInterest, Drawable mRemove) {
        this.mInterest = mInterest;
        this.mRemove = mRemove;
    }

    public String getmInterest() {
        return mInterest;
    }

    public Drawable getmRemove() {
        return mRemove;
    }
}
