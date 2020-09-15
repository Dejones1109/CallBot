package com.its.sanve.api.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
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

    public ResponseEntity<Map<String, List>> getListPointCity(@RequestParam String startCity, @RequestParam String endCity, @RequestParam String routeID, @RequestParam String companyId) throws Exception {
        Map<String, RouteInfo> data = sanveClient.getCompaniesRoutes(companyId);
        return new ResponseEntity<>(callBotClient.listRoutes(data, startCity, endCity, routeID), HttpStatus.OK);
    }
    @PostMapping("getListPointCityByDB")

    public ResponseEntity<Map<String, List>> getListPointCityByDB(@RequestParam String startCity, @RequestParam String endCity, @RequestParam String routeID, @RequestParam String companyId) throws Exception {
        Map<String, RouteInfo> data = sanveClient.getCompaniesRoutes(companyId);
        return new ResponseEntity<>(callBotClient.listRoutes(data, startCity, endCity, routeID), HttpStatus.OK);
    }
    @PostMapping("getListStartTimer")
    public ResponseEntity<Object> getListStartTimerOfDay(@RequestParam String startCity, @RequestParam String endCity, @RequestParam String date, @RequestParam String companyId) throws Exception {

        int size = 0;
        int page = 0;
        String startTimeFrom = "";
        String endTimeFrom = "";
        Long requestAPI = System.currentTimeMillis();
        Map<String,List<Trip>> data = sanveClient.getTripByPoints(page, size, convertDateTime(date), startCity, endCity, startTimeFrom, endTimeFrom);
        Long responseAPI = System.currentTimeMillis();
        log.info("Response data by SanVe : {}",responseAPI-requestAPI);
        log.info("date:{},parseDate:{}",date,convertDateTime(date));
        return new ResponseEntity<>(callBotClient.listStartTimeReality(data, convertDateTime(date), companyId), HttpStatus.OK);
    }

    @GetMapping("getQualitiesTickets")
    public ResponseEntity<Map<String, Object>> getQualitiesTickets(@RequestParam String tripID, @RequestParam String pointUpID, @RequestParam String pointDownID,@RequestParam String startPoint,@RequestParam String endPoint,@RequestParam String date) throws Exception {
        log.info("tripId:{},pointUpId:{},pointDownId:{}", tripID, pointUpID, pointDownID);
        log.info("request API An Vui!,{}", sanveClient.getTripsTickets(tripID, pointUpID, pointDownID));
        Long requestAPI= System.currentTimeMillis();
        Map<String, List<Ticket>> tickets = sanveClient.getTripsTickets(tripID, pointUpID, pointDownID);
        log.info("Get qualities of Tickets,{}", tickets);
        int page = 0;
        int size = 0;
        String startTimeFrom="";
        String startTimeTo="";
        Map<String, List<Trip>> trips = sanveClient.getTripByPoints(page,size,convertDateTime(date),startPoint,endPoint,startTimeFrom,startTimeTo);
        Long responseAPI= System.currentTimeMillis();
        log.info("time of call API:{}",(responseAPI-requestAPI));
        return new ResponseEntity<>(callBotClient.QuantitiesTickets(tickets,trips,tripID), HttpStatus.OK);
    }

    @PostMapping(value = "creatTransactionLog",consumes = {"multipart/form-data"})
    public String creatTransactionLog(@RequestParam String hotLine, @RequestParam String callId, @RequestParam String intent, @RequestParam String phoneOrder, @RequestParam String phone, @RequestParam String pointUp, @RequestParam String pointDown, @RequestParam String startTimeReality, @RequestParam String startDate, @RequestParam String route, @RequestParam Integer status) throws ParseException {

        TransactionLog transactionLog = new TransactionLog();
        transactionLog.setPhone(phone);
        transactionLog.setIntent(intent);
        transactionLog.setRoute(route);
        transactionLog.setCreatedAt(LocalDateTime.now());
        transactionLog.setHotLine(hotLine);
        transactionLog.setStartDate(convertDateTime(startDate));
        transactionLog.setCallId(callId);
        transactionLog.setPointDown(pointDown);
        transactionLog.setPointUp(pointUp);
        transactionLog.setPhoneOrder(phoneOrder);
        transactionLog.setStartTimeReality(startTimeReality);
        transactionLog.setStatus(status);
        transactionLogRepository.save(transactionLog);
       //  sanveClient.createOrderTicket()
        return "saveToTransaction!!";
    }

    @PostMapping("createOrderTicket")
    public ResponseEntity createOrderTicket(@RequestBody OrderTicketRequest request){
        return new ResponseEntity("ok", HttpStatus.OK);
    }

        @GetMapping("getCompanyInfo")
    public ResponseEntity<Object> getInfoCompany(@RequestParam String phone) throws Exception {
        return new ResponseEntity<>(getDataFacade.getCompanyInfo(phone), HttpStatus.OK);
    }
    @GetMapping("getListRouteByDB")
    public ResponseEntity<Object> getListRouteByDb(@RequestParam int limit,@RequestParam String companyId)  {
        return new ResponseEntity<>(getDataFacade.getListAllRoute(limit,companyId),HttpStatus.OK);
    }
    private String convertDateTime(String date) throws ParseException {
        Date initDate = new SimpleDateFormat("dd/MM/yyyy").parse(date);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        String parsedDate = formatter.format(initDate);
        System.out.println(parsedDate);
        return parsedDate;
    }

}
