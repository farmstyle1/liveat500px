package com.wind.liveat500px.manager;

import android.content.Context;

import com.inthecheesefactory.thecheeselibrary.manager.Contextor;
import com.wind.liveat500px.dao.PhotoItemCollectionDAO;

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
        int maxId = dao.getData().get(0).getUserId();
        for(int i =1 ;i < dao.getData().size(); i++)
            maxId = Math.max(maxId,dao.getData().get(i).getUserId());

        return maxId;
    }

    public int getCount(){
        if(dao == null)
            return 0;
        if(dao.getData() == null)
            return 0;
        return dao.getData().size();
    }
}
