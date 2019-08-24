package com.nitconclave.internitcommunication.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nitconclave.internitcommunication.Activities.DisplayActivity;
import com.nitconclave.internitcommunication.Helpers.AppConstants;
import com.nitconclave.internitcommunication.Models.User;
import com.nitconclave.internitcommunication.R;


public class ChannelFragment extends Fragment {

    private ListView listView;
    private TextView textView;
    private AppConstants appConstants;
    private Context context;

    public ChannelFragment() {
        // Required empty public constructor
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_channel, container, false);
        listView = view.findViewById(R.id.list);
        context = view.getContext();
        appConstants = new AppConstants(getContext());

        User mUser = appConstants.getmUser();
        String id = appConstants.getmSecret();

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        final String institute = appConstants.getCollege();
        Log.e(institute, id);
        databaseReference.child("users").child(institute).child(id).child("mList").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    String post = (String) postSnapshot.getValue();
                    Log.e("Get Data", post);
                    Log.e("Get Data", institute);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return view;
    }

}
