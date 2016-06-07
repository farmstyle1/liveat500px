package com.wind.liveat500px.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.wind.liveat500px.dao.PhotoItemDAO;
import com.wind.liveat500px.manager.PhotoListManager;
import com.wind.liveat500px.view.PhotoListItem;

/**
 * Created by 015240 on 6/6/2016.
 */
public class PhotoListAdapter extends BaseAdapter {
    @Override
    public int getCount() {
        if(PhotoListManager.getInstance().getDao() == null)
            return  0;
        if(PhotoListManager.getInstance().getDao().getData() == null)
            return 0;
        return PhotoListManager.getInstance().getDao().getData().size();
    }

    @Override
    public Object getItem(int position) {
        return PhotoListManager.getInstance().getDao().getData().get(position);
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
        PhotoItemDAO dao = (PhotoItemDAO) getItem(position);
        item.setNameText(dao.getCaption());
        item.setDescriptionText(dao.getUserName()+"\n"+dao.getCamera());
        item.setImageUrl(dao.getImageUrl());
        return item;
    }
}
