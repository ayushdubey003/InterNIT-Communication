package com.nitconclave.internitcommunication.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.nitconclave.internitcommunication.Activities.MessageActivity;
import com.nitconclave.internitcommunication.Helpers.AppConstants;
import com.nitconclave.internitcommunication.Models.MessageModel;
import com.nitconclave.internitcommunication.Models.User;
import com.nitconclave.internitcommunication.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder>{
    private ArrayList<MessageModel> messageModels;
    private Context context;
    private String userName;
    private AppConstants appConstants;
    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;

    public MessageAdapter(ArrayList<MessageModel> messageModels) {
        this.messageModels = messageModels;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public int getItemViewType(int position) {
        MessageModel messageModel = messageModels.get(position);
        userName = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        if(messageModel.getUserName().equals(userName))
            return VIEW_TYPE_MESSAGE_SENT;
        else
            return VIEW_TYPE_MESSAGE_RECEIVED;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        context = viewGroup.getContext();
        if(viewType == VIEW_TYPE_MESSAGE_SENT){
            View view = LayoutInflater.from(context).inflate(R.layout.message_sent, viewGroup, false);
            return new ViewHolder(view);
        }
        else {
            View view = LayoutInflater.from(context).inflate(R.layout.message_recieved, viewGroup, false);
            return new ViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        if(messageModels.get(i).getImageUrl() != null){
            Uri uri = Uri.parse(messageModels.get(i).getImageUrl());
            Glide.with(context)
                    .load(uri)
                    .into(viewHolder.imageView);
//            viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(context, ImageViewActivity.class);
//                    intent.putExtra("url", messageModels.get(i).getImageUrl());
//                    context.startActivity(intent);
//                }
//            });
        }
        else{
            Glide.with(context)
                    .load((Bitmap) null)
                    .into(viewHolder.imageView);
        }

        if(messageModels.get(i).getMessage() != null)
            viewHolder.textView.setText(messageModels.get(i).getMessage());
        else
            viewHolder.textView.setText(null);
        if(messageModels.get(i).getUserName() != null)
        viewHolder.user.setText(messageModels.get(i).getUserName());

        Date date = messageModels.get(i).getDate();
        SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, hh:mm a");
        String strDate = formatter.format(date);
        viewHolder.dateView.setText(strDate);
    }

    @Override
    public int getItemCount() {
        return messageModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView textView;
        private TextView dateView;
        private TextView user;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
            user = itemView.findViewById(R.id.user);
            textView = itemView.findViewById(R.id.text);
            dateView = itemView.findViewById(R.id.time);
        }
    }
}