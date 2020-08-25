package com.its.sanve.api.controller;


import com.its.sanve.api.communication.SanVe.SanveClient;


import com.its.sanve.api.communication.SanVe.Order_Cal_Price.dto.CalculatePriceRequest;
import com.its.sanve.api.communication.SanVe.Order_Cal_Price.dto.CalculatePriceResponse;
import com.its.sanve.api.dto.GwResponseDto;
import com.its.sanve.api.facade.GetDataFacade;
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

    private static final int PARAM_MISSING = 1;
    @Autowired
    SanveClient SVClient;
    
    @Autowired(required = true)
    private GetDataFacade getDataFacade;
    
    @Autowired
    MessageUtils messageUtils;


    @RequestMapping(value = "point/get_province_district", method = RequestMethod.GET)
    public ResponseEntity<Object> getProvinceDistrict() throws Exception {
        Map<String, Object> p = new HashMap<>();
        
        p.put("1", getDataFacade.getDataProvinceDistrict());
        //p.put("1", SVClient.getProvinceDistrict());

        log.info(SVClient.getProvinceDistrict());
        return new ResponseEntity<>(p.values(), HttpStatus.OK);
    }

    @GetMapping("/companies")
    public ResponseEntity<Object> get_companies() throws Exception {

        Map<String, Object> p = new HashMap<>();
        p.put("1", getDataFacade.getDataCompanies());
        
//       p.put("1", SVClient.getCompanies());
//
//        log.info(SVClient.getCompanies());
        return new ResponseEntity<>(p.values(), HttpStatus.OK);
    }

    @GetMapping("company/routes")
    public ResponseEntity<Object> get_company_router(@RequestParam String companyId) throws Exception {
        Map<String, Object> p = new HashMap<>();
        p.put("1", getDataFacade.getDataRoutes(companyId));
//        p.put("1", SVClient.getCompaniesRoutes(CompanyId));
        return new ResponseEntity<>(p.values(), HttpStatus.OK);
    }

    @GetMapping("tripsPoint")
    //trips by Points
    public ResponseEntity<Object> getTripsPoint(@RequestParam int page, @RequestParam int size, @RequestParam String date, @RequestParam
            String startPoint, @RequestParam String endPoint, @RequestParam String startTimeFrom, @RequestParam String startTimeTo) throws Exception {

        Map<String, Object> p = new HashMap<>();

            p.put("1", SVClient.getTripsbyPoints(page, size, date, startPoint, endPoint, startTimeFrom, startTimeTo));

        return new ResponseEntity<>(p.values(), HttpStatus.OK);
    }
    @GetMapping("tripsRoute")
    //trips by Points
    public ResponseEntity<Object> getTrips(@RequestParam int page, @RequestParam int size, @RequestParam String date,
            @RequestParam String startTimeFrom, @RequestParam String startTimeTo, @RequestParam String routeId) throws Exception {

        Map<String, Object> p = new HashMap<>();

            p.put("1", SVClient.getTripsbyRouteId(page, size, date, startTimeFrom, startTimeTo, routeId));

        return new ResponseEntity<>(p.values(), HttpStatus.OK);
    }

    @GetMapping("trip/tickets")
    public ResponseEntity<Object> get_trip_tickets(@RequestParam String tripId, @RequestParam String pointUpId, @RequestParam String pointDownId) throws Exception {

        Map<String, Object> p = new HashMap<>();
        log.info(p);
        p.put("1", SVClient.getTripsTickets(tripId, pointUpId, pointDownId));
        return new ResponseEntity<>(p.values(), HttpStatus.OK);
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
    
    @GetMapping("getInfoCompany")
    public ResponseEntity<Object> getInfoCompany(@RequestParam String phone) throws Exception{
    	Map<String, Object> map = new HashMap<>();
    	map.put("1", getDataFacade.getInfoCompany(phone));
    	return new ResponseEntity<Object>(map.values(), HttpStatus.OK);
    }

}



