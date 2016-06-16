package com.wind.liveat500px.manager.http;

import com.wind.liveat500px.dao.PhotoItemCollectionDAO;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by 015240 on 6/6/2016.
 */
public interface ApiService {
    @POST("list")
    Call<PhotoItemCollectionDAO> loadPhotoList();

    @POST("list/after/{id}")
    Call<PhotoItemCollectionDAO> loadPhotoListAfter(@Path("id")int id);

    @POST("list/before/{id}")
    Call<PhotoItemCollectionDAO> loadPhotoListBefore(@Path("id")int id);


}
