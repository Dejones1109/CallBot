package com.its.sanve.api.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.its.sanve.api.communication.callbot.CallBotClient;
import com.its.sanve.api.communication.sanve.SanVeClient;

import com.its.sanve.api.communication.dto.OrderTicketRequest;
import com.its.sanve.api.entities.RouteInfo;
import com.its.sanve.api.entities.Ticket;
import com.its.sanve.api.entities.TransactionLog;
import com.its.sanve.api.entities.Trip;
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
    ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    SanVeClient sanveClient;

    @Autowired
    CallBotClient callBotClient;

    @Autowired
    TransactionLogRepository transactionLogRepository;

    @Autowired
    RouteInfoRepository routeInfoRepository;

    @Autowired
    GetDataFacade getDataFacade;

    @PostMapping("getListPointCity")

    public ResponseEntity<Object> getListPointCity(@RequestParam String startCity, @RequestParam String endCity, @RequestParam String routeID, @RequestParam String companyId) throws Exception {


        String route_name = startCity + " - " + endCity;
        Map<String, RouteInfo> data = sanveClient.getCompaniesRoutes(companyId);
        Map<String, List> listRoutes =  callBotClient.listRoutes(data, startCity, endCity, route_name, routeID);
        return new ResponseEntity<>(listRoutes, HttpStatus.OK);
    }

    @PostMapping("getListStartTimer")
    public ResponseEntity<Object> getListStartTimerOfDay(@RequestParam String startCity, @RequestParam String endCity, @RequestParam String date, @RequestParam String companyId) throws Exception {

        int size = 0;
        int page = 0;
        String startTimeFrom = "";
        String endTimeFrom = "";
        Map<String,List<Trip>> data = sanveClient.getTripByPoints(page, size, date, startCity, endCity, startTimeFrom, endTimeFrom);
        Map<String,Object> p =  callBotClient.listStartTimeReality(data, date, companyId);
        return new ResponseEntity<>(p, HttpStatus.OK);
    }

    @GetMapping("getQualitiesTickets")
    public ResponseEntity<Map<String, String>> getQualitiesTickets(@RequestParam String tripID, @RequestParam String pointUpID, @RequestParam String pointDownID) {
        Map<String, String> qualitiesTickets = new HashMap<>();
        Map<String, List<Ticket>> data;

        try {
            log.info("tripId:{},pointUpId:{},pointDownId:{}", tripID, pointUpID, pointDownID);
            log.info("request API An Vui!,{}", sanveClient.getTripsTickets(tripID, pointUpID, pointDownID));
            data = sanveClient.getTripsTickets(tripID, pointUpID, pointDownID);
            log.info("Get qualities of Tickets,{}", data);
            qualitiesTickets = callBotClient.QuantitiesTickets(data);
        } catch (Exception ex) {
            log.info(ex.getMessage());
        }
        return new ResponseEntity<>(qualitiesTickets, HttpStatus.OK);
    }


    @PostMapping("creatTransactionLog")
    public String creatTransactionLog(@RequestParam String hotLine, @RequestParam String callId, @RequestParam String intent, @RequestParam String phoneOrder, @RequestParam String phone, @RequestParam String pointUp, @RequestParam String pointDown, @RequestParam String startTimeReality, @RequestParam String startDate, @RequestParam String route, @RequestParam Integer status) throws ParseException {

        TransactionLog transactionLog = new TransactionLog(phone,phoneOrder,callId,hotLine,intent,pointUp,pointDown,startTimeReality,convertDateTime(startDate),LocalDateTime.now(),route,status);
        transactionLogRepository.save(transactionLog);
        return "saveToTransaction!!";
    }

    @PostMapping("createOrderTicket")
    public ResponseEntity createOrderTicket(@RequestBody OrderTicketRequest request){
//
        return new ResponseEntity("ok", HttpStatus.OK);
    }

    @GetMapping("getCompanyInfo")
    public ResponseEntity<Object> getInfoCompany(@RequestParam String phone) throws Exception {

        return new ResponseEntity<>(getDataFacade.getCompanyInfo(phone), HttpStatus.OK);
    }

    private String convertDateTime(String date) throws ParseException {
        Date initDate = new SimpleDateFormat("dd/MM/yyyy").parse(date);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String parsedDate = formatter.format(initDate);
        System.out.println(parsedDate);
        return parsedDate;
    }
}
