package com.its.sanve.api.communication.callbot;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.its.sanve.api.entities.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Log4j2
@Component
public class CallBotClient {
    @Autowired
    ObjectMapper objectMapper = new ObjectMapper();


    public Map<String, Object> listStartTimeReality(Map<String, List<Trip>> data, String date, String companyId) {
        Map<String, Object> list = new HashMap<>();
        int valid = 0;
        List<String> listTripId = new ArrayList();
        List<String> listRouteId = new ArrayList();
        List<String> listTimes = new ArrayList();

        try {

            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMdd");


            String currentDay = format.format(now);
            Integer currentTime = (now.getHour() * 3600000 + now.getMinute() * 60 * 1000);
            List<Trip> trips = data.get("trips");
            log.debug("List Trips:{}", trips);
            if (date.equals(currentDay)) {
                log.info("currentDay");
                for (Trip trip : trips) {
                    CompanyInfo companyInfo = trip.getCompanyInfo();
                    if (trip.getStartDateReality().equals(date) && companyInfo.getId().equals(companyId)) {
                        int timer = Integer.parseInt(trip.getStartTimeReality());
                        if (timer > currentTime) {
                            log.info("StartRealitiesTimer!!!");
                            listTripId.add(trip.getId());
                            log.info("TripId : {}", trip.getId());
                            listRouteId.add(trip.getRouteId());
                            log.info("RouteId : {}", trip.getRouteId());
                            listTimes.add(ConventTimer(trip.getStartTimeReality()));
                            log.info("StartTimeReality : {}", trip.getStartTimeReality());
                        }
                    }

                }
            } else {
                for (Trip trip : trips) {
                    log.info("otherDay!!");
                    CompanyInfo companyInfo = trip.getCompanyInfo();
                    if (trip.getStartDateReality().equals(date) && companyInfo.getId().equals(companyId)) {
                        log.info("StartRealityTimer!!!");
                        listTripId.add(trip.getId());
                        log.info("TripId : {}", trip.getId());
                        listRouteId.add(trip.getRouteId());
                        log.info("RouteId : {}", trip.getRouteId());
                        listTimes.add(ConventTimer(trip.getStartTimeReality()));
                        log.info("StartTimeReality : {}", trip.getStartTimeReality());
                    }

                }
            }

            if (listRouteId.isEmpty() && listTripId.isEmpty()) {
                valid = 0;
            } else if (!listRouteId.isEmpty() && listTripId.isEmpty()) {
                valid = 2;
            } else {
                valid = 1;
            }
            list.put("valid", valid);
            list.put("tripId", listTripId);
            list.put("routeId", listRouteId);
            list.put("timer", listTimes);
        } catch (Exception e) {
            log.info(e.getMessage());

        }
        return list;
    }

    private String ConventTimer(String startTimeReality) {
        String timer;
        double Time = Double.parseDouble(startTimeReality);
        double number = Time / 3600000;
        int hour = (int) Math.floor(number);
        if (number == hour) {
            if (hour < 10) {
                timer = "0" + hour + "h00";
            } else {
                timer = hour + "h00";
            }
        } else {
            if (hour < 10) {
                timer = "0" + hour + "h" + Math.round((number - hour) * 60);
            } else {
                timer = hour + "h" + Math.round((number - hour) * 60);

            }

        }
        return timer;
    }

    public Map<String, String> QuantitiesTickets(Map<String, List<Ticket>> data) throws JsonProcessingException {
        Map<String, String> quantitys = new HashMap<>();
        List<Ticket> listTickets = data.get("tickets");
        int count = 0;
        double temp = 0;
        for (Ticket ticket : listTickets) {

            if (ticket.getStatus() != null) {
                count++;
                Double originalTicketPrice = ticket.getOriginalTicketPrice();
                if (temp == 0) {
                    temp = originalTicketPrice;
                } else if (originalTicketPrice < temp) {
                    temp = originalTicketPrice;
                }
            }
        }
        log.info("the number of seats available,{}", count);
        log.info("the quatity of ticket,{}", temp);

        quantitys.put("ticketQuantity", Integer.toString(count));

        quantitys.put("price", Double.toString(temp));


        return quantitys;
    }

    public Map<String, List> listRoutes(Map<String, RouteInfo> data, String startCity, String endCity, String routeId) throws JsonProcessingException {

        Map<String, List> listPoints = new HashMap<>();
        List pointUp = new ArrayList();
        List pointUpId = new ArrayList();
        List pointDown = new ArrayList();
        List pointDownId = new ArrayList();
        List addressPointUp = new ArrayList();
        List addressPointDown = new ArrayList();
        try {
            for (String key : data.keySet()) {
                if (key.equals(routeId)) {
                    RouteInfo routeInfo = data.get(key);
                    List<Point> listPoint = routeInfo.getListPoint();
                    for (Point point : listPoint) {
                        if (point.getProvince().equals(startCity)) {
                            log.info("List Point Up");
                            pointUp.add(point.getName());
                            log.info("point UP:{}", point.getName());

                            pointUpId.add(point.getId());
                            log.info("point up id:{}", point.getId());

                            addressPointUp.add(getAddressPoint(point.getAddress()));
                            log.info(" address point up :{}", getAddressPoint(point.getAddress()));
                        }
                        if (point.getProvince().equals(endCity)) {
                            log.info("List Point Down:");
                            pointDown.add(point.getName());
                            log.info("point down:{}", point.getName());

                            pointDownId.add(point.getId());
                            log.info("point down id:{}", point.getId());

                            addressPointDown.add(getAddressPoint(point.getAddress()));
                            log.info(" address point down :{}", getAddressPoint(point.getAddress()));
                        }

                    }
                }


            }
            listPoints.put("pointUpId", pointUpId);
            listPoints.put("pointUp", pointUp);
            listPoints.put("pointDownID", pointDownId);
            listPoints.put("pointDown", pointDown);
            listPoints.put("addressPointDown", addressPointDown);
            listPoints.put("addressPointUp", addressPointUp);

        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return listPoints;
    }

    public String getAddressPoint(String addressPoint) {
        String[] address = null;
        if (addressPoint.contains(",")) {
            address = addressPoint.split(",");
        } else {
            address = addressPoint.split("-");
        }
        return address[0];
    }
}
