package com.its.sanve.api.controller;


import com.its.sanve.api.communication.callbot.CallBotClient;
import com.its.sanve.api.communication.sanve.SanVeClient;


import com.its.sanve.api.communication.dto.CalculatePriceRequest;
import com.its.sanve.api.communication.dto.CalculatePriceResponse;
import com.its.sanve.api.communication.sanve.SanVeClientV1;
import com.its.sanve.api.dto.GwResponseDto;

import com.its.sanve.api.entities.*;
import com.its.sanve.api.facede.GetDataFacade;
import com.its.sanve.api.utils.MessageUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.*;

@RestController
@Log4j2
public class SanVeController {


    @Autowired
    SanVeClient SVClient;
    @Autowired
    MessageUtils messageUtils;
    @Autowired
    CallBotClient callBotClient;
    @Autowired
    SanVeClientV1 svClient;
    @Autowired
    GetDataFacade getDataFacade;

    @RequestMapping(value = "saveDbProvinceDistrict", method = RequestMethod.GET)
    public String saveProvinceDistrict() {
        return getDataFacade.getDataProvinceDistrict();
    }
    @RequestMapping(value = "point/get_province_district", method = RequestMethod.GET)
    public ResponseEntity<List<Province>> getProvinceDistrict() {
        log.info("data get successful!!");
        return new ResponseEntity<>( SVClient.getProvinceDistrict(), HttpStatus.OK);
    }

    @GetMapping("/companies")
    public ResponseEntity<Map<String, CompanyInfo>> get_companies() {
        log.info("data get successful!!");
        return new ResponseEntity<>(SVClient.getCompanies(), HttpStatus.OK);
    }

    @GetMapping("company/routes")
    public ResponseEntity<Map<String, RouteInfo>> get_company_router(@RequestParam String companyId) {
        log.info("data get successful!!");
        return new ResponseEntity<>(SVClient.getCompaniesRoutes(companyId), HttpStatus.OK);
    }
    @GetMapping("saveDbRouteInfo")
    public String saveDbRouteInfo(@RequestParam String companyId) {
        log.info("data get successful!!");
        return getDataFacade.getRouteInfo(companyId);
    }

    @GetMapping("tripsPoint")

    public ResponseEntity< Map<String, List<Trip>>> getTripsPoint(@RequestParam int page, @RequestParam int size, @RequestParam String date, @RequestParam
            String startPoint, @RequestParam String endPoint, @RequestParam String startTimeFrom, @RequestParam String startTimeTo) throws Exception {

        log.info("data get successful!!");
        return new ResponseEntity<>(SVClient.getTripByPoints(page, size, date, startPoint, endPoint, startTimeFrom, startTimeTo), HttpStatus.OK);
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
    public ResponseEntity get_trip_tickets(@RequestParam String tripId, @RequestParam String pointUpId, @RequestParam String pointDownId) throws Exception {


        log.info("data get successful!!");
        return new ResponseEntity<>( SVClient.getTripsTickets(tripId, pointUpId, pointDownId), HttpStatus.OK);
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
    @GetMapping("getListPoint")
    public ResponseEntity<Object> getListPoint() {
        log.info("data get successful!!");
        return new ResponseEntity<>( svClient.getListPoint(), HttpStatus.OK);
    }
}



