package com.nitconclave.internitcommunication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    //Declaration
    private ImageView mImageView;
    private AppConstants mAppConstants;
    private ArrayList<Drawable> mLogos;
    private int currIndex;
    private final String LOG_TAG = "Main";
    private EditText mEditText;
    private ImageView mErrorImageView;
    private ImageView mLogin;
    private TextView mLoginText;
    private ProgressBar mLoginProgress;
    private TextView mSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialization
        mImageView = findViewById(R.id.logo);
        mAppConstants = new AppConstants(this);
        mLogos = mAppConstants.getLogos();
        currIndex = 0;
        mEditText = findViewById(R.id.email);
        mErrorImageView = findViewById(R.id.error);
        mLogin = findViewById(R.id.button);
        mLoginText = findViewById(R.id.button_text);
        mLoginProgress = findViewById(R.id.button_progress);
        mSignUp = findViewById(R.id.sign_up);

        updateLogo();

    }

    private void updateLogo() {
        if (currIndex >= mLogos.size())
            currIndex = 0;
        mImageView.setImageDrawable(mLogos.get(currIndex++));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                updateLogo();
            }
        }, 2500);
    }
}
