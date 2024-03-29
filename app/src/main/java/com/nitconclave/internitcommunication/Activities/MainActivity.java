package com.nitconclave.internitcommunication.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nitconclave.internitcommunication.Helpers.AppConstants;
import com.nitconclave.internitcommunication.Helpers.Encryptor;
import com.nitconclave.internitcommunication.Models.User;
import com.nitconclave.internitcommunication.R;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    //Declaration
    private ImageView mImageView;
    private AppConstants mAppConstants;
    private ArrayList<Drawable> mLogos;
    private int currIndex;
    private final String LOG_TAG = "Main";
    private EditText mEditText;
    private ImageView mErrorImageView;
    private ImageView mLogin;
    private TextView mLoginText;
    private ProgressBar mLoginProgress;
    private TextView mSignUp;
    private boolean mVerified;
    private String mEmail;
    private boolean mValidEmail;
    private HashMap<String, String> mDomains;
    private CoordinatorLayout mCoordinator;
    private boolean update;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mDatabaseReference;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private String mCollegeName;
    private ValueEventListener mValueEventListener;
    private EditText mPasswordEdit;
    private String mPassword;
    private User mUserDetails;
    private TextView mForgot;
    private ProgressBar mProgress;
    private ImageView mBack;
    private ConstraintLayout mC1;
    private ConstraintLayout mC2;
    private ImageView mLogo1;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialization
        FirebaseApp.initializeApp(this);
        mImageView = findViewById(R.id.logo);
        mAppConstants = new AppConstants(this);
        mLogos = mAppConstants.getLogos();
        currIndex = 0;
        mEditText = findViewById(R.id.email);
        mErrorImageView = findViewById(R.id.error);
        mLogin = findViewById(R.id.button);
        mLoginText = findViewById(R.id.button_text);
        mLoginProgress = findViewById(R.id.button_progress);
        mSignUp = findViewById(R.id.sign_up);
        mVerified = false;
        mDomains = mAppConstants.getShortForms();
        mCoordinator = findViewById(R.id.coordinator);
        update = true;
        mDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mDatabase.getReference().child("users");
        mAuth = FirebaseAuth.getInstance();
        mPasswordEdit = findViewById(R.id.password);
        mForgot = findViewById(R.id.forgot);
        mProgress = findViewById(R.id.forgot_progress);
        mBack = findViewById(R.id.back);
        mC1 = findViewById(R.id.constraintLayout1);
        mC2 = findViewById(R.id.constraintLayout2);
        mLogo1 = findViewById(R.id.logo1);
        mLogos = new AppConstants(this).getLogos();
        updateLogo1(12);
        displayScreenOne();
    }

    private void updateLogo1(int ind) {
        if (!update)
            return;
        if (ind >= mLogos.size())
            ind = 0;
        mLogo1.setImageDrawable(mLogos.get(ind++));
        final int ind1 = ind;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                updateLogo1(ind1);
            }
        }, 1000);
    }

    private void updateLogo() {
        if (!update)
            return;
        if (currIndex >= mLogos.size())
            currIndex = 0;
        mImageView.setImageDrawable(mLogos.get(currIndex++));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                updateLogo();
            }
        }, 2500);
    }

    @Override
    protected void onStart() {
        super.onStart();

        int y = getIntent().getIntExtra("newuser", 0);
        if (y == 1) {
            Snackbar.make(mCoordinator, "Please verify your email and sign in !", Snackbar.LENGTH_LONG).show();
            mC2.setVisibility(View.VISIBLE);
            mC1.setVisibility(View.GONE);
            return;
        }
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        if (mUser != null) {
            mEmail = mUser.getEmail();
            String val[] = mEmail.split("@");
            val[0] = val[0].toUpperCase();
            mEmail = val[0] + "@" + val[1];
            String x[] = val[1].split("\\.");
            int co = 0;
            Log.e(LOG_TAG, x[0]);
            for (int i = 0; i < mAppConstants.getDomains().size(); i++) {
                if (x[0].equalsIgnoreCase(mAppConstants.getDomains().get(i))) {
                    co = i;
                    break;
                }
            }
            mCollegeName = mAppConstants.getNitNames().get(co);
            Log.e(LOG_TAG, mCollegeName);
            final String secret = Encryptor.GenerateSecret(mEmail);
            mDatabaseReference.child(mCollegeName).child(secret).addValueEventListener(mValueEventListener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    mUserDetails = dataSnapshot.getValue(User.class);
                    if (mUserDetails != null) {
                        mDatabaseReference.child(mCollegeName).child(secret).removeEventListener(mValueEventListener);
                        if (!mUserDetails.ismInterests()) {
                            final Intent intent = new Intent(MainActivity.this, UserActivity.class);
                            mUserDetails.setmInterests();
                            mDatabaseReference.child(mCollegeName).child(secret).setValue(mUserDetails).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    mAppConstants.setmSecret(secret);
                                    mAppConstants.setmUser(mUserDetails);
                                    startActivity(intent);
                                    finish();
                                }
                            });
                        } else {
                            mAppConstants.setmUser(mUserDetails);
                            startActivity(new Intent(MainActivity.this, DisplayActivity.class));
                            finish();
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            return;
        }
        mC2.setVisibility(View.VISIBLE);
        mC1.setVisibility(View.GONE);
    }

    private boolean detectInternet() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    private void displayScreenOne() {
        mSignUp.setText("Not a member ? Sign Up here!");
        mLoginText.setText("Next");
        mEditText.setVisibility(View.VISIBLE);
        mPasswordEdit.setVisibility(View.GONE);
        mProgress.setVisibility(View.GONE);
        mForgot.setVisibility(View.GONE);
        mBack.setVisibility(View.GONE);

        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mValidEmail = !TextUtils.isEmpty(charSequence) && Patterns.EMAIL_ADDRESS.matcher(charSequence).matches();
                mVerified = mValidEmail;
                if (!mVerified) {
                    mErrorImageView.setVisibility(View.VISIBLE);
                    mErrorImageView.setImageDrawable(getDrawable(R.drawable.ic_error_black_24dp));
                } else {
                    mEmail = charSequence.toString();
                    String chars[] = mEmail.split("@");
                    String domain[] = chars[1].split("\\.");
                    if (mDomains.containsKey(domain[0].toLowerCase())) {
                        mVerified = true;
                        String value = mDomains.get(domain[0].toLowerCase());
                        String college[] = value.split("\\$");
                        int co = Integer.parseInt(college[1].trim());
                        mErrorImageView.setImageDrawable(mLogos.get(co - 1));
                        mCollegeName = mAppConstants.getNitNames().get(co - 1);
                    } else {
                        mErrorImageView.setVisibility(View.VISIBLE);
                        mErrorImageView.setImageDrawable(getDrawable(R.drawable.ic_error_black_24dp));
                        mVerified = false;
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mVerified)
                    Snackbar.make(mCoordinator, "Invalid email address", Snackbar.LENGTH_LONG).show();
                else {
                    mLogin.setEnabled(false);
                    mLoginText.setVisibility(View.GONE);
                    mLoginProgress.setVisibility(View.VISIBLE);
                    if (detectInternet()) {
                        String val[] = mEmail.split("@");
                        val[0] = val[0].toUpperCase();
                        mEmail = val[0] + "@" + val[1];
                        mDatabaseReference.child(mCollegeName).child(Encryptor.GenerateSecret(mEmail)).addValueEventListener(mValueEventListener = new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                mUserDetails = dataSnapshot.getValue(User.class);
                                if (mUserDetails != null) {
                                    mDatabaseReference.child(mCollegeName).removeEventListener(mValueEventListener);
                                    mLoginProgress.setVisibility(View.GONE);
                                    mLoginText.setVisibility(View.VISIBLE);
                                    displayScreenTwo(Encryptor.GenerateSecret(mEmail));
                                } else {
                                    Snackbar.make(mCoordinator, "No such user exists !", Snackbar.LENGTH_LONG).show();
                                    mDatabaseReference.child(mCollegeName).removeEventListener(mValueEventListener);
                                    mLogin.setEnabled(true);
                                    mLoginText.setVisibility(View.VISIBLE);
                                    mLoginProgress.setVisibility(View.GONE);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    } else {
                        Snackbar.make(mCoordinator, "No Internet Detected !", Snackbar.LENGTH_LONG).show();
                        mLogin.setEnabled(true);
                        mLoginText.setVisibility(View.VISIBLE);
                        mLoginProgress.setVisibility(View.GONE);
                    }
                }
            }
        });
        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SignUpActivity.class));
            }
        });
        updateLogo();
    }

    private void displayScreenTwo(final String secret) {
        mForgot.setVisibility(View.VISIBLE);
        mLoginText.setText("Log In");
        String genericText = "Hey " + mUserDetails.getmName();
        mSignUp.setText(genericText);
        mPasswordEdit.setVisibility(View.VISIBLE);
        mEditText.setVisibility(View.GONE);
        mBack.setVisibility(View.VISIBLE);

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayScreenOne();
            }
        });

        mLogin.setEnabled(true);
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mLogin.setEnabled(false);
                mLoginText.setVisibility(View.GONE);
                mLoginProgress.setVisibility(View.VISIBLE);
                mPassword = mPasswordEdit.getText().toString();
                if (mPassword.isEmpty())
                    Snackbar.make(mCoordinator, "Password field is blank", Snackbar.LENGTH_LONG).show();
                else {
                    mAuth.signInWithEmailAndPassword(mEmail, mPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            mLoginText.setVisibility(View.VISIBLE);
                            mLoginProgress.setVisibility(View.GONE);
                            mLogin.setEnabled(true);
                            task.addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    if (!authResult.getUser().isEmailVerified())
                                        resendVerificationMail(authResult.getUser());
                                    else {
                                        mUser = authResult.getUser();
                                        mLogin.setEnabled(true);
                                        mLoginProgress.setVisibility(View.GONE);
                                        mLoginText.setVisibility(View.VISIBLE);
                                        mDatabaseReference.child(mCollegeName).child(secret).removeEventListener(mValueEventListener);
                                        if (!mUserDetails.ismInterests()) {
                                            final Intent intent = new Intent(MainActivity.this, UserActivity.class);
                                            mUserDetails.setmInterests();
                                            mAppConstants.setmUser(mUserDetails);
                                            mDatabaseReference.child(mCollegeName).child(secret).setValue(mUserDetails).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    mAppConstants.setmSecret(secret);
                                                    mAppConstants.setmUser(mUserDetails);
                                                    startActivity(intent);
                                                    finish();
                                                }
                                            });
                                        } else {
                                            mAppConstants.setmSecret(secret);
                                            mAppConstants.setmUser(mUserDetails);
                                            startActivity(new Intent(MainActivity.this, DisplayActivity.class));
                                            finish();
                                        }
                                    }
                                }
                            });
                        }
                    });
                }
            }
        });

        mForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mForgot.setVisibility(View.GONE);
                mProgress.setVisibility(View.VISIBLE);
                mAuth.sendPasswordResetEmail(mEmail).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        mForgot.setVisibility(View.VISIBLE);
                        mProgress.setVisibility(View.GONE);
                        Snackbar.make(mCoordinator, "Password Reset Email sent successfully", Snackbar.LENGTH_LONG).show();
                    }
                });
                mAuth.sendPasswordResetEmail(mEmail).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        mForgot.setVisibility(View.VISIBLE);
                        mProgress.setVisibility(View.GONE);
                        Snackbar.make(mCoordinator, e.getLocalizedMessage(), Snackbar.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

    void resendVerificationMail(final FirebaseUser user) {
        final Snackbar snackbar = Snackbar.make(mCoordinator, "Email not verified", Snackbar.LENGTH_LONG).
                setAction("Send Verification Email ?", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mLogin.setEnabled(false);
                        Snackbar bar = Snackbar.make(mCoordinator, "Sending...", Snackbar.LENGTH_INDEFINITE);
                        ViewGroup contentLay = (ViewGroup) bar.getView().findViewById(com.google.android.material.R.id.snackbar_text).getParent();
                        ProgressBar item = new ProgressBar(MainActivity.this);
                        contentLay.addView(item, 0);
                        bar.show();
                        user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                mLogin.setEnabled(true);
                                task.addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Snackbar.make(mCoordinator, "Verification email sent successfully", Snackbar.LENGTH_LONG).show();
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
        snackbar.show();
    }
}
