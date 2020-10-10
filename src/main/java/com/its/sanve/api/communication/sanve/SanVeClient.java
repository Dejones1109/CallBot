package com.its.sanve.api.communication.sanve;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.its.sanve.api.communication.AbstractCommunication;
import com.its.sanve.api.communication.SanVeCommunicate;

import com.its.sanve.api.communication.dto.CalculatePriceRequest;
import com.its.sanve.api.communication.dto.CalculatePriceResponse;
import com.its.sanve.api.communication.dto.OrderTicketRequest;
import com.its.sanve.api.dto.TransactionRequest;
import com.its.sanve.api.entities.*;
import com.its.sanve.api.utils.MessageUtils;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;



import javax.annotation.PostConstruct;
import java.io.IOException;

import java.net.URLDecoder;

import java.nio.charset.StandardCharsets;

import java.util.*;

@Log4j2
@Component
@NoArgsConstructor
@AllArgsConstructor

public class SanVeClient extends AbstractCommunication {


    @Value(value = "${SV.apiKey}")
    String apiKey;
    @Value(value = "${SV.secretKey}")
    String secretKey;
    @Value("${sanve.endpoint}")
    private String baseUrl;

    @Autowired
    MessageUtils messageUtils;

    private SanVeCommunicate sanVeCommunicate;


    @PostConstruct
    public void intConnection() {
        this.sanVeCommunicate = buildSetting();
    }

    private SanVeCommunicate buildSetting() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(JacksonConverterFactory.create())
                .client(this.buildCommunication())
                .build();

        return retrofit.create(SanVeCommunicate.class);
    }


    public List<Province> getProvinceDistrict() {

        try {
            Call<SanVeResponse<List<Province>>> request = sanVeCommunicate.getProvinceDistrict(apiKey, secretKey);
            log.debug("request:,{}", request);

            Response<SanVeResponse<List<Province>>> response = request.execute();
            log.debug("response: ,{}", response);


            if (response.isSuccessful()) {
                log.info("response successfully!!!");
                SanVeResponse<List<Province>> data = response.body();
                log.info("Successfully!!! {}", data.getData());

                return data.getData();

            } else {
                log.warn("response failed!!!");
                return null;
            }

        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return null;
        }


    }

    public Map<String, CompanyInfo> getCompanies() {
        try {
            Call<SanVeResponse<Map<String, CompanyInfo>>> request = sanVeCommunicate.getCompanies(apiKey.trim(), secretKey.trim());
            log.debug("request,{}", request);
            Response<SanVeResponse<Map<String, CompanyInfo>>> response = request.execute();
            log.debug("response,{}", request);
            // log.info("re");
            if (response.isSuccessful()) {
                log.info("response successfully!!!");
                SanVeResponse<Map<String, CompanyInfo>> data = response.body();
                log.info("Successfully!!! {}", data.getData());
                return data.getData();

            } else {
                log.warn("response failed!!!");
                return null;
            }

        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return null;
        }


    }

    public Map<String, RouteInfo> getCompaniesRoutes(String companyId) {
        log.debug("request get ");

        try {
            Call<SanVeResponse<Map<String, RouteInfo>>> request = sanVeCommunicate.getCompanyRoute(apiKey.trim(), secretKey.trim(), companyId.trim());
            log.debug("request,{}", request);
            Response<SanVeResponse<Map<String, RouteInfo>>> response = request.execute();
            log.debug("response,{}", response);

            if (response.isSuccessful()) {
                log.info("response successfully");
                SanVeResponse<Map<String, RouteInfo>> data = response.body();
                log.info("Successfully!!! {}", data.getData());
                String startCity = "Hà Nội";
                String endCity ="Nam Định";
               Map<String,RouteInfo> routeInfoMap = data.getData();
               log.info("keySet:{}",routeInfoMap.keySet());

                return data.getData();
            } else {
                log.warn("response failed!!!");
                return null;
            }

        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return null;
        }
    }

    public Map<String, List<Trip>> getTripByPoints(int page, int size, String date, String startPoint, String endPoint, String startTimeFrom, String startTimeTo) throws Exception {

        String start = URLDecoder.decode(startPoint, StandardCharsets.UTF_8.toString());

        String end = URLDecoder.decode(endPoint, StandardCharsets.UTF_8.toString());
        log.info("convert to UTF-8 : startPoint,{},endPoint,{}",start,end);
        try {
            Call<SanVeResponse<Map<String, List<Trip>>>> request = sanVeCommunicate.getTripsByPoint(apiKey.trim(), secretKey.trim(), page, size, date.trim(), start, end, startTimeFrom, startTimeTo);
            log.debug("request,{}", request);
            Response<SanVeResponse<Map<String, List<Trip>>>> response = request.execute();
            log.debug("response,{}", response);
            if (response.isSuccessful()) {
                log.info("response successfully");

                SanVeResponse<Map<String, List<Trip>>> data = response.body();
                log.info("Successfully!!! {}", data.getData());
                Map<String, List<Trip>> mapTrip = data.getData();
                log.info("mapTrip:{}",mapTrip);

                return data.getData();
            } else {
                log.warn("response failed!!!");
                return null;
            }
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return null;
        }
    }

//    public Object getTripsbyRouteId(int page, int size, String date, String startTimeFrom,
//                                    String startTimeTo, String RouteId) throws Exception {
//
//        SanveResponse data = null;
//        Call<SanveResponse> request = null;
//        try {
//            log.info("request");
//            request = sanVeCommunicate.getTripsByRouteId(apiKey.trim(), SecretKey.trim(), size, page, date.trim(), startTimeFrom.trim(), startTimeTo.trim(), RouteId.trim());
//
//            log.info("2");
//            Response<SanveResponse> response = request.execute();
//            log.info("responce");
//            if (response.isSuccessful()) {
//                log.info("4");
//                data = response.body();
//                log.info("data trả về thành công");
//
//
//            } else {
//                log.info("failed");
//            }
//
//        } catch (Exception e) {
//            log.info(e.getMessage());
//        }
//
//        return data;
//    }

    public Map<String, List<Ticket>> getTripsTickets(String tripId, String pointUpId,
                                                     String pointDownId) {


        try {

            Call<SanVeResponse<Map<String, List<Ticket>>>> request = sanVeCommunicate.getTickets(apiKey, secretKey, tripId, pointUpId, pointDownId);
            log.debug("request,{}", request);
            Response<SanVeResponse<Map<String, List<Ticket>>>> response = request.execute();
            log.debug("response,{}", response);

            if (response.isSuccessful()) {
                log.info("response successfully");
                SanVeResponse<Map<String, List<Ticket>>> data = response.body();
                log.info("Successfully!!! {}", data.getData());

                Map<String, List<Ticket>> tickets = data.getData();
                log.info("data successful!");
                List<Ticket> listTickets = tickets.get("tickets");

                int count = 0;
                double temp =0;
                for (Ticket ticket : listTickets) {

                    if (ticket.getStatus() != null) {
                        count++;
                        Double code = ticket.getOriginalTicketPrice();
                        if (temp == 0) {
                            temp = code;
                        } else if (code < temp) {
                            temp = code;
                        }
                    }
                }
                log.info("the number of seats available,{}",count);
                log.info("the qualities of ticket,{}",temp);
                return data.getData();
            } else {
                log.warn("response failed!!!");
                return null;
            }

        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return null;
        }


    }
    public Object creatOrder(String apiKey,String secretKey,Object seatSelected,Object pointSelected,String routeId,String tripId,String fullName,String phone,String companyId){
        SanVeResponse results = null;
        log.info("1");
        Call<SanVeResponse> requests = sanVeCommunicate.orderTicket1(apiKey,secretKey,seatSelected,pointSelected,routeId,tripId,fullName,phone,companyId);
        log.info("2");
        Response<SanVeResponse> response = requests.execute();
        log.info(response);
        log.info("3");
        if (response.isSuccessful()) {
            log.info("4");
            results = response.body();
            log.info(results);
            log.info("5");
        }
        return results;
    }

    public CalculatePriceResponse calculatePrice(CalculatePriceRequest request) throws Exception {
        CalculatePriceResponse results = null;
        log.info("1");
        Call<CalculatePriceResponse> requests = sanVeCommunicate.calculatePrice(request);
        log.info("2");
        Response<CalculatePriceResponse> response = requests.execute();
        log.info(response);
        log.info("3");
        if (response.isSuccessful()) {
            log.info("4");
            results = response.body();
            log.info("5");
        }
        return results;
    }


}




