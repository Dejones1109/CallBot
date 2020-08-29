package com.its.sanve.api.communication.callbot;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.its.sanve.api.entities.Ticket;
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

    public boolean checkRouteName(String routeInfo) {
        boolean check = false;
        if (routeInfo.isEmpty()) {
            check = false;
        } else {
            check = true;
        }

        return check;
    }

    //    public int isCheckCity(String data, String route_name) {
//        if (data.toLowerCase().contains(route_name.toLowerCase())) {
//            return 1;
//        }
//        return 0;
//
//    }
    public Object listStartTimeRealityforRoute(String data, String date) throws JsonProcessingException {
        JsonNode jsonNode = objectMapper.readTree(data);
        List<Object> list = new ArrayList<>();
        //   log.info(jsonNode);
        log.info("8");
        JsonNode arraynodes = jsonNode.get("data");
        JsonNode arraynode = arraynodes.get("trips");
        log.info("9");
        if (arraynode.isArray()) {
            log.info("So sánh ngày khác ngày hôm  nay");
            for (final JsonNode objNode : arraynode) {
                if (objNode.get("startDateReality").asText().equals(date)) {
                    log.info(objNode.get("startTimeReality").asText() + ",");

                    list.add(ConventTimer(objNode.get("startTimeReality").asText()));

                    log.info(ConventTimer(objNode.get("startTimeReality").asText()));
                    log.info("thành công");

                }
            }
        }


        return list;
    }

    public Object listStartTimeReality(String data, String date, String companyId) {
        Map<String, Object> list = new HashMap<>();
        Integer valid = 0;
        List<String> listTripId = new ArrayList();
        List<String> listRouteId = new ArrayList();
        List<String> listTimes = new ArrayList();


        log.info("cắt chuyễn Json");


        try {

            JsonNode jsonNode = objectMapper.readTree(data);


            JsonNode arraynodes = jsonNode.get("data");
            JsonNode arraynode = arraynodes.get("trips");


            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMdd");
            log.info("currentDay");

            String currentDay = format.format(now);
            Integer currentTime = (now.getHour() * 3600000 + now.getMinute() * 60 * 1000);

            if (date.equals(currentDay)) {
                if (arraynode.isArray()) {
                    log.info("So sánh ngày hôm nay");

                    for (final JsonNode objNode : arraynode) {
                        if (objNode.get("startDateReality").asText().equals(currentDay) && objNode.get("startDateReality").asText().equals(companyId)) {
                            if (objNode.get("startTimeReality").asInt() > currentTime) {
                                listTripId.add(objNode.get("tripId").asText());
                                listRouteId.add(objNode.get("routeId").asText());
                                listTimes.add(ConventTimer(objNode.get("startTimeReality").asText()));
                            }
                            log.info("thành công");

                        }
                    }
                }

            } else {
                if (arraynode.isArray()) {
                    log.info("So sánh ngày khác ngày hôm  nay");
                    Long time = System.currentTimeMillis();
                    for (final JsonNode objNode : arraynode) {


                        if (objNode.get("startDateReality").asText().equals(date) && objNode.get("companyId").asText().equals(companyId)) {
                         //   log.info(objNode.get("startTimeReality").asText() + ",");
//                            if(listRouteId.isEmpty()){
                            listTripId.add(objNode.get("tripId").asText());
                            listRouteId.add(objNode.get("routeId").asText());
                            listTimes.add(ConventTimer(objNode.get("startTimeReality").asText()));
//                            }else {
//                                if (!(listTimes.contains(ConventTimer(objNode.get("startTimeReality").asText())))) {
//                                    listTripId.add(objNode.get("tripId").asText());
//                                    listRouteId.add(objNode.get("routeId").asText());
//                                    listTimes.add(ConventTimer(objNode.get("startTimeReality").asText()));
//                                    log.info("thành công");
//                                }
//                            }

                        }

                        Long time1 = System.currentTimeMillis();
                      //  log.info("chạy 1 routeId :" + (time1 - time));
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

    public Map<String,String> QuantitiesTickets(Map<String,List<Ticket>> data) throws JsonProcessingException {
        Map<String,String> quantitys = new HashMap<>();
        List<Ticket> listTickets = data.get("tickets");
        int count = 0;
        double temp =0;
        for (Ticket ticket : listTickets) {

            if (ticket.getStatus() != null) {
                count++;
                Double code = ticket.getOriginalTicketPrice();
                if (temp == 0) {
                    temp = code;
                } else if (code < temp) {
                    temp = code;
                }
            }
        }
        log.info("the number of seats available,{}",count);
        log.info("the quatity of ticket,{}",temp);

            quantitys.put("ticket_qtt", Integer.toString(count));

            quantitys.put("price", Double.toString(temp));


        return quantitys;
    }

    public Object listRoutes(String data, String StartCity, String EndCity, String routeName, String RouteId) throws JsonProcessingException {

        Map<String, Object> listPoints = new HashMap<>();
        List PointUp = new ArrayList();
        List PointUpId = new ArrayList();
        List PointDown = new ArrayList();
        List PointDownID = new ArrayList();
        try {
            log.info("Chuyển Json thành java Object");
            JsonNode routeInfos = objectMapper.readTree(data);
            //   log.info(routeInfos);
            JsonNode Routes = routeInfos.get("data");
            log.info(Routes);
            log.info("So sánh chuyến");
            log.info(Routes.isArray());
            for (JsonNode obj : Routes) {
                if (obj.get("routeId").asText().equals(RouteId)) {
                    JsonNode listPoint = obj.get("listPoint");
                    if (listPoint.isArray()) {
                        for (JsonNode arr : listPoint) {
                            log.info("vào vòng for");
                            if (arr.get("province").asText().toLowerCase().equals(StartCity.toLowerCase())) {

                                PointUp.add(arr.get("id").asText());
                                log.info(PointUp);
                                PointUpId.add(arr.get("name").asText());
                                log.info(PointUpId);

                            }
                            if (arr.get("province").asText().toLowerCase().equals(EndCity.toLowerCase())) {

                                PointDown.add(arr.get("id").asText());
                                log.info(PointDown);
                                PointDownID.add(arr.get("name").asText());
                                log.info(PointDownID);

                            }
                        }

                    }
                    listPoints.put("PointUpID", PointUp);
                    listPoints.put("PointUp", PointUpId);
                    listPoints.put("PointDownID", PointDown);
                    listPoints.put("PointDown", PointDownID);

                }

            }


            log.info("thành công");
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return listPoints;
    }


}
