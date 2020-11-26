package com.growin.silveryogaapp;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.net.URI;
import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {

    private ArrayList<ListViewItem> lstViewItem = new ArrayList<ListViewItem>();

    public ListViewAdapter(ArrayList<ListViewItem> data) {
        this.lstViewItem = data;
    }

    @Override
    public int getCount() {
        return lstViewItem.size();
    }

    @Override
    public Object getItem(int position) {
        return lstViewItem.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listveiw_item, parent, false);

        ImageView iconImageView = convertView.findViewById(R.id.item_image) ;
        TextView titleTextView = convertView.findViewById(R.id.item_text) ;

        ListViewItem vItem = lstViewItem.get(position);
        Glide.with(convertView).load(vItem.getUri()).into(iconImageView);
        titleTextView.setText(vItem.getTitle());
        return convertView;

    }



}
