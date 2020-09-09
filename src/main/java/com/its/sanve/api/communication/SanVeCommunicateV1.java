package com.its.sanve.api.communication;

import com.its.sanve.api.entities.Point;

import retrofit2.Call;
import retrofit2.http.Query;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

import java.util.List;

public interface SanVeCommunicateV1 {
    @GET("get-list-point")
    public Call<String> getListPoint(
            @Query("api_key" ) String apiKey,
            @Query(value = "secret_key",encoded = true) String secretKey);
}