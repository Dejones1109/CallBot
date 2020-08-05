package com.its.sanve.api.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.its.sanve.api.communication.SanVe.SanveClient;


import com.its.sanve.api.communication.SanVeCommunicate;
import com.its.sanve.api.communication.dto.CalculatePriceRequest;
import com.its.sanve.api.entities.CompanyInfo;

import com.its.sanve.api.exceptions.ProcessErrorException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import retrofit2.Call;


import java.util.*;

@RestController
@Log4j2
public class SanVeController {

    private static final int PARAM_MISSING = 1;
    @Autowired
    SanveClient SVClient;


    @RequestMapping(value = "point/get_province_district",method = RequestMethod.GET)
      public ResponseEntity<Object> getProvinceDistrict() throws Exception {
        Map<String,Object> p = new HashMap<>();
        p.put("1",  SVClient.getProvinceDistrict());

        log.info(SVClient.getProvinceDistrict());
        return new ResponseEntity<>(p.values(), HttpStatus.OK);
    }

    @GetMapping("/companies")
    public ResponseEntity<Object> get_companies() throws Exception {

        Map<String,Object> p = new HashMap<>();
       p.put("1",  SVClient.getCompanies());
//
//        log.info(SVClient.getCompanies());
        return new ResponseEntity<>(p.values(), HttpStatus.OK);
    }

    @GetMapping("company/routes")
    public ResponseEntity<Object> get_company_router(@RequestParam String CompanyId) throws Exception {
           Map<String,Object> p = new HashMap<>();
        p.put("1",SVClient.getCompaniesRoutes(CompanyId));
        return new ResponseEntity<>(p.values(), HttpStatus.OK);
    }

    @GetMapping("trips")
    //trips by Points
    public ResponseEntity<Object> getTrips(@RequestParam int page,@RequestParam int size, @RequestParam String date, @RequestParam
             String startPoint, @RequestParam String endPoint, @RequestParam String startTimeFrom,@RequestParam  String startTimeTo,@RequestParam  String rounteId) throws Exception {

        Map<String,Object> p = new HashMap<>();
        if(startPoint!=null&&endPoint!=null){
            p.put("1",SVClient.getTripsbyPoints(page,size,date,startPoint,endPoint,startTimeFrom,startTimeTo));
        }
       if(rounteId!=null){
           p.put("1",SVClient.getTripsbyRouteId(page,size,date,startTimeFrom,startTimeTo,rounteId));
       }
        return new ResponseEntity<>(p.values(), HttpStatus.OK);
    }

    @GetMapping("trip/tickets")
    public ResponseEntity<Object> get_trip_tickets(@RequestParam String tripId,@RequestParam String pointUpId,@RequestParam String pointDownId) throws  Exception {

        Map<String, Object> p = new HashMap<>();
        log.info(p);
        p.put("1", SVClient.getTripsTickets(tripId,pointUpId,pointDownId));
        return new ResponseEntity<>(p.values(), HttpStatus.OK);
    }
    @PostMapping("order/calc_price")
    public ResponseEntity<Object> get_order_calc_price(@RequestBody CalculatePriceRequest request) throws Exception {

        Map<String, Object> p = new HashMap<>();

        if (request == null) {
            throw new ProcessErrorException(HttpStatus.BAD_REQUEST, PARAM_MISSING, "Null body not allowed");
        }else {
            p.put("1", SVClient.calculatePrice(request));
            log.info(SVClient.calculatePrice(request));
        }
        log.info(p);
        return new ResponseEntity<>(p.values(), HttpStatus.OK);
    }

}



