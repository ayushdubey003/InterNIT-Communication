package com.nitconclave.internitcommunication.Activities;

import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.nitconclave.internitcommunication.Adapters.MessageAdapter;
import com.nitconclave.internitcommunication.Helpers.AppConstants;
import com.nitconclave.internitcommunication.Models.MessageModel;
import com.nitconclave.internitcommunication.Models.User;
import com.nitconclave.internitcommunication.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Objects;
import java.util.Random;

public class MessageActivity2 extends AppCompatActivity {

    private static final int REQ_CODE_IMAGE_INPUT = 1;
    private String uid;
    private String userUid;
    private String userName;
    private ImageView addButton;
    private EditText messageEdit;
    private Button sendButton;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private ArrayList<MessageModel> messageModels;
    private DatabaseReference databaseReference;
    private MessageAdapter messageAdapter;
    private AppConstants appConstants;
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        Intent intent = getIntent();
        final String name = Objects.requireNonNull(intent.getExtras()).getString("name");
        uid = Objects.requireNonNull(intent.getExtras()).getString("uid");
        User mUser = appConstants.getmUser();
        String id = appConstants.getmSecret();

        Toolbar toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.recyclerView);
        addButton = findViewById(R.id.addMessageImageView);
        messageEdit = findViewById(R.id.messageEditText);
        sendButton = findViewById(R.id.sendButton);
        progressBar = findViewById(R.id.progressBar);
        appConstants = new AppConstants(this);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MessageActivity2.this));

        messageModels = new ArrayList<>();
        userUid = id;
        userName = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getDisplayName();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        populateMessage();

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = messageEdit.getText().toString().trim();
                if(!message.equals("")){
                    messageEdit.setText("");
                    Random random =  new Random();
                    long rand = random.nextLong();
                    String randomString = Long.toString(rand);
                    Date date = new Date();
                    MessageModel messageModel= new MessageModel(null, message, null, date);
                    databaseReference.child(uid).child("message").child(userUid).child(randomString).setValue(messageModel);
                    databaseReference.child(uid).child("notify").child(userUid).child("notify").setValue(true);
                    databaseReference.child(uid).child("notify").child(userUid).child("userName").setValue(userName);
                    messageModel.setSender(true);
                    messageModels.add(messageModel);
                    messageAdapter.notifyDataSetChanged();
                    recyclerView.scrollToPosition(messageAdapter.getItemCount() - 1);
                }
            }
        });
    }

    private void populateMessage() {
        databaseReference.child(uid).child("message").child(userUid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot data: dataSnapshot.getChildren()){
                    MessageModel message = data.getValue(MessageModel.class);
                    assert message != null;
                    message.setSender(true);
                    messageModels.add(message);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        databaseReference.child(userUid).child("message").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot data: dataSnapshot.getChildren()){
                    MessageModel message = data.getValue(MessageModel.class);
                    assert message != null;
                    if(!message.getRead()){
                        message.setRead(true);
                        data.child("isRead").getRef().setValue(true);
                    }
                    message.setSender(false);
                    messageModels.add(message);
                }
                Collections.sort(messageModels, new Comparator<MessageModel>() {
                    @Override
                    public int compare(MessageModel o1, MessageModel o2) {
                        return o1.getDate().compareTo(o2.getDate());
                    }
                });
                messageAdapter = new MessageAdapter(messageModels);
                recyclerView.setAdapter(messageAdapter);
                recyclerView.scrollToPosition(messageAdapter.getItemCount() - 1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        databaseReference.child(userUid).child("notify").child(uid).child("notify").setValue(false);

        progressBar.setVisibility(View.INVISIBLE);

        databaseReference.child(userUid).child("message").child(uid).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if(messageAdapter != null){
                    MessageModel message = dataSnapshot.getValue(MessageModel.class);
                    assert message != null;
                    if (!message.getRead()) {
                        message.setRead(true);
                        dataSnapshot.child("isRead").getRef().setValue(true);
                        message.setSender(false);
                        if (!messageModels.contains(message)) {
                            messageModels.add(message);
                            messageAdapter.notifyDataSetChanged();
                            databaseReference.child(userUid).child("notify").child(uid).child("notify").setValue(false);
                            recyclerView.scrollToPosition(messageAdapter.getItemCount() - 1);
                        }
                    }
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void chooseImage() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto , REQ_CODE_IMAGE_INPUT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQ_CODE_IMAGE_INPUT && data != null){
            uploadImage(data);
        }
    }

    private void uploadImage(@Nullable Intent data) {
        final Uri selectedImage;

        if(data != null)
            selectedImage = data.getData();
        else
            selectedImage = null;

        if(selectedImage != null){
            final String randomString;
            Random random =  new Random();
            long rand = random.nextLong();
            randomString = Long.toString(rand);

            final StorageReference newReference = FirebaseStorage.getInstance().getReference().child("message").child(userUid).child(randomString);

            progressBar.setVisibility(View.VISIBLE);
            addButton.setEnabled(false);
            messageEdit.setEnabled(false);
            sendButton.setEnabled(false);

            newReference.putFile(selectedImage)
                    .addOnSuccessListener(MessageActivity2.this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            newReference.getDownloadUrl().addOnSuccessListener(MessageActivity2.this, new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Date date = new Date();
                                    final MessageModel messageModel= new MessageModel(null, null, uri.toString(), date);
                                    databaseReference.child(uid).child("message").child(userUid).child(randomString).setValue(messageModel)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    messageModel.setSender(true);
                                                    messageModels.add(messageModel);
                                                    messageAdapter.notifyDataSetChanged();
                                                    recyclerView.scrollToPosition(messageAdapter.getItemCount() - 1);
                                                }
                                            });
                                }
                            });
                            progressBar.setVisibility(View.INVISIBLE);
                            addButton.setEnabled(true);
                            messageEdit.setEnabled(true);
                            sendButton.setEnabled(true);
                            databaseReference.child(uid).child("notify").child(userUid).child("notify").setValue(true);
                            databaseReference.child(uid).child("notify").child(userUid).child("userName").setValue(userName);
                        }
                    })
                    .addOnFailureListener(MessageActivity2.this, new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressBar.setVisibility(View.INVISIBLE);
                            addButton.setEnabled(true);
                            messageEdit.setEnabled(true);
                            sendButton.setEnabled(true);
                        }
                    });
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}