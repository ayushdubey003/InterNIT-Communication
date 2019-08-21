package com.nitconclave.internitcommunication;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.content.Intent;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nitconclave.internitcommunication.Helpers.Encryptor;
import com.nitconclave.internitcommunication.Models.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    private EditText mEmailEdit;
    private String mEmail;
    private EditText mPasswordText;
    private String mPassword;
    private EditText mConfirmPassword;
    private TextView mDomainText;
    private FirebaseAuth mAuth;
    private FirebaseUser mFirebaseUser;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mDatabaseReference;
    private EditText mNameText;
    private String mName;
    private ImageView mButton;
    private TextView mButtonText;
    private ProgressBar mProgress;
    private ImageView mEmailError;
    private ImageView mPasswordError;
    private ImageView mConfirmError;
    private boolean mEmailErr;
    private boolean mPasswordErr;
    private boolean mConfirmErr;

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
        mEmailEdit = findViewById(R.id.email);
        mPasswordText = findViewById(R.id.password);
        mConfirmPassword = findViewById(R.id.confirm);
        mNameText = findViewById(R.id.name);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, mAppConstants.getNitNames());
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mDatabase.getReference().child("users");
        mButton = findViewById(R.id.button);
        mButtonText = findViewById(R.id.button_text);
        mProgress = findViewById(R.id.button_progress);
        mEmailErr = true;
        mPasswordErr = true;
        mConfirmErr = true;
        mEmailError = findViewById(R.id.emailError);
        mPasswordError = findViewById(R.id.passwordError);
        mConfirmError = findViewById(R.id.confirmError);
        mCoordinator = findViewById(R.id.coordinator);

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

        mEmailEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mEmail = charSequence.toString();
                if (mEmail.length() <= 0) {
                    mEmailError.setVisibility(View.VISIBLE);
                    return;
                }
                mEmail = mEmail + "@" + mDomains.get(mSelected);
                mEmailErr = (!TextUtils.isEmpty(charSequence) && Patterns.EMAIL_ADDRESS.matcher(charSequence).matches());
                if (mEmailErr)
                    mEmailError.setVisibility(View.VISIBLE);
                else
                    mEmailError.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mPasswordText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mPassword = charSequence.toString();
                if (mPassword.length() < 8) {
                    mPasswordErr = true;
                    mPasswordError.setVisibility(View.VISIBLE);
                    return;
                }
                Pattern pattern;
                Matcher matcher;
                final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
                pattern = Pattern.compile(PASSWORD_PATTERN);
                matcher = pattern.matcher(mPassword);
                mPasswordErr = !(matcher.matches());
                if (mPasswordErr)
                    mPasswordError.setVisibility(View.VISIBLE);
                else
                    mPasswordError.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mConfirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String confirm = charSequence.toString();
                if (mPasswordErr) {
                    mConfirmError.setVisibility(View.VISIBLE);
                    return;
                }
                if (confirm.equals(mPassword)) {
                    mConfirmErr = false;
                    mConfirmError.setVisibility(View.GONE);
                } else {
                    mConfirmErr = true;
                    mConfirmError.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mButton.setClickable(false);
                mName = mNameText.getText().toString();
                if (mConfirmErr || mPasswordErr || mEmailErr || mName.length() <= 0) {
                    Snackbar.make(mCoordinator, "Please check Entered Details!", Snackbar.LENGTH_LONG).show();
                    return;
                } else {
                    mButtonText.setVisibility(View.GONE);
                    mProgress.setVisibility(View.VISIBLE);
                    mAuth.createUserWithEmailAndPassword(mEmail, mPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                mFirebaseUser = mAuth.getCurrentUser();
                                mFirebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            String mSecret = Encryptor.GenerateSecret(mEmail);
                                            mDatabaseReference.child(mAppConstants.getNitNames().get(mSelected)).child(mSecret).setValue(
                                                    new User(
                                                            mEmail,
                                                            mName,
                                                            null
                                                    )
                                            ).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    mFirebaseUser = null;
                                                    if (!task.isSuccessful()) {
                                                        Snackbar.make(mCoordinator, task.getException().getLocalizedMessage(), Snackbar.LENGTH_LONG).show();
                                                        mButtonText.setVisibility(View.VISIBLE);
                                                        mButton.setClickable(true);
                                                        mProgress.setVisibility(View.GONE);
                                                    } else {
                                                        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                                                        intent.putExtra("newuser", 1);
                                                        startActivity(intent);
                                                        finish();
                                                    }
                                                }
                                            });
                                        } else {
                                            Snackbar.make(mCoordinator, task.getException().getLocalizedMessage(), Snackbar.LENGTH_LONG).show();
                                            mButtonText.setVisibility(View.VISIBLE);
                                            mButton.setClickable(true);
                                            mProgress.setVisibility(View.GONE);
                                        }
                                    }
                                });
                            } else {
                                Snackbar.make(mCoordinator, task.getException().getLocalizedMessage(), Snackbar.LENGTH_LONG).show();
                                mButtonText.setVisibility(View.VISIBLE);
                                mButton.setClickable(true);
                                mProgress.setVisibility(View.GONE);
                            }
                        }
                    });
                }
            }
        });
    }

}
