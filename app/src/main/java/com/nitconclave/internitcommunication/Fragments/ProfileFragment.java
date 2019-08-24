package com.nitconclave.internitcommunication.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.nitconclave.internitcommunication.Activities.DisplayActivity;
import com.nitconclave.internitcommunication.Activities.UserActivity;
import com.nitconclave.internitcommunication.Helpers.AppConstants;
import com.nitconclave.internitcommunication.Models.User;
import com.nitconclave.internitcommunication.R;

import java.util.ArrayList;

public class ProfileFragment extends Fragment {


    private static final int PICK_IMAGE = 1;
    private ImageView mProfilePhoto;
    private ImageView mUpdateProfile;
    private Uri mImageUri;
    private ImageView mCircleBg;
    private AppConstants mAppConstants;
    private User mUser;
    private String mSecret;
    private Context mContext;
    private TextView mName;
    private TextView mEmail;
    private ImageView mBin;
    private FirebaseUser mFirebaseUser;
    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabaseReference;
    private FirebaseDatabase mDatabase;
    private FirebaseStorage mFirebaseStorage;
    private StorageReference mStorageRef;
    private CoordinatorLayout mCoordinator;
    private EditText mEditText;
    private ImageView mEdit;
    private boolean mCheck;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        mContext = getContext();
        mAppConstants = new AppConstants(mContext);
        mUpdateProfile = view.findViewById(R.id.update_profile);
        mProfilePhoto = view.findViewById(R.id.profile_image);
        mCircleBg = view.findViewById(R.id.circle_bg);
        mUser = mAppConstants.getmUser();
        mSecret = mAppConstants.getmSecret();
        mName = view.findViewById(R.id.name);
        mEmail = view.findViewById(R.id.email);
        mBin = view.findViewById(R.id.bin);
        mDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mDatabase.getReference();
        mFirebaseStorage = FirebaseStorage.getInstance();
        mStorageRef = mFirebaseStorage.getReference();
        mCoordinator = view.findViewById(R.id.coordinator);
        mEditText = view.findViewById(R.id.edit_name);
        mEdit = view.findViewById(R.id.edit);
        mCheck = true;

        mName.setText(mUser.getmName());
        mEmail.setText(mUser.getmEmail());
        if (mUser.getmProfileUrl() == null) {
            mBin.setVisibility(View.GONE);
        } else {
            mBin.setVisibility(View.VISIBLE);
            Glide.with(this)
                    .load(mUser.getmProfileUrl())
                    .into(mProfilePhoto);
        }

        mUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Profile Image"), PICK_IMAGE);
            }
        });

        mBin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBin.setVisibility(View.GONE);
                mProfilePhoto.setImageDrawable(getActivity().getDrawable(R.drawable.ic_account_circle_black_24dp));
                String college = mAppConstants.getCollege();
                mUser.setmProfileUrl(null);
                mDatabaseReference.child("users").child(college).child(mSecret).setValue(mUser).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        task.addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                mAppConstants.setmUser(mUser);
                            }
                        });

                        task.addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Snackbar.make(mCoordinator, e.getLocalizedMessage(), Snackbar.LENGTH_LONG).show();
                            }
                        });
                    }
                });
            }
        });

        mEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCheck) {
                    mEdit.setImageDrawable(getActivity().getDrawable(R.drawable.ic_check_black_24dp));
                    String name = mName.getText().toString();
                    mName.setVisibility(View.GONE);
                    mEditText.setText(name);
                    mEditText.setVisibility(View.VISIBLE);
                    mCheck = false;
                } else {
                    mCheck = true;
                    mEdit.setImageDrawable(getActivity().getDrawable(R.drawable.ic_edit_black_24dp));
                    String name = mEditText.getText().toString();
                    mEditText.setVisibility(View.GONE);
                    mName.setVisibility(View.VISIBLE);
                    mName.setText(name);
                    String college = mAppConstants.getCollege();
                    if (!name.equals(mUser.getmName())) {
                        mUser.setmName(name);
                        mDatabaseReference.child("users").child(college).child(mSecret).setValue(mUser).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                task.addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        mAppConstants.setmUser(mUser);
                                    }
                                });

                                task.addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Snackbar.make(mCoordinator, e.getLocalizedMessage(), Snackbar.LENGTH_LONG).show();
                                    }
                                });
                            }
                        });
                    }
                }
            }
        });
        return view;
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
                updatePhotoData();
            } else
                mImageUri = null;
        }
    }

    private void updatePhotoData() {
        mBin.setVisibility(View.VISIBLE);
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
                                        String college = mAppConstants.getCollege();
                                        mDatabaseReference.child("users").child(college).child(mSecret).setValue(mUser).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                                task.addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        mAppConstants.setmUser(mUser);
                                                    }
                                                });

                                                task.addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Snackbar.make(mCoordinator, e.getLocalizedMessage(), Snackbar.LENGTH_LONG).show();
                                                    }
                                                });
                                            }
                                        });
                                    }
                                });
                                profile_photos.addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Snackbar.make(mCoordinator, e.getLocalizedMessage(), Snackbar.LENGTH_LONG).show();
                                    }
                                });
                            }
                        });
                    }
                });
    }
}
