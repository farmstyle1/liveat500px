package com.wind.liveat500px.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;

import com.wind.liveat500px.R;
import com.wind.liveat500px.dao.PhotoItemCollectionDAO;
import com.wind.liveat500px.dao.PhotoItemDAO;
import com.wind.liveat500px.manager.PhotoListManager;
import com.wind.liveat500px.view.PhotoListItem;

/**
 * Created by 015240 on 6/6/2016.
 */
public class PhotoListAdapter extends BaseAdapter {
    private PhotoItemCollectionDAO dao;

    // เอาไว้เช็ค ถ้า position ในการให้ animation พุ่งขึ้น  ถ้า posรtion > lastposรtion ทำงานให้ทำการเรียกใช้ animation
    private int lastPosition = -1;

    public void setDao(PhotoItemCollectionDAO dao) {
        this.dao = dao;
    }

    @Override
    public int getCount() {
        if(dao == null)
            return  0;
        if(dao.getData() == null)
            return 0;
        return dao.getData().size();
    }

    @Override
    public Object getItem(int position) {
        return dao.getData().get(position);
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
        item.setDescriptionText(dao.getUserName() + "\n" + dao.getCamera());
        item.setImageUrl(dao.getImageUrl());

        if(position > lastPosition) {
            Animation anim = AnimationUtils.loadAnimation(parent.getContext(), R.anim.up_from_bottom);
            item.startAnimation(anim);
        }
        return item;
    }
}
