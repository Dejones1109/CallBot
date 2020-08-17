package com.its.sanve.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.its.sanve.api.communication.SanVe.SanveClient;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@Log4j2
@RestController
@RequestMapping(value = "/callbot")
public class CallBotController {
    @Autowired
    ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    SanveClient sanveClient;


    @PostMapping("StartEndCity")
    @ApiOperation(value = "StartEndCity")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Created face register successfully", response = SanveClient.class),
            @ApiResponse(code = 400, message = "Bad Request", response = SanveClient.class),
            @ApiResponse(code = 500, message = "Failure", response = SanveClient.class)})
    public ResponseEntity<Object> getStartEndCity(@RequestParam String startCity, @RequestParam String endCity,@RequestParam String date) throws Exception {
        Map<String, Object> p = new HashMap<>();

        String companiesId = "TC01gWSmr8A9Qx";
        String routeName = startCity+" - "+endCity;
        int size = 0;
        int page = 0;
        String startTimeFrom = "";
        String endTimeFrom = "";
        log.info("Request chuyến ");
        Long time1 = System.currentTimeMillis();
       Object   data1 = sanveClient.getCompaniesRoutes(companiesId);
       Integer check = sanveClient.isCheckCity(data1.toString(),routeName);
        log.info("trả về có chuyến hay không?");
        log.info(check);
        if(check==1){
         log.info("có tuyến rồi check  có chuyến");
            Object data = sanveClient.getTripsbyPoints(page,size,date,startCity,endCity,startTimeFrom,endTimeFrom);
            String string1 = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(data);
            Object list = sanveClient.listStartTimeReality(string1,date);
            if (list!=null){
                p.put("valid",1);
            }else {
                p.put("valid",2);
            }
        }else {

            p.put("valid",0);
        }
        Long time2 = System.currentTimeMillis();
        log.info("thành công");
        log.info(time2-time1);


        return new ResponseEntity<>(p,HttpStatus.OK);
    }

    @PostMapping("getListPointCity")

    public ResponseEntity<Object> getListPointCity(@RequestParam String startCity, @RequestParam String endCity,@RequestParam String routeID) throws Exception {
        Map<String, Object> p = new HashMap<>();

        String route_name = startCity + " - " + endCity;
        String CompanyId = "TC01gWSmr8A9Qx";
        long time1 = System.currentTimeMillis();
        Object data = sanveClient.getCompaniesRoutes(CompanyId);
        long time3 = System.currentTimeMillis();
        String string1 = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(data);
        log.info(string1);
        p.put("listPointUp", sanveClient.listRoutes(string1, startCity, route_name,routeID));
        p.put("listPointDown", sanveClient.listRoutes(string1, endCity, route_name,routeID));
        long time2 = System.currentTimeMillis();
        ;
        log.info(time2 - time3);
        return new ResponseEntity<>(p, HttpStatus.OK);
    }

    @PostMapping("getListStartTimer")
    public ResponseEntity<Object> getListStartTimerOrDay(@RequestParam String startCity, @RequestParam String endCity, @RequestParam String date) throws Exception {
        Map<String, Object> p=new HashMap<>();
        int size = 0;
        int page = 0;
        String startTimeFrom = "";
        String endTimeFrom = "";
        Long time1 = System.currentTimeMillis();
        Object data = sanveClient.getTripsbyPoints(page,size,date,startCity,endCity,startTimeFrom,endTimeFrom);
        String string1 = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(data);
        p= (Map<String, Object>) sanveClient.listStartTimeReality(string1,date);

        Long time2 = System.currentTimeMillis();
        log.info(time2-time1);
        return new ResponseEntity<>(p,HttpStatus.OK);
    }
    @PostMapping("getQuanitiesTickets")
    private  ResponseEntity<Object> getQuanitiesTickets(@RequestParam String tripID,@RequestParam String pointUpID,@RequestParam String pointDownID) throws Exception {
        Map<String,Object> p = new HashMap<>();
        Long time = System.currentTimeMillis();

        Object data = sanveClient.getTripsTickets(tripID,pointUpID,pointDownID);
        String string = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(data);
        p = (Map<String, Object>) sanveClient.QuantitiesTickets(string);
        Long time1 = System.currentTimeMillis();
        log.info(time1-time);
         log.info(p);
       // p.put("1",map.setK);
//        p.put("key",map.equals("temp"));
//        p.put("ticket_qtt",map.equals("count"));
        return new ResponseEntity<>(p,HttpStatus.OK);
    }


}
