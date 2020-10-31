package com.its.sanve.api.controller;

import com.its.sanve.api.communication.callbot.CallBotClient;
import com.its.sanve.api.communication.sanve.SanVeClient;

import com.its.sanve.api.communication.sanve.SanVeClientV1;
import com.its.sanve.api.dto.TransactionRequest;
import com.its.sanve.api.entities.*;
import com.its.sanve.api.facede.CallBotFacade;
import com.its.sanve.api.facede.GetDataFacade;


import lombok.extern.log4j.Log4j2;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    @PostMapping("getListStartTimer")
    public ResponseEntity<Object> getListStartTimerOfDay(@RequestParam String pointUp, @RequestParam String pointDown, @RequestParam String date, @RequestParam int numberTicket, @RequestParam String companyId, @RequestParam String startTime) throws Exception {
        Long requestAPI = System.currentTimeMillis();
        List<Trip> listTrips = sanVeClientV1.getListTripByNumber(pointUp, pointDown, convertDateTime(date), companyId, numberTicket);
        Long responseAPI = System.currentTimeMillis();
        log.info("Response data by SanVe : {}", responseAPI - requestAPI);
        return new ResponseEntity<>(callBotClient.listStartTimeRealityV1(listTrips, convertDateTime(date), convertStartTime(startTime), numberTicket), HttpStatus.OK);
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


    @GetMapping("getCompanyInfo")
    public ResponseEntity<Object> getInfoCompany(@RequestParam String phone) throws Exception {
        return new ResponseEntity<>(getDataFacade.getCompanyInfo(phone), HttpStatus.OK);
    }

    @GetMapping("getListRouteByDB")
    public ResponseEntity<Object> getListRouteByDb(@RequestParam int limit, @RequestParam String companyId) {
        return new ResponseEntity<>(getDataFacade.getListAllRoute(limit, companyId), HttpStatus.OK);
    }

//    @PostMapping("orderTicket")
//    public ResponseEntity<Map<String, String>> orderTicket(@RequestParam String secretKey, @RequestParam String apiKey,
//                                                           @RequestBody TransactionRequest transaction) throws ParseException {
//      //  TransactionRequest request = new TransactionRequest();
//
//        Map<String, String> orderTicket = new HashMap<>();
//        if (callBotFacade.orderTicket(secretKey, apiKey, transaction) == true) {
//            orderTicket.put("result", "true");
//            return new ResponseEntity<>(orderTicket, HttpStatus.OK);
//        } else {
//            orderTicket.put("result", "false");
//            return new ResponseEntity<>(orderTicket, HttpStatus.BAD_REQUEST);
//        }
//    }
        @PostMapping("orderTicket")
    public ResponseEntity<Map<String, String>> orderTicket(@RequestParam String secretKey, @RequestParam String apiKey,
                                                           @RequestParam List<String> seatSelected, @RequestParam String pointSelected, @RequestParam String routeId, @RequestParam String tripId, @RequestParam String phone,@RequestParam String companyId,@RequestParam String phoneOrder) throws ParseException {
        Map<String, String> orderTicket = new HashMap<>();
        TransactionRequest transaction = new TransactionRequest();
        transaction.setSeatId(seatSelected);
        transaction.setPointSelected(pointSelected);
        transaction.setRoute(routeId);
        transaction.setTripId(tripId);
        transaction.setPhone(phone);
        transaction.setFull_name("Khách đặt từ điện thoại");
        transaction.setCompanyId(companyId);
        transaction.setPhoneOrder(phoneOrder);

        log.info(transaction);
        if (callBotFacade.orderTicket(secretKey, apiKey, transaction) == true) {
            orderTicket.put("result", "true");
            return new ResponseEntity<>(orderTicket, HttpStatus.OK);
        } else {
            orderTicket.put("result", "false");
            return new ResponseEntity<>(orderTicket, HttpStatus.BAD_REQUEST);
        }
    }



//    @PostMapping("ringStore")
//    public String getCommandApi(@RequestParam int status, @RequestParam String callId, @RequestParam String text, @RequestParam String voice,@RequestParam int id) throws Exception{
//        String result = null;
//        if (status == 1) {
//            SocketIO clientSocket = new SocketIO();
//            clientSocket.startConnection("123.31.17.59", 7088);
//            String msg = "{\"jsonrpc\": \"2.0\", \"method\": \"CallBot.sendCommand\", \"params\": {\"token\": \"Marvel_20$20@##\", \"call_id\": \"" + callId + "\", \"text_command\": \"" + text + "\", \"voice\": \"" + voice + "\" , \"timeout\": 15, \"type\": 202 }, \"id\": "+id+"}";
//            result = clientSocket.sendMessage(msg.length() + ":" + msg + ",");
//            clientSocket.stopConnection();
//        }else{
//            result = "action_after_wait";
//        }
//        return result;
//    }
//    @PostMapping(path = "/file-upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity<String> fileUpload(
//            @RequestParam(name = "metadata", required = false) List<com.its.sanve.api.communication.dto.OrderTicketRequest> requests, @RequestParam(name = "data", required = false) String data
//           ) {
//        log.info(requests);
//        log.info(data);
//      //  System.out.println(file);
//        return new ResponseEntity<>("example", HttpStatus.OK);
//    }
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
