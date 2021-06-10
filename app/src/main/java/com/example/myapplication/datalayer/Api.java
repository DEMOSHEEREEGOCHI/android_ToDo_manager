package com.example.myapplication.datalayer;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Api {
    @POST("user/login")
    Call<JsonObject> login(@Body JsonObject json);

    @POST("user/registration")
    Call<JsonObject> registration(@Body JsonObject json);

    @GET("todos")
    Call<JsonObject> getTodos(@Query("userId") String userId);










}
