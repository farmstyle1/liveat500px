package com.wind.liveat500px.dao;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 015240 on 6/7/2016.
 */
public class PhotoItemCollectionDAO implements Parcelable {
    @SerializedName("success")      private Boolean success;
    @SerializedName("data")         private List<PhotoItemDAO> data;

    public PhotoItemCollectionDAO(){

    }

    protected PhotoItemCollectionDAO(Parcel in) {
        data = in.createTypedArrayList(PhotoItemDAO.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(data);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PhotoItemCollectionDAO> CREATOR = new Creator<PhotoItemCollectionDAO>() {
        @Override
        public PhotoItemCollectionDAO createFromParcel(Parcel in) {
            return new PhotoItemCollectionDAO(in);
        }

        @Override
        public PhotoItemCollectionDAO[] newArray(int size) {
            return new PhotoItemCollectionDAO[size];
        }
    };

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
