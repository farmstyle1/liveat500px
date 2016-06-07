package com.wind.liveat500px.manager;

import android.content.Context;

import com.inthecheesefactory.thecheeselibrary.manager.Contextor;
import com.wind.liveat500px.dao.PhotoItemCollectionDAO;

/**
 * Created by nuuneoi on 11/16/2014.
 */
public class PhotoListManager {

    private static PhotoListManager instance;

    public static PhotoListManager getInstance() {
        if (instance == null)
            instance = new PhotoListManager();
        return instance;
    }

    private Context mContext;
    private PhotoItemCollectionDAO dao;

    private PhotoListManager() {
        mContext = Contextor.getInstance().getContext();
    }

    public PhotoItemCollectionDAO getDao() {
        return dao;
    }

    public void setDao(PhotoItemCollectionDAO dao) {
        this.dao = dao;
    }
}
