package com.nitconclave.internitcommunication.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nitconclave.internitcommunication.Models.User;
import com.nitconclave.internitcommunication.R;

public class UserSpecificClass extends AppCompatActivity {

    private String mKey;
    private String mValue;
    private final String LOG_TAG = "UserSpecificClass";
    private FirebaseUser mFirebaseUser;
    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabaseReference;
    private FirebaseDatabase mDatabase;
    private ValueEventListener mValueEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_specific_class);
        mKey = getIntent().getStringExtra("key");
        mValue = getIntent().getStringExtra("value");
        mDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mDatabase.getReference();

        mDatabaseReference.child(mValue).child(mKey).addValueEventListener(mValueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                mDatabaseReference.child(mValue).child(mKey).removeEventListener(mValueEventListener);
                Log.e(LOG_TAG, user.getmName() + " " + user.getRecommendation().size() + " " + user.getUserRec().size());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
