package com.nitconclave.internitcommunication.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.nitconclave.internitcommunication.Adapters.GridAdapter;
import com.nitconclave.internitcommunication.Helpers.AppConstants;
import com.nitconclave.internitcommunication.Models.GridItems;
import com.nitconclave.internitcommunication.Models.User;
import com.nitconclave.internitcommunication.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

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
    private FirebaseUser mFirebaseUser;
    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabaseReference;
    private FirebaseDatabase mDatabase;
    private FirebaseStorage mFirebaseStorage;
    private StorageReference mStorageRef;
    private HashMap<String, ArrayList<String>> mInterests;
    private ArrayList<String> mAllInterests;
    private ArrayList<GridItems> mSelectedInterests;
    private ArrayAdapter<String> mArrayAdapter;
    private ImageView mAdd;
    private CoordinatorLayout mCoordinator;
    private GridView mGrid;
    private GridAdapter gridItemsAdapter;
    private TextView mSkip;
    private ImageView mButton;
    private TextView mButtonText;
    private ProgressBar mProgress;

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
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        try {
            Log.e(LOG_TAG, mFirebaseUser.getDisplayName());
        } catch (Exception e) {
            Log.e(LOG_TAG, "" + e);
        }
        mDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mDatabase.getReference();
        mFirebaseStorage = FirebaseStorage.getInstance();
        mStorageRef = mFirebaseStorage.getReference();
        mInterests = mAppConstants.getInterests();
        mAllInterests = mAppConstants.getAllInterests();
        mSelectedInterests = new ArrayList<>();
        mAdd = findViewById(R.id.add);
        mCoordinator = findViewById(R.id.coordinator);
        mGrid = findViewById(R.id.grid);
        gridItemsAdapter = new GridAdapter(UserActivity.this, mSelectedInterests);
        Collections.sort(mAllInterests);
        mArrayAdapter = new ArrayAdapter<>(UserActivity.this,
                android.R.layout.simple_list_item_1,
                mAllInterests);
        mSkip = findViewById(R.id.use_default);
        mButton = findViewById(R.id.button);
        mProgress = findViewById(R.id.button_progress);
        mButtonText = findViewById(R.id.button_text);

        mSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserActivity.this, DisplayActivity.class));
            }
        });

        mAuto.setThreshold(1);
        mAuto.setAdapter(mArrayAdapter);


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

        mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String value = mAuto.getText().toString();
                if (mAllInterests.contains(value)) {
                    mSelectedInterests.add(new GridItems(value, getDrawable(R.drawable.ic_cancel_black_24dp)));
                    mGrid.setAdapter(gridItemsAdapter);
                    mAuto.setText("");
                    mArrayAdapter = new ArrayAdapter<>(UserActivity.this,
                            android.R.layout.simple_list_item_1,
                            mAllInterests);
                    mAuto.setAdapter(mArrayAdapter);
                    mAllInterests.remove(value);
                } else
                    Snackbar.make(mCoordinator, "We're still growing, Please select an interest from the available interests !", Snackbar.LENGTH_LONG).show();
            }
        });

        mGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                GridItems gridItems = mSelectedInterests.get(i);
                for (int j = 0; j < mSelectedInterests.size(); j++) {
                    if (mSelectedInterests.get(j).equals(gridItems)) {
                        mSelectedInterests.remove(j);
                        gridItemsAdapter.notifyDataSetChanged();
                        break;
                    }
                }
                mAllInterests.add(gridItems.getmInterest());
                Collections.sort(mAllInterests);
            }
        });

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mButton.setEnabled(false);
                mButtonText.setVisibility(View.GONE);
                mProgress.setVisibility(View.VISIBLE);
                if (mImageUri != null) {
                    mStorageRef.child("users").
                            child(mSecret).
                            putFile(mImageUri).
                            addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onComplete(final Task<UploadTask.TaskSnapshot> task) {
                                    task.addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(Exception e) {
                                            Snackbar.make(mCoordinator, "Profile Photo size limit is 1 mb", Snackbar.LENGTH_LONG).show();
                                            mButton.setEnabled(true);
                                            mButtonText.setVisibility(View.VISIBLE);
                                            mProgress.setVisibility(View.GONE);
                                        }
                                    });
                                    task.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                            Task<Uri> profile_photos = mStorageRef.child("users").
                                                    child(mSecret).
                                                    getDownloadUrl();
                                            profile_photos.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                @Override
                                                public void onSuccess(Uri uri) {
                                                    mUser.setmProfileUrl(uri.toString());
                                                    updateData();
                                                }
                                            });
                                            profile_photos.addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Snackbar.make(mCoordinator, e.getLocalizedMessage(), Snackbar.LENGTH_LONG).show();
                                                    mButtonText.setVisibility(View.VISIBLE);
                                                    mButton.setEnabled(true);
                                                    mProgress.setVisibility(View.GONE);
                                                }
                                            });
                                        }
                                    });
                                }
                            });
                } else {
                    mUser.setmProfileUrl(null);
                    updateData();
                }
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

    private void updateData() {
        ArrayList<String> temp = new ArrayList<>();
        for (int i = 0; i < mSelectedInterests.size(); i++)
            temp.add(mSelectedInterests.get(i).getmInterest());
        mUser.setmList(temp);
        String email = mUser.getmEmail();
        String val[] = email.split("\\@");
        String x[] = val[1].split("\\.");
        Log.e(LOG_TAG, x[0]);
        int co = 0;
        for (int j = 0; j < mAppConstants.getDomains().size(); j++) {
            if (x[0].equalsIgnoreCase(mAppConstants.getDomains().get(j))) {
                co = j;
                break;
            }
        }
        String college = mAppConstants.getNitNames().get(co);
        mDatabaseReference.child("users").child(college).child(mSecret).setValue(mUser).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                task.addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        startActivity(new Intent(UserActivity.this, DisplayActivity.class));
                        finish();
                    }
                });

                task.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        mButton.setEnabled(true);
                        mButtonText.setVisibility(View.VISIBLE);
                        mProgress.setVisibility(View.GONE);
                        Snackbar.make(mCoordinator, e.getLocalizedMessage(), Snackbar.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

}
