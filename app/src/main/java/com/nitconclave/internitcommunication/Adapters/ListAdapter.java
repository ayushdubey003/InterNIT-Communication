package com.nitconclave.internitcommunication.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.nitconclave.internitcommunication.Models.ListItems;
import com.nitconclave.internitcommunication.R;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends ArrayAdapter<ListItems> {
    private Context mContext;
    private ArrayList<ListItems> listItems;

    public ListAdapter(Context context, int resource, ArrayList<ListItems> objects) {
        super(context, resource, objects);
        mContext = context;
        listItems = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.fragment_discover_items, null, false);
        }
        TextView name = convertView.findViewById(R.id.recommended_name);
        TextView college = convertView.findViewById(R.id.college);

        name.setText(listItems.get(position).getmName());
        college.setText(listItems.get(position).getmCollegeName());
        return convertView;
    }
}
