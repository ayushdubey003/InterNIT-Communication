package com.nitconclave.internitcommunication.Activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.nitconclave.internitcommunication.Helpers.AppConstants;
import com.nitconclave.internitcommunication.Models.User;
import com.nitconclave.internitcommunication.R;

public class UserActivity extends AppCompatActivity {

    private static final int PICK_IMAGE = 1;
    private ImageView mProfilePhoto;
    private ImageView mUpdateProfile;
    private Uri mImageUri;
    private ImageView mCircleBg;
    private AutoCompleteTextView mAuto;
    private final String LOG_TAG = "UserActivity";
    private User mUser;
    private String mSecret;
    private AppConstants mAppConstants;
    private SharedPreferences mSharedPrefs;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        mUpdateProfile = findViewById(R.id.update_profile);
        mProfilePhoto = findViewById(R.id.profile_image);
        mCircleBg = findViewById(R.id.circle_bg);
        mAuto = findViewById(R.id.auto);
        mAppConstants = new AppConstants(this);
        mSharedPrefs = getSharedPreferences(mAppConstants.mPrefsName, Context.MODE_PRIVATE);
        mSecret = mAppConstants.getmSecret();
        mUser = mAppConstants.getmUser();

        if (mUser != null)
            Log.e(LOG_TAG, mUser.getmName() + mUser.getmEmail());
        Log.e(LOG_TAG, mSecret);

        mUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Profile Image"), PICK_IMAGE);
            }
        });
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_IMAGE) {
            if (data != null) {
                mImageUri = data.getData();
                mCircleBg.setVisibility(View.GONE);
                Glide.with(this)
                        .load(mImageUri)
                        .into(mProfilePhoto);
            } else
                mImageUri = null;
        }
    }
}
