package com.nitconclave.internitcommunication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;

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
    private boolean mVerified;
    private String mEmail;
    private boolean mValidEmail;
    private HashMap<String, String> mDomains;
    private CoordinatorLayout mCoordinator;

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
        mVerified = false;
        mDomains = mAppConstants.getShortForms();
        mCoordinator = findViewById(R.id.coordinator);

        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mValidEmail = !TextUtils.isEmpty(charSequence) && Patterns.EMAIL_ADDRESS.matcher(charSequence).matches();
                mVerified = mValidEmail;
                if (!mVerified) {
                    mErrorImageView.setVisibility(View.VISIBLE);
                    mErrorImageView.setImageDrawable(getDrawable(R.drawable.ic_error_black_24dp));
                } else {
                    mEmail = charSequence.toString();
                    String chars[] = mEmail.split("@");
                    String domain[] = chars[1].split("\\.");
                    if (mDomains.containsKey(domain[0].toLowerCase())) {
                        mVerified = true;
                        String value = mDomains.get(domain[0].toLowerCase());
                        String college[] = value.split("\\$");
                        Log.e(LOG_TAG, college[0]);
                        int co = Integer.parseInt(college[1].trim());
                        mErrorImageView.setImageDrawable(mLogos.get(co - 1));
                    } else {
                        mErrorImageView.setVisibility(View.VISIBLE);
                        mErrorImageView.setImageDrawable(getDrawable(R.drawable.ic_error_black_24dp));
                        mVerified = false;
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mVerified)
                    Snackbar.make(mCoordinator, "Invalid email address", Snackbar.LENGTH_LONG).show();
                else {
                    mLoginText.setVisibility(View.GONE);
                    mLoginProgress.setVisibility(View.VISIBLE);
                }
            }
        });

        
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
