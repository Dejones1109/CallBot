package com.its.sanve.api.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.its.sanve.api.communication.callbot.CallBotClient;
import com.its.sanve.api.communication.sanve.SanveClient;

import com.its.sanve.api.communication.dto.OrderTicketRequest;
import com.its.sanve.api.entities.CompanyInfo;
import com.its.sanve.api.entities.Ticket;
import com.its.sanve.api.entities.TransactionLog;
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

@Log4j2

@RestController
@RequestMapping(value = "/callbot")
public class CallBotController {
    @Autowired
    ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    SanveClient sanveClient;
    @Autowired
    SanveClient svClient;
    @Autowired
    CallBotClient callBotClient;

    @Autowired
    TransactionLogRepository transactionLogRepository;

    @Autowired
    RouteInfoRepository routeInfoRepository;
//    @Autowired
//    com.its.sanve.api.facede.GetDataFacade getDataFacade;

    @PostMapping("getListPointCity")

    public ResponseEntity<Object> getListPointCity(@RequestParam String startCity, @RequestParam String endCity, @RequestParam String routeID, @RequestParam String companyId) throws Exception {
        Map<String, Object> p = new HashMap<>();

        String route_name = startCity + " - " + endCity;
        long time1 = System.currentTimeMillis();
        Object data = sanveClient.getCompaniesRoutes(companyId);
        long time3 = System.currentTimeMillis();
        String string1 = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(data);
        // log.info(string1);
        p = (Map<String, Object>) callBotClient.listRoutes(string1, startCity, endCity, route_name, routeID);


        long time2 = System.currentTimeMillis();

        log.info(time2 - time1);
        return new ResponseEntity<>(p, HttpStatus.OK);
    }

    @PostMapping("getListStartTimer")
    public ResponseEntity<Object> getListStartTimerOrDay(@RequestParam String startCity, @RequestParam String endCity, @RequestParam String date, @RequestParam String companyId) throws Exception {
        Map<String, Object> p = new HashMap<>();
        int size = 0;
        int page = 0;

//        String routeName = startCity + " - " + endCity;
        String startTimeFrom = "";
        String endTimeFrom = "";
        Long time1 = System.currentTimeMillis();


        Object data = sanveClient.getTripByPoints(page, size, date, startCity, endCity, startTimeFrom, endTimeFrom);
        Long time2 = System.currentTimeMillis();
        log.info("GOi bên API An Vui tra ve du lieu------" + (time2 - time1));
        String string1 = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(data);
        p = (Map<String, Object>) callBotClient.listStartTimeReality(string1, date, companyId);
        Long time3 = System.currentTimeMillis();
        log.info("Api ben mk tra ra du lieu------" + (time3 - time2));
        return new ResponseEntity<>(p, HttpStatus.OK);
    }

    @PostMapping("getQualitiesTickets")
    private ResponseEntity<Map<String,String>> getQualitiesTickets(@RequestParam String tripID, @RequestParam String pointUpID, @RequestParam String pointDownID){
        Map<String, String> qualitiesTickets = new HashMap<>();
        Map<String, List<Ticket>> data;
        Map<String, CompanyInfo> m = svClient.getCompanies();
        if(m == null){
            log.info("data null!!");
        }
        log.info("initialization!!,{}", m);
           try{
               log.info("tripId:{},pointUpId:{},pointDownId:{}",tripID,pointUpID,pointDownID);
               log.info("request API An Vui!,{}",sanveClient.getTripsTickets(tripID, pointUpID, pointDownID));
               data = sanveClient.getTripsTickets(tripID, pointUpID, pointDownID);
               log.info("Get qualities of Tickets,{}",data);
               qualitiesTickets = callBotClient.QuantitiesTickets(data);
           }
           catch (Exception ex){
               log.info(ex.getMessage());
           }
        return new ResponseEntity<>(qualitiesTickets, HttpStatus.OK);
    }


    @PostMapping("creatTransactionLog")
    public String creatTransactionLog(@RequestParam String hotLine, @RequestParam String callId, @RequestParam String intent, @RequestParam String phoneOrder, @RequestParam String phone, @RequestParam String pointUp, @RequestParam String pointDown, @RequestParam String startTimeReality, @RequestParam String startDate, @RequestParam String route, @RequestParam Integer status) throws ParseException {

        LocalDateTime now = LocalDateTime.now();
        TransactionLog transactionLog = new TransactionLog();
        // transactionLog.setId(randomString.randomAlphaNumeric());
        transactionLog.setPhone(phone);
        transactionLog.setHotline(hotLine);
        transactionLog.setCall_ID(callId);
        transactionLog.setIntent(intent);
        transactionLog.setPhone_order(phoneOrder);
        transactionLog.setPoint_up(pointUp);
        transactionLog.setPoint_down(pointDown);
        transactionLog.setStart_time_reality(startTimeReality);
        transactionLog.setStart_date(convertDateTime(startDate));
        transactionLog.setCreated_at(now);
        transactionLog.setRoute(route);
        transactionLog.setStatus(status);
        transactionLogRepository.save(transactionLog);

        return "" + HttpStatus.OK;
    }

    @PostMapping("createOrderTicket")
    public ResponseEntity createOrderTicket(@RequestBody OrderTicketRequest request) throws IOException {
//
        return new ResponseEntity("ok", HttpStatus.OK);
    }

//    @GetMapping("getInfoCompany")
//    public ResponseEntity<Object> getInfoCompany(@RequestParam String phone) throws Exception {
//        Map<String, Object> map = (Map<String, Object>) getDataFacade.getInfoCompany(phone);
//        log.info(map);
//        return new ResponseEntity<Object>(map, HttpStatus.OK);
//    }

    private String convertDateTime(String items) throws ParseException {
        Date initDate = new SimpleDateFormat("dd/MM/yyyy").parse(items);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String parsedDate = formatter.format(initDate);
        System.out.println(parsedDate);

        return parsedDate;
    }
}
