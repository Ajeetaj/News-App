package com.example.mygallery;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface RetrofitAPI {
    @GET
    Call<DataModelClass> getAllNews(@Url String url);
}
