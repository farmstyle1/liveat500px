package com.wind.liveat500px.manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.gson.Gson;
import com.inthecheesefactory.thecheeselibrary.manager.Contextor;
import com.wind.liveat500px.dao.PhotoItemCollectionDAO;
import com.wind.liveat500px.dao.PhotoItemDAO;

import java.util.ArrayList;


public class PhotoListManager {



    private Context mContext;
    private PhotoItemCollectionDAO dao;

    public PhotoListManager() {
        mContext = Contextor.getInstance().getContext();

        loadCache();

    }

    public PhotoItemCollectionDAO getDao() {
        return dao;
    }

    public void setDao(PhotoItemCollectionDAO dao) {
        this.dao = dao;
        saveCache();
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
        saveCache();
    }

    public void appendDaoAtBottomPosition(PhotoItemCollectionDAO newDao){
        if(newDao == null)
            dao = new PhotoItemCollectionDAO();
        if(newDao.getData() == null)
            dao.setData(new ArrayList<PhotoItemDAO>());
        dao.getData().addAll(dao.getData().size(),newDao.getData());
        saveCache();
    }

    public int getCount(){
        if(dao == null)
            return 0;
        if(dao.getData() == null)
            return 0;
        return dao.getData().size();
    }


    public Bundle onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("dao",dao);
        return bundle;

    }

    public void onRestoreInstanceState(Bundle savedInstanceState){
        dao = savedInstanceState.getParcelable("dao");
    }

    private void saveCache(){
        PhotoItemCollectionDAO cacheDao = new PhotoItemCollectionDAO();
        if(dao != null & dao.getData() != null)
            cacheDao.setData(dao.getData().subList(0,Math.min(20,dao.getData().size())));
        String json = new Gson().toJson(cacheDao);

        SharedPreferences prefs = mContext.getSharedPreferences("photos",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("json",json);
        editor.apply();
    }

    private void loadCache(){
        SharedPreferences prefs = mContext.getSharedPreferences("photos",Context.MODE_PRIVATE);
        String json = prefs.getString("json",null);
        if(json == null)
            return;
        dao = new Gson().fromJson(json,PhotoItemCollectionDAO.class);
    }

}
