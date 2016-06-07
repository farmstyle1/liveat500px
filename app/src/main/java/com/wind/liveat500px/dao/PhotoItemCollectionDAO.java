package com.wind.liveat500px.dao;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 015240 on 6/7/2016.
 */
public class PhotoItemCollectionDAO {
    @SerializedName("success")      private Boolean success;
    @SerializedName("data")         private List<PhotoItemDAO> data;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public List<PhotoItemDAO> getData() {
        return data;
    }

    public void setData(List<PhotoItemDAO> data) {
        this.data = data;
    }
}
