package com.nitconclave.internitcommunication;

import android.content.Context;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.HashMap;

public class AppConstants {

    private ArrayList<Drawable> logos;
    private HashMap<String, String> shortForms;

    AppConstants(Context context) {
        logos = new ArrayList<>();
        shortForms = new HashMap<>();

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

        shortForms.put("mnnit", "allahabad$2");
        shortForms.put("manit", "bhopal$5");
        shortForms.put("nitc", "calicut$6");
        shortForms.put("nith", "hamirpur$10");
        shortForms.put("mnit", "jaipur$11");
        shortForms.put("nitj", "jalandhar$12");
        shortForms.put("nitjsr", "jamshedpur$13");
        shortForms.put("nitkkr", "kurukshetra$15");
        shortForms.put("vnit", "nagpur$20");
        shortForms.put("nitrkl", "rourkela$24");
        shortForms.put("nits", "silchar$26");
        shortForms.put("nitk", "karnataka$14");
        shortForms.put("nitw", "warangal$31");
        shortForms.put("nitdgp", "durgapur$8");
        shortForms.put("nitsri", "srinagar$27");
        shortForms.put("svnit", "surat$28");
        shortForms.put("nitt", "trichy$29");
        shortForms.put("nitp", "patna$21");
        shortForms.put("nitrr", "raipur$23");
        shortForms.put("nita", "agartala$1");
        shortForms.put("nitap", "arunachal$4");
        shortForms.put("nitd", "delhi$7");
        shortForms.put("nitg", "goa$9");
        shortForms.put("nitmn", "manipur$16");
        shortForms.put("nitm", "meghalaya$17");
        shortForms.put("nitmz", "mizoram$18");
        shortForms.put("nitn", "nagaland$19");
        shortForms.put("nitpy", "puducherry$22");
        shortForms.put("nitskm", "sikkim$25");
        shortForms.put("nituk", "uttarakhand$30");
        shortForms.put("nitanp", "andhra$3");

    }

    public ArrayList<Drawable> getLogos() {
        return logos;
    }

    public HashMap<String, String> getShortForms() {
        return shortForms;
    }
}
