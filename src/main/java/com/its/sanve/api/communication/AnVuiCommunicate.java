package com.its.sanve.api.communication;


import com.its.sanve.api.communication.anvui.AnVuiResponse;
import com.its.sanve.api.communication.dto.RouteRequest;
import com.its.sanve.api.entities.RouteInfo;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public interface AnVuiCommunicate {
    @POST("web_admin/rlogin")
    Call<AnVuiResponse<LinkedHashMap<String,Object>>> login(@Query("userName") String userName, @Query("password") String password, @Query("accessCode") String accessCode, @Query("deviceType") String deviceType, @Query("refreshToken") String refreshToken);
    @POST("route/getList")
    Call<AnVuiResponse<Map<String,List<RouteInfo>>>> getListRoute(@Body RouteRequest routeRequest);
//    @POST("planfortrip/search")
//    Call<AnVuiResponse<T>> getTrips(@Body ListTripsRequest listTripsRequest);

}
