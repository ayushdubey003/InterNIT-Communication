package com.nitconclave.internitcommunication.Helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.google.gson.Gson;
import com.nitconclave.internitcommunication.Models.User;
import com.nitconclave.internitcommunication.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

public class AppConstants {

    private ArrayList<Drawable> logos;
    private HashMap<String, String> shortForms;
    private ArrayList<String> nitNames;
    private ArrayList<String> domains;
    private String mSecret;
    private User mUser;
    private HashMap<String, ArrayList<String>> interests;
    private ArrayList<String> allInterests;
    private ArrayList<String> mBranches;

    public SharedPreferences mSharedPreferences;
    public final String mPrefsName = "loginDetails";

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public AppConstants(Context context) {
        logos = new ArrayList<>();
        shortForms = new HashMap<>();
        nitNames = new ArrayList<>();
        domains = new ArrayList<>();
        mUser = new User();
        mSharedPreferences = context.getSharedPreferences(mPrefsName, Context.MODE_PRIVATE);
        interests = new HashMap<>();
        mBranches = new ArrayList<>();
        allInterests = new ArrayList<>();

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

        nitNames.add("NIT Agartala");
        nitNames.add("NIT Allahabad");
        nitNames.add("NIT Andhra Pradesh");
        nitNames.add("NIT Arunachal Pradesh");
        nitNames.add("NIT Bhopal");
        nitNames.add("NIT Calicut");
        nitNames.add("NIT Delhi");
        nitNames.add("NIT Durgapur");
        nitNames.add("NIT Goa");
        nitNames.add("NIT Hamirpur");
        nitNames.add("NIT Jaipur");
        nitNames.add("NIT Jalandhar");
        nitNames.add("NIT Jamshedpur");
        nitNames.add("NIT Karnataka");
        nitNames.add("NIT Kurukshetra");
        nitNames.add("NIT Manipur");
        nitNames.add("NIT Meghalaya");
        nitNames.add("NIT Mizoram");
        nitNames.add("NIT Nagaland");
        nitNames.add("NIT Nagpur");
        nitNames.add("NIT Patna");
        nitNames.add("NIT Puducherry");
        nitNames.add("NIT Raipur");
        nitNames.add("NIT Rourkela");
        nitNames.add("NIT Sikkim");
        nitNames.add("NIT Silchar");
        nitNames.add("NIT Srinagar");
        nitNames.add("NIT Surat");
        nitNames.add("NIT Trichy");
        nitNames.add("NIT Uttarakhand");
        nitNames.add("NIT Warangal");

        domains.add("nita");
        domains.add("mnnit");
        domains.add("nitanp");
        domains.add("nitap");
        domains.add("manit");
        domains.add("nitc");
        domains.add("nitd");
        domains.add("nitdgp");
        domains.add("nitg");
        domains.add("nith");
        domains.add("mnit");
        domains.add("nitj");
        domains.add("nitjsr");
        domains.add("nitk");
        domains.add("nitkkr");
        domains.add("nitmn");
        domains.add("nitm");
        domains.add("nitmz");
        domains.add("nitn");
        domains.add("vnit");
        domains.add("nitp");
        domains.add("nitpy");
        domains.add("nitrr");
        domains.add("nitrkl");
        domains.add("nitskm");
        domains.add("nits");
        domains.add("nitsri");
        domains.add("svnit");
        domains.add("nitt");
        domains.add("nituk");
        domains.add("nitw");

        interests.put("Mechanical", new ArrayList<String>());
        interests.put("Computer Science", new ArrayList<String>());
        interests.put("Electronics", new ArrayList<String>());
        interests.put("Electrical", new ArrayList<String>());
        interests.put("Metallurgy", new ArrayList<String>());
        interests.put("Civil", new ArrayList<String>());

        interests.get("Mechanical").add("Ground Vehicle Systems");
        interests.get("Mechanical").add("Manufacturing");
        interests.get("Mechanical").add("Mechanical Design");
        interests.get("Mechanical").add("Transportation System");
        interests.get("Mechanical").add("System Dynamics and Control");
        interests.get("Mechanical").add("Aerospace Engineering");
        interests.get("Mechanical").add("Off Road Vehicle");
        interests.get("Mechanical").add("Formula One Vehicle");
        interests.get("Mechanical").add("Material Science");
        mBranches.add("Mechanical");

        interests.get("Computer Science").add("Competitive Coding");
        interests.get("Computer Science").add("Machine Learning");
        interests.get("Computer Science").add("Web Development");
        interests.get("Computer Science").add("App Development");
        interests.get("Computer Science").add("Blockchain");
        interests.get("Computer Science").add("Software Development");
        interests.get("Computer Science").add("JAVA");
        interests.get("Computer Science").add("Python");
        interests.get("Computer Science").add("C++");
        interests.get("Computer Science").add("Microprocessor");
        interests.get("Computer Science").add("Database Management System");
        mBranches.add("Computer Science");


        interests.get("Electronics").add("Circuit Theory");
        interests.get("Electronics").add("Micro Electronics");
        interests.get("Electronics").add("VLSI");
        interests.get("Electronics").add("Integrated Circuits");
        interests.get("Electronics").add("Telecommunication");
        interests.get("Electronics").add("Nanotechnology");
        interests.get("Electronics").add("Digital Signal Processing");
        interests.get("Electronics").add("Analog Signal Processing");
        interests.get("Electronics").add("Embedded Systems");
        mBranches.add("Electronics");

        interests.get("Electrical").add("Digital Signal Processing");
        interests.get("Electrical").add("Analog Signal Processing");
        interests.get("Electrical").add("Industrial Instrumentation");
        interests.get("Electrical").add("Power System");
        interests.get("Electrical").add("Microprocessor");
        interests.get("Electrical").add("Communication System");
        mBranches.add("Electrical");

        interests.get("Metallurgy").add("Thermodynamics");
        interests.get("Metallurgy").add("Physics of Materials");
        interests.get("Metallurgy").add("Material Science");
        interests.get("Metallurgy").add("Extractive Metallurgy");
        interests.get("Metallurgy").add("Iron Making");
        interests.get("Metallurgy").add("X-ray Diffraction");
        interests.get("Metallurgy").add("Steel Making");
        mBranches.add("Metallurgy");

        interests.get("Civil").add("Construction");
        interests.get("Civil").add("Planning and Management");
        interests.get("Civil").add("Concrete Design");
        interests.get("Civil").add("Structural Analysis");
        interests.get("Civil").add("Geotechnical Engineering");
        interests.get("Civil").add("Survey");
        interests.get("Civil").add("Hydraulics");
        interests.get("Civil").add("Structural Mechanics");
        mBranches.add("Civil");

        for (int i = 0; i < mBranches.size(); i++) {
            allInterests.addAll(interests.get(mBranches.get(i)));
        }
    }

    public ArrayList<Drawable> getLogos() {
        return logos;
    }

    public HashMap<String, String> getShortForms() {
        return shortForms;
    }

    public ArrayList<String> getNitNames() {
        return nitNames;
    }

    public ArrayList<String> getDomains() {
        return domains;
    }

    public void setmSecret(String mSecret) {
        this.mSecret = mSecret;
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString("UserSecret", mSecret);
        editor.apply();
    }

    public void setmUser(User mUser) {
        this.mUser = mUser;
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(mUser);
        editor.putString("User", json);
        editor.apply();
    }

    public String getmSecret() {
        mSecret = mSharedPreferences.getString("UserSecret", "Error");
        return mSecret;
    }

    public User getmUser() {
        Gson gson = new Gson();
        String json = mSharedPreferences.getString("User", "");
        mUser = gson.fromJson(json, User.class);
        return mUser;
    }

    public HashMap<String, ArrayList<String>> getInterests() {
        return interests;
    }

    public ArrayList<String> getmBranches() {
        return mBranches;
    }

    public ArrayList<String> getAllInterests() {
        return allInterests;
    }

    public ArrayList<String> getSortedInterests() {
        ArrayList<String> temp = allInterests;
        Collections.sort(temp);
        return temp;
    }
}
