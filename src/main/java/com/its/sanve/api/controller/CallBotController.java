package com.its.sanve.api.controller;

import com.its.sanve.api.communication.anvui.AnVuiClient;
import com.its.sanve.api.communication.anvui.AnVuiCommunication;
import com.its.sanve.api.communication.dto.OldCustomerRequest;
import com.its.sanve.api.communication.dto.PriceTicketRequest;
import com.its.sanve.api.communication.dto.TicketRequest;
import com.its.sanve.api.communication.dto.TripsRequest;
import com.its.sanve.api.entities.Ticket;
import com.its.sanve.api.facede.GetDataFacade;
import com.its.sanve.api.repositories.PointRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Log4j2
@RequestMapping("/callBot")

public class CallBotController {

    @Autowired
    GetDataFacade getDataFacade;
    @Autowired
    AnVuiClient anVuiClient;
    @Autowired
    PointRepository pointRepository;
    @Autowired
    AnVuiCommunication anVuiCommunication;

    @GetMapping("getCompanyInfo")
    public ResponseEntity<Object> getInfoCompany(@RequestParam String phone) throws Exception {
        return new ResponseEntity<>(getDataFacade.getCompanyInfo(phone), HttpStatus.OK);
    }

    @GetMapping("getRouteInfo")
    public Map<String, Object> getRouteInfo(@RequestParam String startPoint, @RequestParam String endPoint) {

        return anVuiClient.getRoute(startPoint, endPoint);
    }

    @PostMapping("getTrip")
    public Map<String, Object> getTrip(@RequestBody TripsRequest request) {

        return anVuiClient.getTrip(request);
    }

    @PostMapping("getPriceTicket")
    public Map<String, Object> getPriceTicket(@RequestBody PriceTicketRequest request) {
        return anVuiCommunication.getPriceTicket(request);
    }

    @PostMapping("oldCustomer")
    public Map<String, Object> oldCustomer(@RequestParam String phone, @RequestParam String companyId) {
        OldCustomerRequest request = new OldCustomerRequest();
        request.setCount(5);
        request.setPage(0);
        request.setPhoneNumber(phone);
        request.setRouteId("");
        return anVuiClient.getOldCustomer(request, companyId);
    }

    @PostMapping("orderTicket")
    public Object orderTicket(@RequestBody TicketRequest request) {

        request.setPlatform(1);
        Ticket[] tickets = new Ticket[1];
        tickets[0] = new Ticket();
        tickets[0].setSeatType(3);
        tickets[0].setIsAdult(true);
        tickets[0].setFullName("");
        tickets[0].setBirthday("");
        tickets[0].setEmail("");
        tickets[0].setImage("");
        tickets[0].setNote("");
        tickets[0].setSendSMS(true);
        tickets[0].setPaymentType(3);
        tickets[0].setForeignKey("");
        tickets[0].setSurcharges(null);
        tickets[0].setOrder("");
        for (Ticket ticket : request.getInformationsBySeats()) {
            tickets[0].setSeatId(ticket.getSeatId());
            tickets[0].setPhoneNumber(ticket.getPhoneNumber());
            tickets[0].setAgencyPrice(ticket.getAgencyPrice());
            tickets[0].setPaidMoney(ticket.getPaidMoney());
            tickets[0].setPointUp(ticket.getPointUp());
            tickets[0].setPointDown(ticket.getPointDown());
            tickets[0].setOrderPhoneNumber(ticket.getOrderPhoneNumber());
            tickets[0].setStartDate(ticket.getStartDate());
            tickets[0].setStartTime(ticket.getStartTime());
        }
        request.setInformationsBySeats(tickets);
        log.info("request:{}", request);
        return anVuiClient.orderTicket(request);
    }

//    private String convertDateTime(String date) throws ParseException {
//        Date initDate = new SimpleDateFormat("dd/MM/yyyy").parse(date);
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
//        String parsedDate = formatter.format(initDate);
//        System.out.println(parsedDate);
//        return parsedDate;
//    }
//
//    private String convertStartTime(String startTime) {
//        int time = 0;
//        String[] number = startTime.split("h");
//        time = Integer.parseInt(number[0]) * 3600000 + Integer.parseInt(number[1]) * 60000;
//        return String.valueOf(time);
//    }


}
