package com.its.sanve.api.communication.callbot;

import com.fasterxml.jackson.core.JsonProcessingException;


import com.its.sanve.api.communication.sanve.SanVeClient;
import com.its.sanve.api.entities.*;

import com.its.sanve.api.facede.GetDataFacade;
import com.its.sanve.api.repositories.ListPointRepository;
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
    SanVeClient sanVeClient;
    @Autowired
    GetDataFacade getDataFacade;
    @Autowired
    ListPointRepository listPointRepository;

    public Map<String, Object> isCheckRoute(Map<String, RouteInfo> routeInfoMap, String startCity, String endCity) {
        Map<String, Object> map = new HashMap<>();
        for (String key : routeInfoMap.keySet()) {
            RouteInfo routeInfo = routeInfoMap.get(key);
            log.info("routeInfo:{}", routeInfo);
            List<Point> listPoints = routeInfo.getListPoint();
            List<String> listProvinces = new ArrayList<>();
            Boolean isCheck = false;
            for (Point point : listPoints) {
                log.info("province:{}", point.getProvince());
                String temp = null;
                if (point.getProvince().equals("Hồ Chí Minh")) {
                    temp = point.getProvince().replace("Hồ Chí Minh", "Sài Gòn");
                } else {
                    temp = point.getProvince();
                }
                if (!listProvinces.contains(temp.toLowerCase())) {
                    listProvinces.add(temp.toLowerCase());
                }

            }

            log.info("listProvinces:{}", listProvinces);


            if (!startCity.contains(endCity)) {
                if (listProvinces.contains(startCity.toLowerCase()) && listProvinces.contains(endCity.toLowerCase())) {
                    isCheck = true;
                } else {
                    isCheck = false;
                }
            }


            map.put("status", isCheck);
            if (isCheck == false) {
                map.put("route", listProvinces);
            }
        }


        return map;
    }

    public Map<String, Object> listStartTimeRealityV1(List<Trip> data, String date, String startTime, int numberTicket) {
        Map<String, Object> map = new HashMap<>();
        int valid = 0;
        ArrayList<String> listTripID = new ArrayList();
        ArrayList<String> listTripName = new ArrayList();
        ArrayList<Boolean> listPickingAtHome = new ArrayList<>();
        ArrayList<Boolean> listDroppingAtHome = new ArrayList<>();
        ArrayList<String> listRouteId = new ArrayList<>();
        ArrayList<Double> listPrice = new ArrayList<>();
        ArrayList<String> listEmptySeat = new ArrayList<>();
        String TotalEmptySeat = null;
        int count1 =0;
        int numberAbove = 0;
        int numberBelow = 0;
        boolean req = false;
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMdd");

        String currentDay = format.format(now);
        Integer currentTime = (now.getHour() * 3600000 + now.getMinute() * 60 * 1000);

        if (data.isEmpty()) {
            valid = 0;
        } else {
            ArrayList<Integer> listStartTimeReality = new ArrayList();
            for (Trip trip : data) {
                Point pUp = trip.getPointUp();
                Point pDown = trip.getPointDown();
                SeatMap seatMap = trip.getSeatMap();
                List<Seat> listSeat = seatMap.getSeatList();

                log.info("totalSeatEmpty:{}", trip.getTotalEmptySeat());
                log.info("startTimeReality:{}", trip.getStartTimeReality());
                if (date.equals(currentDay)) {
                    if (Integer.parseInt(trip.getStartTimeReality()) >= currentTime) {
                        if (trip.getStartTimeReality().equals(startTime)) {
                            valid = 1;
                            for (Seat seat : listSeat) {
                                if(count1>=numberTicket){
                                    break;
                                }
                                if (seat.getType() == 3) {
                                    count1++;
                                    if(count1==1){
                                        TotalEmptySeat=seat.getId();
                                    }else {
                                        TotalEmptySeat+=","+ seat.getId();
                                    }
                                }

                            }

                            listEmptySeat.add(TotalEmptySeat);
                            if (!listTripName.contains(ConventTimer(trip.getStartTimeReality()))) {
                                listTripID.add(trip.getId());
                                listRouteId.add(pUp.getRouteId());
                                listTripName.add(ConventTimer(trip.getStartTimeReality()));
                                listPickingAtHome.add(pUp.getAllowPickingAndDroppingAtHome());
                                listDroppingAtHome.add(pDown.getAllowPickingAndDroppingAtHome());
                                listPrice.add(trip.getPrice());
                                for (Seat seat : listSeat) {
                                    if(count1>=numberTicket){
                                        break;
                                    }
                                    if (seat.getType() == 3) {
                                        count1++;
                                        if(count1==1){
                                            TotalEmptySeat=seat.getId();
                                        }else {
                                            TotalEmptySeat+=","+ seat.getId();
                                        }
                                    }

                                }

                                listEmptySeat.add(TotalEmptySeat);
                            }
                            req = true;
                            break;
                        } else {
                            valid = 2;
                            listStartTimeReality.add(Integer.parseInt(trip.getStartTimeReality()));
                        }
                    }

                } else {
                    if (trip.getStartTimeReality().equals(startTime)) {
                        valid = 1;

                        if (!listTripName.contains(ConventTimer(trip.getStartTimeReality()))) {
                            listTripID.add(trip.getId());
                            listRouteId.add(pUp.getRouteId());
                            listTripName.add(ConventTimer(trip.getStartTimeReality()));
                            listPickingAtHome.add(pUp.getAllowPickingAndDroppingAtHome());
                            listDroppingAtHome.add(pDown.getAllowPickingAndDroppingAtHome());
                            listPrice.add(trip.getPrice());
                            for (Seat seat : listSeat) {
                                if(count1>=numberTicket){
                                    break;
                                }
                                if (seat.getType() == 3) {
                                    count1++;
                                    if(count1==1){
                                        TotalEmptySeat=seat.getId();
                                    }else {
                                        TotalEmptySeat+=","+ seat.getId();
                                    }
                                }

                            }

                            listEmptySeat.add(TotalEmptySeat);
                        }
                        req = true;
                        break;
                    } else {
                        valid = 2;
                        listStartTimeReality.add(Integer.parseInt(trip.getStartTimeReality()));
                    }
                }

            }
            log.info("listStartReality:{}", listStartTimeReality);
            if (req == false) {
                log.info("listTripID:{}", listTripID);
                log.info("listTripName:{}", listTripName);
                int count = listStartTimeReality.size() - 1;
                int temp = Integer.parseInt(startTime);
                if (temp < listStartTimeReality.get(0)) {
                    numberAbove = listStartTimeReality.get(0);
                } else if (temp > listStartTimeReality.get(count)) {
                    numberBelow = listStartTimeReality.get(count);
                } else {
                    for (int i = 0; i <= count; i++) {
                        if (listStartTimeReality.get(i) > temp && req == false) {
                            numberAbove = listStartTimeReality.get(i);
                            numberBelow = listStartTimeReality.get(i - 1);
                            break;
                        }
                    }
                }

                log.info("numberAbove:{}", numberAbove);
                log.info("numberBelow:{}", numberBelow);

            }
            for (Trip trip : data) {
                Point pUp = trip.getPointUp();
                Point pDown = trip.getPointDown();
                SeatMap seatMap = trip.getSeatMap();
                List<Seat> listSeat = seatMap.getSeatList();
                if (trip.getStartTimeReality().equals(String.valueOf(numberAbove)) || trip.getStartTimeReality().equals(String.valueOf(numberBelow))) {

                    if (!listTripName.contains(ConventTimer(trip.getStartTimeReality()))) {
                        listTripID.add(trip.getId());
                        listRouteId.add(pUp.getRouteId());
                        listTripName.add(ConventTimer(trip.getStartTimeReality()));
                        listPickingAtHome.add(pUp.getAllowPickingAndDroppingAtHome());
                        listDroppingAtHome.add(pDown.getAllowPickingAndDroppingAtHome());
                        listPrice.add(trip.getPrice());
                        for (Seat seat : listSeat) {
                            if(count1>=numberTicket){
                                break;
                            }
                            if (seat.getType() == 3) {
                                count1++;
                                if(count1==1){
                                    TotalEmptySeat=seat.getId();
                                }else {
                                    TotalEmptySeat+=","+ seat.getId();
                                }
                            }

                        }

                        listEmptySeat.add(TotalEmptySeat);
                    }
                }
            }
        }

        map.put("valid", valid);
        map.put("listTripId", listTripID);
        map.put("listTripName", listTripName);
        map.put("pickingAtHome", listPickingAtHome);
        map.put("droppingAtHome", listDroppingAtHome);
        map.put("listRouteId", listRouteId);
        map.put("listPrice", listPrice);
        map.put("listSeatEmpty",listEmptySeat);
        log.info("map:{}", map);
        return map;
    }

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

    public Map<String, Object> QuantitiesTickets(Map<String, List<Ticket>> tickets, Map<String, List<Trip>> trips, String tripId) throws JsonProcessingException {
        Map<String, Object> quantitys = new HashMap<>();
        List<Ticket> listTickets = tickets.get("tickets");
        List listSeatsEmpty = new ArrayList();
        log.info("count:{}", listTickets.size());

        double temp = 0;
        List<Trip> listTrips = trips.get("trips");


        log.info("listTrips:{}", listTrips);
        for (Trip trip : listTrips) {
            if (trip.getId().equals(tripId)) {
                SeatMap seatMap = trip.getSeatMap();
                List<Seat> listSeat = seatMap.getSeatList();
                for (Seat seat : listSeat) {
                    if (seat.getType() == 3 || seat.getType() == 4 || seat.getType() == 0) {
                        listSeatsEmpty.add(seat.getId());

                    }

                }
                RouteInfo routeInfo = trip.getRouteInfo();
                temp = routeInfo.getDisplayPrice();

            }

        }
        log.info("list seat defaults:{}", listSeatsEmpty);
        for (Ticket ticket : listTickets) {
            Seat seat = ticket.getSeat();
            log.info("seatType:{}", seat.getId());
            listSeatsEmpty.remove(seat.getId());
//            if (ticket.getStatus() != null) {
//                Double originalTicketPrice = ticket.getOriginalTicketPrice();
//                if (temp == 0) {
//                    temp = originalTicketPrice;
//                } else if (originalTicketPrice < temp) {
//                    temp = originalTicketPrice;
//                }
//            }
        }
        log.info("the number of seats available,{}", listSeatsEmpty.size());
        log.info("the qualities of ticket,{}", temp);
        log.info("the list seat empty: {}", listSeatsEmpty);
        quantitys.put("ticketQuantity", Integer.toString(listSeatsEmpty.size()));
        quantitys.put("price", Double.toString(temp));
        quantitys.put("listSeatsEmpty", listSeatsEmpty);
        return quantitys;
    }

    public Map<String, List> listRoutes(Map<String, RouteInfo> data, String startCity, String endCity, String routeId) {

        Map<String, List> listPoints = new HashMap<>();
        List pointUp = new ArrayList();
        List pointUpId = new ArrayList();
        List pointDown = new ArrayList();
        List pointDownId = new ArrayList();

        try {
            for (String key : data.keySet()) {
                if (key.equals(routeId)) {
                    RouteInfo routeInfo = data.get(key);
                    List<Point> listPoint = routeInfo.getListPoint();
                    for (Point point : listPoint) {
                        if (point.getProvince().equals(startCity)) {
                            log.info("List Point Up");
                            pointUp.add(listPointRepository.getPointByPointId(point.getId()));
                            log.info("point UP:{}", listPointRepository.getPointByPointId(point.getId()));

                            pointUpId.add(point.getId());
                            log.info("point up id:{}", point.getId());

                        }
                        if (point.getProvince().equals(endCity)) {
                            log.info("List Point Down:");
                            pointDown.add(listPointRepository.getPointByPointId(point.getId()));
                            log.info("point down:{}", listPointRepository.getPointByPointId(point.getId()));
                            pointDownId.add(point.getId());
                            log.info("point down id:{}", point.getId());
                        }

                    }

                }
//                pointUp = getDataFacade.getListRoute(pointUpId);
//                log.info("listPointUp:{}",pointUp);
//                pointDown = getDataFacade.getListRoute(pointDownId);
//                log.info("listPointDown:{}",pointDown);
            }
            listPoints.put("pointUpId", pointUpId);
            listPoints.put("pointUp", pointUp);
            listPoints.put("pointDownID", pointDownId);
            listPoints.put("pointDown", pointDown);


        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return listPoints;
    }

    public Map<String, String> listRoutesByDB(List<ListPoint> data, String routeId) {
        Map<String, String> listRoutes = new HashMap<>();
        for (ListPoint pointV1 : data) {
//            if(pointV1.getRouteId().equals(routeId)){
//
//            }
        }
        return null;
    }

    public String getAddressPoint(String addressPoint) {
        String[] address;
        if (addressPoint.contains(",")) {
            address = addressPoint.split(",");
        } else {
            address = addressPoint.split("-");
        }
        return address[0];
    }
}
