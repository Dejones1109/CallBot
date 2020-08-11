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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public ResponseEntity<Object> getStartEndCity(@RequestParam String startCity, @RequestParam String endCity) throws Exception {
        Map<String, Object> p = new HashMap<>();
        String route_name = startCity + " - " + endCity;
        String CompanyId = "TC01gWSmr8A9Qx";
        long time1 = System.currentTimeMillis();
        Object data = sanveClient.getCompaniesRoutes(CompanyId);
        log.info(data.toString());
        p.put("1", sanveClient.isCheckCity(data.toString(), route_name));
        long time2 = System.currentTimeMillis();
        log.info(p);
        log.info(time2 - time1);
        return new ResponseEntity<>(p.values(), HttpStatus.OK);
    }

    @PostMapping("getListPointCity")

    public ResponseEntity<Object> getListPointCity(@RequestParam String startCity, @RequestParam String endCity) throws Exception {
        Map<String, Object> p = new HashMap<>();

        String route_name = startCity + " - " + endCity;
        String CompanyId = "TC01gWSmr8A9Qx";
        long time1 = System.currentTimeMillis();
        Object data = sanveClient.getCompaniesRoutes(CompanyId);
        long time3 = System.currentTimeMillis();
        String string1 = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(data);
        log.info(string1);
        p.put("listPointUp", sanveClient.listRoutes(string1, startCity, route_name));
        p.put("listPointDown",sanveClient.listRoutes(string1, endCity, route_name));
        long time2 = System.currentTimeMillis();
        ;
        log.info(time2 - time3);
        return new ResponseEntity<>(p.values(), HttpStatus.OK);
    }

}
