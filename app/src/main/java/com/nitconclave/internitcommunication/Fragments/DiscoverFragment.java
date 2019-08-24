package com.nitconclave.internitcommunication.Fragments;

import android.content.Context;
import android.content.Intent;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nitconclave.internitcommunication.Activities.UserSpecificClass;
import com.nitconclave.internitcommunication.Adapters.ListAdapter;
import com.nitconclave.internitcommunication.Helpers.AppConstants;
import com.nitconclave.internitcommunication.Models.ListItems;
import com.nitconclave.internitcommunication.Models.User;
import com.nitconclave.internitcommunication.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DiscoverFragment extends Fragment {


    private AppConstants mAppConstants;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mDatabaseReference;
    private User mUserDetails;
    private ValueEventListener mValueEventListener;
    private ListView mListView;
    private ProgressBar mProgress;
    private ArrayList<ListItems> mRecommendedUsers;
    private ListAdapter mListAdapter;
    private RelativeLayout mRelativeLayout;
    private ArrayList<String> mKeys;
    private ArrayList<String> mValues;

    public DiscoverFragment() {
        // Required empty public constructor
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_discover, container, false);
        mAppConstants = new AppConstants(getContext());
        mDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mDatabase.getReference("users");
        mUserDetails = mAppConstants.getmUser();
        mListView = view.findViewById(R.id.list);
        mProgress = view.findViewById(R.id.progress_circular);
        mRecommendedUsers = new ArrayList<>();
        mListAdapter = new ListAdapter(getContext(), R.layout.fragment_discover, mRecommendedUsers);
        mRelativeLayout = view.findViewById(R.id.relativeLayout1);
        mKeys = new ArrayList<>();
        mValues = new ArrayList<>();

        mDatabaseReference.child(mAppConstants.getCollege()).child(mAppConstants.getmSecret()).child("userRec").addValueEventListener(mValueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final HashMap<String, String> map = (HashMap<String, String>) dataSnapshot.getValue();
                mUserDetails.setUserRec(map);
                mAppConstants.setmUser(mUserDetails);
                mDatabaseReference.child(mAppConstants.getCollege()).child(mAppConstants.getmSecret()).child("userRec").removeEventListener(mValueEventListener);
                for (Map.Entry mapElement : map.entrySet()) {
                    final String key = (String) mapElement.getKey();
                    final String value = (String) mapElement.getValue();
                    mDatabaseReference.child(value).child(key).addValueEventListener(mValueEventListener = new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            mKeys.add(key);
                            mValues.add(value);
                            User user = dataSnapshot.getValue(User.class);
                            mRecommendedUsers.add(new ListItems(user.getmName(), value, getActivity().getDrawable(R.drawable.ic_account_circle_black_24dp)));
                            if (mRecommendedUsers.size() == map.size()) {
                                mRelativeLayout.setVisibility(View.VISIBLE);
                                mProgress.setVisibility(View.GONE);
                                mListView.setVisibility(View.VISIBLE);
                                mListView.setAdapter(mListAdapter);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), UserSpecificClass.class);
                intent.putExtra("key", mKeys.get(i));
                intent.putExtra("value", mValues.get(i));
                startActivity(intent);
            }
        });

        return view;
    }
}
