package com.its.sanve.api.controller;

import com.its.sanve.api.communication.callbot.CallBotClient;
import com.its.sanve.api.communication.sanve.SanVeClient;

import com.its.sanve.api.communication.dto.OrderTicketRequest;
import com.its.sanve.api.communication.sanve.SanVeClientV1;
import com.its.sanve.api.dto.TransactionRequest;
import com.its.sanve.api.entities.*;
import com.its.sanve.api.facede.CallBotFacade;
import com.its.sanve.api.facede.GetDataFacade;
import com.its.sanve.api.repositories.RouteInfoRepository;
import com.its.sanve.api.repositories.TransactionLogRepository;


import lombok.extern.log4j.Log4j2;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@Log4j2
@RequestMapping("/callBot")
public class CallBotController {

    @Autowired
    SanVeClient sanveClient;

    @Autowired
    CallBotClient callBotClient;
    @Autowired
    SanVeClientV1 sanVeClientV1;

    @Autowired
    CallBotFacade callBotFacade;

    @Autowired
    GetDataFacade getDataFacade;

    @GetMapping("isCheckRoute")
    public Object isCheckRoute(@RequestParam String startCity, @RequestParam String endCity, @RequestParam String companyId) {
        return callBotClient.isCheckRoute(sanveClient.getCompaniesRoutes(companyId), startCity, endCity);
    }

    @PostMapping("getListPointCity")

    public ResponseEntity<Map<String, List>> getListPointCity(@RequestParam String startCity, @RequestParam String endCity, @RequestParam String routeId, @RequestParam String companyId) throws Exception {
        Map<String, RouteInfo> data = sanveClient.getCompaniesRoutes(companyId);
        return new ResponseEntity<>(callBotClient.listRoutes(data, startCity, endCity, routeId), HttpStatus.OK);
    }




    //    @PostMapping("getListStartTimer")
//    public ResponseEntity<Object> getListStartTimerOfDay(@RequestParam String startCity, @RequestParam String endCity, @RequestParam String date, @RequestParam String companyId) throws Exception {
//
//        int size = 0;
//        int page = 0;
//        String startTimeFrom = "";
//        String endTimeFrom = "";
//        Long requestAPI = System.currentTimeMillis();
//        Map<String, List<Trip>> data = sanveClient.getTripByPoints(page, size, convertDateTime(date), startCity, endCity, startTimeFrom, endTimeFrom);
//        Long responseAPI = System.currentTimeMillis();
//        log.info("Response data by SanVe : {}", responseAPI - requestAPI);
//        log.info("date:{},parseDate:{}", date, convertDateTime(date));
//        return new ResponseEntity<>(callBotClient.listStartTimeReality(data, convertDateTime(date), companyId), HttpStatus.OK);
//    }
    @PostMapping("getListStartTimer")
    public ResponseEntity<Object> getListStartTimerOfDay(@RequestParam String pointUp, @RequestParam String pointDown, @RequestParam String date, @RequestParam int numberTicket, @RequestParam String companyId, @RequestParam String startTime) throws Exception {
        Long requestAPI = System.currentTimeMillis();
        List<Trip> listTrips = sanVeClientV1.getListTripByNumber(pointUp, pointDown, convertDateTime(date), companyId, numberTicket);
        Long responseAPI = System.currentTimeMillis();
        log.info("Response data by SanVe : {}", responseAPI - requestAPI);
        return new ResponseEntity<>(callBotClient.listStartTimeRealityV1(listTrips, convertDateTime(date), convertStartTime(startTime),numberTicket), HttpStatus.OK);
    }

    @GetMapping("getQualitiesTickets")
    public ResponseEntity<Map<String, Object>> getQualitiesTickets(@RequestParam String tripID, @RequestParam String pointUpID, @RequestParam String pointDownID, @RequestParam String startPoint, @RequestParam String endPoint, @RequestParam String date) throws Exception {
        log.info("tripId:{},pointUpId:{},pointDownId:{}", tripID, pointUpID, pointDownID);
        log.info("request API An Vui!,{}", sanveClient.getTripsTickets(tripID, pointUpID, pointDownID));
        Long requestAPI = System.currentTimeMillis();
        Map<String, List<Ticket>> tickets = sanveClient.getTripsTickets(tripID, pointUpID, pointDownID);
        log.info("Get qualities of Tickets,{}", tickets);
        int page = 0;
        int size = 0;
        String startTimeFrom = "";
        String startTimeTo = "";
        Map<String, List<Trip>> trips = sanveClient.getTripByPoints(page, size, convertDateTime(date), startPoint, endPoint, startTimeFrom, startTimeTo);
        Long responseAPI = System.currentTimeMillis();
        log.info("time of call API:{}", (responseAPI - requestAPI));
        return new ResponseEntity<>(callBotClient.QuantitiesTickets(tickets, trips, tripID), HttpStatus.OK);
    }

    @PostMapping("orderTicket")
    public ResponseEntity<String> orderTicket(@RequestParam String secretKey, @RequestParam String apiKey,
                                              @RequestBody TransactionRequest transaction) throws ParseException {

        if (callBotFacade.orderTicket(secretKey, apiKey, transaction) == true) {
            return new ResponseEntity<>("data successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("data false", HttpStatus.BAD_REQUEST);
        }

//		return (ResponseEntity<Object>) callBotFacade.orderTicket(secretKey, apiKey, transaction);

    }


    @GetMapping("getCompanyInfo")
    public ResponseEntity<Object> getInfoCompany(@RequestParam String phone) throws Exception {
        return new ResponseEntity<>(getDataFacade.getCompanyInfo(phone), HttpStatus.OK);
    }

    @GetMapping("getListRouteByDB")
    public ResponseEntity<Object> getListRouteByDb(@RequestParam int limit, @RequestParam String companyId) {
        return new ResponseEntity<>(getDataFacade.getListAllRoute(limit, companyId), HttpStatus.OK);
    }

    private String convertDateTime(String date) throws ParseException {
        Date initDate = new SimpleDateFormat("dd/MM/yyyy").parse(date);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        String parsedDate = formatter.format(initDate);
        System.out.println(parsedDate);
        return parsedDate;
    }

    private String convertStartTime(String startTime) {
        int time = 0;
        String[] number = startTime.split("h");
        time = Integer.parseInt(number[0]) * 3600000 + Integer.parseInt(number[1]) * 60000;
        return String.valueOf(time);
    }
}
