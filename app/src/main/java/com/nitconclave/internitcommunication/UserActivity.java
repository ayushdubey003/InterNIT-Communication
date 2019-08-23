package com.nitconclave.internitcommunication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class UserActivity extends AppCompatActivity {

    private static final int PICK_IMAGE = 1;
    private ImageView mProfilePhoto;
    private ImageView mUpdateProfile;
    private Uri mImageUri;
    private ImageView mCircleBg;
    private AutoCompleteTextView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        mUpdateProfile = findViewById(R.id.update_profile);
        mProfilePhoto = findViewById(R.id.profile_image);
        mCircleBg = findViewById(R.id.circle_bg);

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
