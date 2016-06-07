package com.wind.liveat500px.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.wind.liveat500px.view.PhotoListItem;

/**
 * Created by 015240 on 6/6/2016.
 */
public class PhotoListAdapter extends BaseAdapter {
    @Override
    public int getCount() {
        return 100;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PhotoListItem item;
        if(convertView!=null)
            item = (PhotoListItem)convertView;
        else
            item = new PhotoListItem(parent.getContext());
        return item;
    }
}
