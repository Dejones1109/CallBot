package com.its.sanve.api.communication;

import com.its.sanve.api.communication.SanVe.SanveResponse;
import com.its.sanve.api.communication.SanVe.Order_Cal_Price.dto.CalculatePriceRequest;
import com.its.sanve.api.communication.SanVe.Order_Cal_Price.dto.CalculatePriceResponse;
import com.its.sanve.api.communication.dto.OrderTicketRequest;
import com.its.sanve.api.communication.dto.PaymentRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface SanVeCommunicate {

    @GET("point/get_province_district")
    public Call<SanveResponse> getProvinceDistrict(
            @Query("api_key") String apiKey,
            @Query("secret_key") String secretKey);

    @GET("companies")
    public Call<SanveResponse> getCompanies(@Query("api_key") String apiKey,
                                            @Query("secret_key") String secretKey);

    @GET("company/routes")
    public Call<SanveResponse> getCompanyRoute(@Query("api_key") String apiKey,
                                                 @Query("secret_key") String secretKey,
                                                 @Query("companyId") String companyId);

    @GET("trips")
    public Call<SanveResponse> getTripsByPoint(@Query("api_key") String apiKey,
                                            @Query("secret_key") String secretKey,
                                            @Query("page") int page, @Query("size") int size, @Query("date") String date,
                                            @Query(value = "startPoint",encoded = true) String startPoint, @Query(value = "endPoint",encoded = true) String endPoint,
                                            @Query("startTimeFrom") String startTimeFrom, @Query("startTimeTo") String startTimeTo);

    @GET("trips")
    public Call<SanveResponse> getTripsByRouteId(@Query("api_key") String apiKey,
                                              @Query("secret_key") String secretKey,
                                              @Query("page") int page, @Query("size") int size, @Query("date") String date,
                                              @Query("startTimeFrom") String startTimeFrom, @Query("startTimeTo") String startTimeTo,@Query("routeId") String routeId);

    @GET("trip/tickets")
    public Call<SanveResponse> getTickets(@Query("api_key") String apiKey,
                                         @Query("secret_key") String secretKey, @Query("tripId") String tripId,
                                         @Query("pointUpId") String pointUpId, @Query("pointDownId") String pointDownId);

    @POST("order/calc_price")
    public Call<CalculatePriceResponse> calculatePrice(@Body CalculatePriceRequest request);

    @POST("order/create")
    public Call<SanveResponse> orderTicket(@Body OrderTicketRequest request);

    @POST("order/payment")
    public Call paymennt(@Body PaymentRequest request);
}
