package com.its.sanve.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.its.sanve.api.communication.CallBot.CallBotClient;
import com.its.sanve.api.communication.SanVe.SanveClient;

import com.its.sanve.api.communication.dto.OrderTicketRequest;
import com.its.sanve.api.entities.RouteInfo;
import com.its.sanve.api.entities.TransactionLog;
import com.its.sanve.api.repositories.RouteInfoRepository;
import com.its.sanve.api.repositories.TransactionLogRepository;
import com.its.sanve.api.utils.RandomString;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.log4j.Log4j2;
import okhttp3.MultipartBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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
    CallBotClient callBotClient;
    @Autowired
    RandomString randomString;
    @Autowired
    TransactionLogRepository transactionLogRepository;

    @Autowired
    RouteInfoRepository routeInfoRepository;
    @PostMapping("StartEndCity")
    @ApiOperation(value = "StartEndCity")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Created face register successfully", response = SanveClient.class),
            @ApiResponse(code = 400, message = "Bad Request", response = SanveClient.class),
            @ApiResponse(code = 500, message = "Failure", response = SanveClient.class)})
    public ResponseEntity<Object> getStartEndCity(@RequestParam String startCity, @RequestParam String endCity, @RequestParam String date) throws Exception {
        Map<String, Object> p = new HashMap<>();

//        String companiesId = "TC01gWSmr8A9Qx";
        String routeName = startCity + " - " + endCity;
        int size = 0;
        int page = 0;
        String startTimeFrom = "";
        String endTimeFrom = "";
        log.info("Request chuyến ");
        Long time1 = System.currentTimeMillis();
        List<String>  data1 = new ArrayList<>();
        data1.addAll(routeInfoRepository.searchRouteName(routeName));
        //routeInfoRepository.searchRouteName(routeName);
        
        log.info(data1);
        Boolean check = callBotClient.checkRouteName(data1);
        log.info("trả về có chuyến hay không?");
        log.info(check);
        if (check == true) {
            log.info("có tuyến rồi check  có chuyến");
            Object data = sanveClient.getTripsbyPoints(page, size, date, startCity, endCity, startTimeFrom, endTimeFrom);
            String string1 = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(data);
            Object list = callBotClient.listStartTimeReality(string1, date);
            if (list != null) {
                p.put("valid", 1);
            } else {
                p.put("valid", 2);
            }
        } else {

            p.put("valid", 0);
        }
        Long time2 = System.currentTimeMillis();
        log.info("thành công");
        log.info(time2 - time1);


        return new ResponseEntity<>(p, HttpStatus.OK);
    }

    @PostMapping("getListPointCity")

    public ResponseEntity<Object> getListPointCity(@RequestParam String startCity, @RequestParam String endCity, @RequestParam String routeID) throws Exception {
        Map<String, Object> p = new HashMap<>();

        String route_name = startCity + " - " + endCity;
        String CompanyId = "TC01gWSmr8A9Qx";
        long time1 = System.currentTimeMillis();
        Object data = sanveClient.getCompaniesRoutes(CompanyId);
        long time3 = System.currentTimeMillis();
        String string1 = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(data);
        // log.info(string1);
        p = (Map<String, Object>) callBotClient.listRoutes(string1, startCity, endCity, route_name, routeID);


        long time2 = System.currentTimeMillis();
        ;
        log.info(time2 - time3);
        return new ResponseEntity<>(p, HttpStatus.OK);
    }

    @PostMapping("getListStartTimer")
    public ResponseEntity<Object> getListStartTimerOrDay(@RequestParam String startCity, @RequestParam String endCity, @RequestParam String date) throws Exception {
        Map<String, Object> p = new HashMap<>();
        int size = 0;
        int page = 0;
        String startTimeFrom = "";
        String endTimeFrom = "";
        Long time1 = System.currentTimeMillis();
        Object data = sanveClient.getTripsbyPoints(page, size, date, startCity, endCity, startTimeFrom, endTimeFrom);
        String string1 = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(data);
        p = (Map<String, Object>) callBotClient.listStartTimeReality(string1, date);

        Long time2 = System.currentTimeMillis();
        log.info(time2 - time1);
        return new ResponseEntity<>(p, HttpStatus.OK);
    }

    @PostMapping("getQuanitiesTickets")
    private ResponseEntity<Object> getQuanitiesTickets(@RequestParam String tripID, @RequestParam String pointUpID, @RequestParam String pointDownID) throws Exception {
        Map<String, Object> p;
        Long time = System.currentTimeMillis();

        Object data = sanveClient.getTripsTickets(tripID, pointUpID, pointDownID);
        String string = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(data);
        p = (Map<String, Object>) callBotClient.QuantitiesTickets(string);
        Long time1 = System.currentTimeMillis();
        log.info(time1 - time);
        log.info(p);

        return new ResponseEntity<>(p, HttpStatus.OK);
    }

    @PostMapping("creatTransantionLog")
    public String creatTransantionLog(@RequestParam String Hotline, @RequestParam String Call_Id, @RequestParam String Intent, @RequestParam String PhoneOrder, @RequestParam String Phone, @RequestParam String PointUp, @RequestParam String PointDown, @RequestParam String StartTimeReality, @RequestParam Date StartDate, @RequestParam String Route, @RequestParam Integer Status) {
        randomString = new RandomString();
        LocalDateTime now = LocalDateTime.now();
        TransactionLog transactionLog = new TransactionLog();
        // transactionLog.setId(randomString.randomAlphaNumeric());
        transactionLog.setPhone(Phone);
        transactionLog.setHotline(Hotline);
        transactionLog.setCall_ID(Call_Id);
        transactionLog.setIntent(Intent);
        transactionLog.setPhone_order(PhoneOrder);
        transactionLog.setPoint_up(PointUp);
        transactionLog.setPoint_down(PointDown);
        transactionLog.setStart_time_reality(StartTimeReality);
        transactionLog.setStart_date(StartDate);
        transactionLog.setCreated_at(now);
        transactionLog.setRoute(Route);
        transactionLog.setStatus(Status);
        transactionLogRepository.save(transactionLog);

        return "" + HttpStatus.OK;
    }

    @PostMapping("createOrderTicket")
    public ResponseEntity createOrderTicket(@RequestBody OrderTicketRequest request) throws IOException {
//
        return new ResponseEntity("ok", HttpStatus.OK);
    }

}
