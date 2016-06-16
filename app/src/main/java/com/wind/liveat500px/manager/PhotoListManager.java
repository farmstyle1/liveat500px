package com.wind.liveat500px.manager;

import android.content.Context;

import com.inthecheesefactory.thecheeselibrary.manager.Contextor;
import com.wind.liveat500px.dao.PhotoItemCollectionDAO;
import com.wind.liveat500px.dao.PhotoItemDAO;

import java.util.ArrayList;

/**
 * Created by nuuneoi on 11/16/2014.
 */
public class PhotoListManager {



    private Context mContext;
    private PhotoItemCollectionDAO dao;

    public PhotoListManager() {
        mContext = Contextor.getInstance().getContext();
    }

    public PhotoItemCollectionDAO getDao() {
        return dao;
    }

    public void setDao(PhotoItemCollectionDAO dao) {
        this.dao = dao;
    }

    public int getMaximumId(){
        if(dao == null)
            return 0;
        if(dao.getData() == null)
            return 0;
        if (dao.getData().size() == 0)
            return 0;
        int maxId = dao.getData().get(0).getId();
        for(int i =1 ;i < dao.getData().size(); i++)
            maxId = Math.max(maxId,dao.getData().get(i).getId());

        return maxId;
    }

    public int getMinimumId(){
        if(dao == null)
            return 0;
        if(dao.getData() == null)
            return 0;
        if (dao.getData().size() == 0)
            return 0;
        int minId = dao.getData().get(0).getId();
        for(int i =1 ;i < dao.getData().size(); i++)
            minId = Math.min(minId,dao.getData().get(i).getId());

        return minId;
    }

    public void insertDaoAtTopPosition(PhotoItemCollectionDAO newDao){
        if(newDao == null)
            dao = new PhotoItemCollectionDAO();
        if(newDao.getData() == null)
            dao.setData(new ArrayList<PhotoItemDAO>());
        dao.getData().addAll(0,newDao.getData());

    }

    public void appendDaoAtBottomPosition(PhotoItemCollectionDAO newDao){
        if(newDao == null)
            dao = new PhotoItemCollectionDAO();
        if(newDao.getData() == null)
            dao.setData(new ArrayList<PhotoItemDAO>());
        dao.getData().addAll(dao.getData().size(),newDao.getData());

    }

    public int getCount(){
        if(dao == null)
            return 0;
        if(dao.getData() == null)
            return 0;
        return dao.getData().size();
    }
}
