package com.its.sanve.api.communication;

import com.its.sanve.api.communication.sanve.SanVeResponse;
import com.its.sanve.api.communication.dto.CalculatePriceRequest;
import com.its.sanve.api.communication.dto.CalculatePriceResponse;
import com.its.sanve.api.communication.dto.OrderTicketRequest;
import com.its.sanve.api.communication.dto.PaymentRequest;

import com.its.sanve.api.entities.*;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

import java.util.List;
import java.util.Map;

public interface SanVeCommunicate {

    @GET("point/get_province_district")
    public Call<SanVeResponse<List<Province>>> getProvinceDistrict(
            @Query("api_key" ) String apiKey,
            @Query(value = "secret_key",encoded = true) String secretKey);

    @GET("companies")
    public Call<SanVeResponse<Map<String, CompanyInfo>>> getCompanies(@Query("api_key") String apiKey,
                                                                      @Query(value = "secret_key",encoded = true) String secretKey);

    @GET("company/routes")
    public Call<SanVeResponse<Map<String,RouteInfo>>> getCompanyRoute(@Query("api_key") String apiKey,
                                                                      @Query(value = "secret_key",encoded = true) String secretKey,
                                                                      @Query("companyId") String companyId);

    @GET("trips")
    public Call<SanVeResponse<Map<String,List<Trip>>>> getTripsByPoint(@Query("api_key") String apiKey,
                                                                       @Query(value = "secret_key",encoded = true) String secretKey,
                                                                       @Query("page") int page, @Query("size") int size, @Query("date") String date,
                                                                       @Query(value = "startPoint",encoded = true) String startPoint, @Query(value = "endPoint",encoded = true) String endPoint,
                                                                       @Query("startTimeFrom") String startTimeFrom, @Query("startTimeTo") String startTimeTo);
    @GET("trips")
    public Call<SanVeResponse> getTripsByRouteId(@Query("api_key") String apiKey,
                                                 @Query(value = "secret_key",encoded = true) String secretKey,
                                                 @Query("page") int page, @Query("size") int size, @Query("date") String date,
                                                 @Query("startTimeFrom") String startTimeFrom, @Query("startTimeTo") String startTimeTo, @Query("routeId") String routeId);

    @GET("trip/tickets")
    public Call<SanVeResponse<Map<String, List<Ticket>>>> getTickets(@Query("api_key") String apiKey,
                                                                     @Query(value = "secret_key",encoded = true) String secretKey, @Query("tripId") String tripId,
                                                                     @Query("pointUpId") String pointUpId, @Query("pointDownId") String pointDownId);

    @POST("order/calc_price")
    public Call<CalculatePriceResponse> calculatePrice(@Body CalculatePriceRequest request);

    @POST("order/create")
    public Call<SanVeResponse> orderTicket(@Body OrderTicketRequest request);

    @POST("order/payment")
    public Call paymennt(@Body PaymentRequest request);
}
