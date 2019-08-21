package com.nitconclave.internitcommunication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class SignUpActivity extends AppCompatActivity {

    //Declaration
    private ImageView mImageView;
    private AppConstants mAppConstants;
    private ArrayList<Drawable> mLogos;
    private ArrayList<String> mDomains;
    private final String LOG_TAG = "SignUp";
    private CoordinatorLayout mCoordinator;
    private Spinner mSpinner;
    private int mSelected;
    private EditText mEmail;
    private TextView mDomainText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        //Initialization
        mAppConstants = new AppConstants(this);
        mSelected = 0;
        mLogos = mAppConstants.getLogos();
        mDomains = mAppConstants.getDomains();
        mImageView = findViewById(R.id.logo);
        mSpinner = findViewById(R.id.spinner);
        mDomainText = findViewById(R.id.domain);
        mEmail = findViewById(R.id.email);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, mAppConstants.getNitNames());

        for (int i = 0; i < mDomains.size(); i++)
            mDomains.set(i, mDomains.get(i) + ".ac.in");

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);
        mImageView.setImageDrawable(mLogos.get(mSelected));
        mDomainText.setText(mDomains.get(mSelected));

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mSelected = i;
                mImageView.setImageDrawable(mLogos.get(mSelected));
                mDomainText.setText(mDomains.get(mSelected));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

}
