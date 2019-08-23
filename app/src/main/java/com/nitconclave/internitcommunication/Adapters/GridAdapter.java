package com.nitconclave.internitcommunication.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nitconclave.internitcommunication.Models.GridItems;
import com.nitconclave.internitcommunication.R;

import java.util.ArrayList;


public class GridAdapter extends BaseAdapter {
    private ArrayList<GridItems> mSelectedInterests;
    private Context mContext;

    public GridAdapter(Context context, ArrayList<GridItems> mSelectedInterests) {
        this.mSelectedInterests = mSelectedInterests;
        mContext = context;
    }

    @Override
    public int getCount() {
        Log.e("this", "" + mSelectedInterests.size());
        return mSelectedInterests.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        GridItems gridItem = mSelectedInterests.get(i);

        if (view == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            view = layoutInflater.inflate(R.layout.grid_items, null);
        }

        ImageView imageView = (ImageView) view.findViewById(R.id.cancel);
        TextView nameTextView = (TextView) view.findViewById(R.id.text);

        imageView.setImageDrawable(mSelectedInterests.get(i).getmRemove());
        nameTextView.setText(mSelectedInterests.get(i).getmInterest());

        return view;
    }
}
