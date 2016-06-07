package com.wind.liveat500px.manager.http;

import com.wind.liveat500px.dao.PhotoItemCollectionDAO;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.http.POST;

/**
 * Created by 015240 on 6/6/2016.
 */
public interface ApiService {
    @POST("list")
    Call<PhotoItemCollectionDAO> loadPhotoList();
}
