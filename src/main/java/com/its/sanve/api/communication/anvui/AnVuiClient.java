package com.its.sanve.api.communication.anvui;

import com.its.sanve.api.communication.dto.OldCustomerRequest;
import com.its.sanve.api.communication.dto.TripsRequest;
import com.its.sanve.api.entities.Customer;
import com.its.sanve.api.entities.Route;
import com.its.sanve.api.entities.Ticket;
import com.its.sanve.api.entities.Trip;
import com.its.sanve.api.implement.QueryEntityImplement;
import com.its.sanve.api.repositories.PointRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@Component
public class AnVuiClient {
    @Autowired
    AnVuiCommunication anVuiCommunication;
    @Autowired
    PointRepository pointRepository;
    @Autowired
    QueryEntityImplement query;


    public Map<String, Object> getRoute(String startPoint, String endPoint) {
        int status = 0;
        try {
            Map<String, Object> mapRoute = new HashMap<>();
            List startPointNames = new ArrayList();
            List startPointIds = new ArrayList();
            List endPointNames = new ArrayList();
            List endPointIds = new ArrayList();
            List startShortNames = new ArrayList();
            List endShortNames = new ArrayList();


            List<Route> listRoutes = query.getRouteInfo(startPoint, endPoint);
            log.info("listRoutes:{}", query.getRouteInfo(startPoint, endPoint));
            if (listRoutes.isEmpty()) {
                status = 0;

            } else {
                for (Route route : listRoutes) {
                    log.info("start!!!");
                    if (route.getStartProvince().contains(route.getEndProvince()) == true) {
                        status = 0;
                    } else {
                        if (route.getStartKeyword().contains(startPoint.toLowerCase()) == true && (route.getEndKeyword().contains(endPoint.toLowerCase()) == true)) {
                            status = 1;
                        }

                        if ((route.getStartProvince().contains(startPoint.toLowerCase()) == true) && (route.getEndKeyword()).contains(endPoint.toLowerCase()) == true) {
                            status = 2;
                        }

                        if (route.getStartKeyword().contains(startPoint.toLowerCase()) == true && route.getEndProvince().contains(endPoint.toLowerCase()) == true) {
                            status = 3;
                        }

                        if (route.getStartProvince().contains(startPoint.toLowerCase()) == true && route.getEndProvince().contains(endPoint.toLowerCase()) == true) {
                            status = 4;
                        }
                        startPointNames.add(route.getStartKeyword());
                        startPointIds.add(route.getStartPointId());
                        endPointNames.add(route.getEndKeyword());
                        endPointIds.add(route.getEndPointId());
                        startShortNames.add(route.getShortKeywordStart());
                        endShortNames.add(route.getShortKeywordEnd());
                    }

                    log.info("status:{}", status);

                }
            }

            mapRoute.put("status", status);
            mapRoute.put("startPointId", startPointIds);
            mapRoute.put("startPointName", startPointNames);
            mapRoute.put("listEndPointId", endPointIds);
            mapRoute.put("endPointName", endPointNames);
            mapRoute.put("startShortName", startShortNames);
            mapRoute.put("endShortName", endShortNames);
            return mapRoute;

        } catch (Exception e) {
            log.error("error:", e);
            return null;
        }

    }

    public Map<String, Object> getTrip(TripsRequest request) {
        try {
            Map<String, Object> listMap = new HashMap<>();
            int valid = 0;
            List<String> listTotalTripId = new ArrayList<>();
            List<String> listTotalTripName = new ArrayList<>();
            List<String> listTripId = new ArrayList<>();
            List<String> listTripName = new ArrayList<>();
            List<Trip> listTrips = anVuiCommunication.getTrips(request);
            int startHour = conventHour(request.getStartHour());
            log.info("startHour:{}", startHour);

            for (Trip trip : listTrips) {
                if (trip.getTotalEmptySeat() >= request.getNumberTicket()) {

                    log.info("trip:{}", trip);
                    if (listTotalTripName.isEmpty()) {
                        listTotalTripId.add(trip.getId());
                        listTotalTripName.add(conventTimer(trip.getStartTimeReality()));
                    } else {
                        if (!listTotalTripName.contains(conventTimer(trip.getStartTimeReality()))) {
                            listTotalTripId.add(trip.getId());
                            listTotalTripName.add(conventTimer(trip.getStartTimeReality()));
                        }
                    }
                }
            }
            int count = listTotalTripName.size();
            log.info("count:{}", count);
            Boolean status = false;

            for (int i = 0; i < count; i++) {
                log.info("tripName:{}", conventHour(listTotalTripName.get(i)));
                if (startHour == conventHour(listTotalTripName.get(i))) {
                    valid = 1;
                    listTripId.add(listTotalTripId.get(i));
                    listTripName.add(request.getStartHour());
                    break;
                } else {
                    valid = 2;

                    if (startHour < conventHour(listTotalTripName.get(0))) {
                        listTripName.add(listTotalTripName.get(0));
                        listTripId.add(listTotalTripId.get(0));
                        listTripName.add(listTotalTripName.get(1));
                        listTripId.add(listTotalTripId.get(1));
                        break;
                    } else if (startHour > conventHour(listTotalTripName.get(count - 1))) {

                        listTripName.add(listTotalTripName.get(count - 2));
                        listTripId.add(listTotalTripId.get(count - 2));
                        listTripName.add(listTotalTripName.get(count - 1));
                        listTripId.add(listTotalTripId.get(count - 1));
                        break;
                    } else {
                        if (startHour < conventHour(listTotalTripName.get(i)) && status == false) {
                            status = true;
                            listTripName.add(listTotalTripName.get(i - 1));
                            listTripId.add(listTotalTripId.get(i - 1));
                            listTripName.add(listTotalTripName.get(i));
                            listTripId.add(listTotalTripId.get(i));

                            break;
                        }
                    }
                }
            }
            listMap.put("listTotalTripId", listTotalTripId);
            listMap.put("listTotalTripName", listTotalTripName);
            listMap.put("valid", valid);
            listMap.put("listTripId", listTripId);
            listMap.put("listTripName", listTripName);
            return listMap;
        } catch (Exception e) {
            log.warn("error:{}", e.getMessage());
            return null;
        }
    }

    public Map<String, Object> getOldCustomer(OldCustomerRequest request) {
        Map<String, Object> map = new HashMap<>();
        String fullName = "";
        List<String> pointUp = new ArrayList<>();
        List<String> pointUpId = new ArrayList<>();
        List<String> pointUpShort = new ArrayList<>();
        List<String> pointDownId = new ArrayList<>();
        List<String> provinceUp = new ArrayList<>();
        List<String> provinceDown = new ArrayList<>();
        List<String> pointDown = new ArrayList<>();
        List<String> pointDownShort = new ArrayList<>();
        try {
            Customer[] customers = anVuiCommunication.oldCustomerTicket(request);
            log.info("customer:{}",customers);
            for (Customer customer : customers) {
                log.info("ticket:{}",customer.getTickets());
                for (Ticket ticket : customer.getTickets()) {
                    fullName = ticket.getFullName();
                    log.info("fullName:{}",fullName);
                    pointUpId.add(ticket.getPointUp().getPointId());
                    log.info("pointUpId:{}",pointUpId);
                    String pUp = pointRepository.keyword(ticket.getPointUp().getPointId());
                    log.info("pUp:{}",pUp);
                    String[] subPointUp = pUp.split(",");
                    pointUp.add(subPointUp[0]);
                    log.info("pointUp:{}",pointUp);
                    pointUpShort.add(subPointUp[1]);
                    provinceUp.add(subPointUp[2]);
                    log.info("pointUpShort:{}",pointUpShort);
                    pointDownId.add(ticket.getPointDown().getPointId());
                    log.info("pointDownId:{}",pointDownId);
                    String pDown = pointRepository.keyword(ticket.getPointDown().getPointId());
                    log.info("pDown:{}",pDown);
                    String[] subPointDown = pDown.split(",");
                    pointDown.add(subPointDown[0]);
                    pointDownShort.add(subPointDown[1]);
                    provinceDown.add(subPointDown[2]);

                }
            }


            map.put("pointUp", pointUp);
            map.put("pointUpId", pointUpId);
            map.put("pointUpShort", pointUpShort);
            map.put("pointDown", pointDown);
            map.put("pointDownId", pointDownId);
            map.put("pointDownShort", pointDownShort);
            map.put("fullName", fullName);
            map.put("provinceUp",provinceUp);
            map.put("provinceDown",provinceDown);
            return map;
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return null;
        }
    }

    private String conventTimer(Double startTimeReality) {
        String timer;
//        double Time = Double.parseDouble(startTimeReality);
        double number = startTimeReality / 3600000;
        int hour = (int) Math.floor(number);
        if (number == hour) {
            if (hour < 10) {
                timer = "0" + hour + "h00";
            } else {
                timer = hour + "h00";
            }
        } else {
            int minus = (int) Math.round((number - hour) * 60);
            if (hour < 10) {
                if (minus < 10) {
                    timer = "0" + hour + "h0" + minus;
                } else {
                    timer = "0" + hour + "h" + minus;
                }
            } else {

                if (minus < 10) {
                    timer = hour + "h0" + minus;
                } else {
                    timer = hour + "h" + minus;
                }
            }


        }
        return timer;
    }

    private int conventHour(String startHour) {
        String[] substring = startHour.split("h");

        return (Integer.parseInt(substring[0]) * 60 + Integer.parseInt(substring[1])) * 60 * 1000;
    }

}
