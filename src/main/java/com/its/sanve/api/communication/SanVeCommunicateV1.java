package com.its.sanve.api.communication;

import com.its.sanve.api.communication.sanve.SanVeV1Response;
import com.its.sanve.api.entities.Point;

import com.its.sanve.api.entities.Trip;
import retrofit2.Call;
import retrofit2.http.Query;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import springfox.documentation.spring.web.json.Json;

import java.util.List;

public interface SanVeCommunicateV1 {
    @GET("get-list-point")
    public Call<Object> getListPoint(
            @Query("api_key" ) String apiKey,
            @Query(value = "secret_key",encoded = true) String secretKey);
    @GET("get-list-trips-by-number")
    public Call<SanVeV1Response<List<Trip>>> getListTripsByNumber(
            @Query("api_key" ) String apiKey,
            @Query(value = "secret_key",encoded = true) String secretKey,@Query("pointUp") String pointUp,@Query("pointDown") String pointDown,@Query("starttime")String startDay,@Query("companyId") String companyId,@Query("numberTicket") int numberTicket);
}