package com.its.sanve.api.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.its.sanve.api.communication.callbot.CallBotClient;
import com.its.sanve.api.communication.sanve.SanveClient;


import com.its.sanve.api.communication.dto.CalculatePriceRequest;
import com.its.sanve.api.communication.dto.CalculatePriceResponse;
import com.its.sanve.api.dto.GwResponseDto;

import com.its.sanve.api.entities.*;
import com.its.sanve.api.utils.MessageUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.io.UnsupportedEncodingException;
import java.util.*;

@RestController
@Log4j2
public class SanVeController {

    private static final int PARAM_MISSING = 1;
    @Autowired
    SanveClient SVClient;
    @Autowired
    MessageUtils messageUtils;
    @Autowired
    CallBotClient callBotClient;
    @Autowired
    ObjectMapper objectMapper = new ObjectMapper();


    @RequestMapping(value = "point/get_province_district", method = RequestMethod.GET)
    public ResponseEntity<Object> getProvinceDistrict() {

        List<Province> provinceList = SVClient.getProvinceDistrict();

        log.info("data get successful!!");
        return new ResponseEntity<>(provinceList, HttpStatus.OK);
    }

    @GetMapping("/companies")
    public ResponseEntity<Object> get_companies() {

        Map<String, CompanyInfo> listCompany = SVClient.getCompanies();
        log.info("data get successful!!");
        return new ResponseEntity<>(listCompany, HttpStatus.OK);
    }

    @GetMapping("company/routes")
    public ResponseEntity<Object> get_company_router(@RequestParam String companyId) {
        Map<String, RouteInfo> listRouteInfo = SVClient.getCompaniesRoutes(companyId);
        log.info("data get successful!!");
        return new ResponseEntity<>(listRouteInfo, HttpStatus.OK);
    }

    @GetMapping("tripsPoint")

    public ResponseEntity<Object> getTripsPoint(@RequestParam int page, @RequestParam int size, @RequestParam String date, @RequestParam
            String startPoint, @RequestParam String endPoint, @RequestParam String startTimeFrom, @RequestParam String startTimeTo) throws Exception {
        Map<String, List<Trip>> listTrips = SVClient.getTripByPoints(page, size, date, startPoint, endPoint, startTimeFrom, startTimeTo);
        log.info("data get successful!!");
        return new ResponseEntity<>(listTrips, HttpStatus.OK);
    }

//    @GetMapping("tripsRoute")
//    //trips by Points
//    public ResponseEntity<Object> getTrips(@RequestParam int page, @RequestParam int size, @RequestParam String date,
//                                           @RequestParam String startTimeFrom, @RequestParam String startTimeTo, @RequestParam String routeId) throws Exception {
//
//        Map<String, Object> p = new HashMap<>();
//
//        p.put("1", SVClient.getTripsbyRouteId(page, size, date, startTimeFrom, startTimeTo, routeId));
//
//        return new ResponseEntity<>(p.values(), HttpStatus.OK);
//    }

    @GetMapping("trip/tickets")
    public ResponseEntity<Object> get_trip_tickets(@RequestParam String tripId, @RequestParam String pointUpId, @RequestParam String pointDownId) throws Exception {

        Map<String, List<Ticket>> tripsTickets = SVClient.getTripsTickets(tripId, pointUpId, pointDownId);
        log.info("data get successful!!");
        return new ResponseEntity<>(tripsTickets.values(), HttpStatus.OK);
    }

    @PostMapping("order/calc_price")
    public ResponseEntity get_order_calc_price(@RequestBody CalculatePriceRequest request) throws Exception {
        CalculatePriceResponse response = SVClient.calculatePrice(request);
        return GwResponseDto.build().withHttpStatus(HttpStatus.OK)
                .withCode(HttpStatus.OK)
                .withData(response)
                .withMessage("Success")
                .toResponseEntity();

    }

}



