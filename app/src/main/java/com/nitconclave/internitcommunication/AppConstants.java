package com.nitconclave.internitcommunication;

import android.content.Context;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;

public class AppConstants {

    private ArrayList<Drawable> logos;

    AppConstants(Context context) {
        logos = new ArrayList<>();
        logos.add(context.getDrawable(R.drawable.agartala));
        logos.add(context.getDrawable(R.drawable.allahabad));
        logos.add(context.getDrawable(R.drawable.andhra));
        logos.add(context.getDrawable(R.drawable.arunachal));
        logos.add(context.getDrawable(R.drawable.bhopal));
        logos.add(context.getDrawable(R.drawable.calicut));
        logos.add(context.getDrawable(R.drawable.delhi));
        logos.add(context.getDrawable(R.drawable.durgapur));
        logos.add(context.getDrawable(R.drawable.goa));
        logos.add(context.getDrawable(R.drawable.hamirpur));
        logos.add(context.getDrawable(R.drawable.jaipur));
        logos.add(context.getDrawable(R.drawable.jalandhar));
        logos.add(context.getDrawable(R.drawable.jamshedpur));
        logos.add(context.getDrawable(R.drawable.karnataka));
        logos.add(context.getDrawable(R.drawable.kurukshetra));
        logos.add(context.getDrawable(R.drawable.manipur));
        logos.add(context.getDrawable(R.drawable.meghalaya));
        logos.add(context.getDrawable(R.drawable.mizoram));
        logos.add(context.getDrawable(R.drawable.nagaland));
        logos.add(context.getDrawable(R.drawable.nagpur));
        logos.add(context.getDrawable(R.drawable.patna));
        logos.add(context.getDrawable(R.drawable.puducherry));
        logos.add(context.getDrawable(R.drawable.raipur));
        logos.add(context.getDrawable(R.drawable.rourkela));
        logos.add(context.getDrawable(R.drawable.sikkim));
        logos.add(context.getDrawable(R.drawable.silchar));
        logos.add(context.getDrawable(R.drawable.srinagar));
        logos.add(context.getDrawable(R.drawable.surat));
        logos.add(context.getDrawable(R.drawable.trichy));
        logos.add(context.getDrawable(R.drawable.uttarakhand));
        logos.add(context.getDrawable(R.drawable.warangal));
    }

    public ArrayList<Drawable> getLogos() {
        return logos;
    }
}
