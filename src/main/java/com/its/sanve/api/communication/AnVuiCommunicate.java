package com.its.sanve.api.communication;


import com.its.sanve.api.communication.anvui.AnVuiResponse;
import com.its.sanve.api.communication.dto.OldCustomerRequest;
import com.its.sanve.api.communication.dto.PriceTicketRequest;
import com.its.sanve.api.communication.dto.RouteRequest;
import com.its.sanve.api.communication.dto.TripsRequest;
import com.its.sanve.api.entities.Customer;
import com.its.sanve.api.entities.RouteInfo;
import com.its.sanve.api.entities.Trip;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public interface AnVuiCommunicate {
    @POST("web_admin/rlogin")
    Call<AnVuiResponse<LinkedHashMap<String,Object>>> login(@Query("userName") String userName, @Query(value = "password",encoded = true) String password, @Query("accessCode") String accessCode, @Query("deviceType") String deviceType, @Query("refreshToken") String refreshToken);
    @POST("route/getList")
    Call<AnVuiResponse<Map<String,List<RouteInfo>>>> getListRoute(@Body RouteRequest routeRequest);
    @POST("planfortrip/search")
    Call<AnVuiResponse<LinkedHashMap<String,List<Trip>>>> getTrips(@Header("DOBODY6969")String token, @Body TripsRequest TripsRequest);
    @POST("sanve/get-price-ticket")
    Call<AnVuiResponse<Map<String, Map<String,Double>>>> getPriceTicket(@Header("DOBODY6969")String token, @Body PriceTicketRequest request);
    @POST("company_customer/searchByPhoneNumber")
    Call<AnVuiResponse<LinkedHashMap<String,Customer[]>>> oldCustomer(@Header("DOBODY6969")String token, @Body OldCustomerRequest request);

}
