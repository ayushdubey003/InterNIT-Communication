package com.nitconclave.internitcommunication.Activities;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.nitconclave.internitcommunication.Fragments.ChannelFragment;
import com.nitconclave.internitcommunication.Fragments.DiscoverFragment;
import com.nitconclave.internitcommunication.Fragments.ProfileFragment;
import com.nitconclave.internitcommunication.Helpers.AppConstants;
import com.nitconclave.internitcommunication.R;

import java.util.ArrayList;

public class DisplayActivity extends AppCompatActivity {

    private ArrayList<Drawable> mLogos;
    private int currIndex;
    private ImageView imageView;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private static final int number_of_tabs = 3;
    private TextView tabs[];

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        imageView = findViewById(R.id.image);
        AppConstants mAppConstants = new AppConstants(this);
        mLogos = mAppConstants.getLogos();
        currIndex = 0;

        updateLogo();
        init();

    }

    private void updateLogo() {
        if (currIndex >= mLogos.size())
            currIndex = 0;
        imageView.setImageDrawable(mLogos.get(currIndex++));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                updateLogo();
            }
        }, 2500);
    }

    private void init(){
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        viewPager.setOffscreenPageLimit(3);
        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(myPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }


    private class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            switch (i) {
                case 0:
                    return new ChannelFragment();
                case 1:
                    return new DiscoverFragment();
                case 2:
                    return new ProfileFragment();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            //Modify the return value to include your fragment
            return number_of_tabs;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "CHANNELS";
                case 1:
                    return "DISCOVER";
                case 2:
                    return "PROFILE";
                default:
                    return null;
            }
        }
    }
}

