package com.nitconclave.internitcommunication.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nitconclave.internitcommunication.Models.Notice;
import com.nitconclave.internitcommunication.R;

import java.util.ArrayList;

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.ViewHolder>{
    private ArrayList<Notice> noticeModels;
    private Context context;

    public NoticeAdapter(ArrayList<Notice> noticeModels) {
        this.noticeModels = noticeModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        context = viewGroup.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.message_recieved, viewGroup, false);
         return new ViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.textView.setText(noticeModels.get(i).getTitle());
        final String uri = noticeModels.get(i).getUrl();
//        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Uri url =Uri.parse(uri);
//                Intent intent =new Intent(Intent.ACTION_VIEW);
//                intent.setData(url);
//                context.startActivity(intent);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return noticeModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        public View itemView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text);
            this.itemView = itemView;
        }

    }
}