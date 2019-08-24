package com.nitconclave.internitcommunication.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nitconclave.internitcommunication.Models.User;
import com.nitconclave.internitcommunication.R;

import java.util.ArrayList;

public class UserSpecificClass extends AppCompatActivity {

    private String mKey;
    private String mValue;
    private final String LOG_TAG = "UserSpecificClass";
    private FirebaseUser mFirebaseUser;
    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabaseReference;
    private FirebaseDatabase mDatabase;
    private ValueEventListener mValueEventListener;
    private TextView mName;
    private TextView mCollegeName;
    private GridView mGrid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_specific_class);
        mKey = getIntent().getStringExtra("key");
        mValue = getIntent().getStringExtra("value");
        mDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mDatabase.getReference();
        mName = findViewById(R.id.name);
        mGrid = findViewById(R.id.grid);
        mCollegeName = findViewById(R.id.college);

        Log.e(LOG_TAG, mKey + mValue);
        mDatabaseReference.child("users").child(mValue).child(mKey).addValueEventListener(mValueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                mDatabaseReference.child(mValue).child(mKey).removeEventListener(mValueEventListener);
                mName.setText(user.getmName());
                String collegeName = "National Institute of Technology ";
                String sf = mValue;
                String x[] = sf.split(" ");
                for (int j = 1; j < x.length; j++)
                    collegeName = collegeName + x[j] + " ";
                mCollegeName.setText(collegeName);
                ArrayList<String> list = (ArrayList<String>) user.getmList();
                mGrid.setAdapter(new ArrayAdapter<>(UserSpecificClass.this, R.layout.grid_values, list));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
