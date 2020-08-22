package com.its.sanve.api.communication.CallBot;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.internal.$Gson$Preconditions;
import com.its.sanve.api.communication.SanVe.Data;
import com.its.sanve.api.communication.SanVe.SanveClient;

import com.its.sanve.api.communication.SanVe.SanveResponse;
import com.its.sanve.api.entities.RouteInfo;
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

    public boolean checkRouteName(List<String> routeInfo){
        boolean check = true;
        for (String obj : routeInfo) {
        	  if (obj != null) {
        	    check = true;
        	    //break;
        	  }else {
        		  check = false;
        	  }
        }
//        if(routeInfo==null){
//           //check = false;
//        }else{
//           // check = true;
//        }
        return check;
    }
//    public int isCheckCity(String data, String route_name) {
//        if (data.toLowerCase().contains(route_name.toLowerCase())) {
//            return 1;
//        }
//        return 0;
//
//    }

    public Object listStartTimeReality(String data, String date) {
        Map<String, Object> list = new HashMap<>();
        Integer valid = 0;
        List listTripId = new ArrayList();
        List listRouteId = new ArrayList();
        List listTimes = new ArrayList();


        log.info("cắt chuyễn Json");


        try {
            log.info("7");
            JsonNode jsonNode = objectMapper.readTree(data);
            //   log.info(jsonNode);
            log.info("8");
            JsonNode arraynodes = jsonNode.get("data");
            JsonNode arraynode = arraynodes.get("trips");
            log.info("9");

            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMdd");
            log.info("currentDay");
            log.info(now.getHour());
            log.info(now.getHour()*3600000);
            log.info(now.getMinute()*60*1000);
            String currentDay = format.format(now);
            Integer currentTime =(now.getHour()*3600000+now.getMinute()*60*1000);
            log.info(currentTime);
            log.info(currentDay);
            log.info(date);
            if(date.equals(currentDay)){
                if (arraynode.isArray()) {
                    log.info("So sánh ngày hôm nay");
                    for (final JsonNode objNode : arraynode) {
                        if (objNode.get("startDateReality").asText().equals(currentDay)) {
                            if(objNode.get("startTimeReality").asInt() > currentTime){
                                listTripId.add(objNode.get("tripId").asText());
                                listRouteId.add(objNode.get("routeId").asText());
                                listTimes.add(ConventTimer(objNode.get("startTimeReality").asText()));
                                log.info(ConventTimer(objNode.get("startTimeReality").asText()));
                            }
                             log.info("thành công");

                        }
                    }
                }

            }else {
            if (arraynode.isArray()) {
                log.info("So sánh ngày khác ngày hôm  nay");
                for (final JsonNode objNode : arraynode) {
                    if (objNode.get("startDateReality").asText().equals(date)) {
                        log.info(objNode.get("startTimeReality").asText() + ",");
                        listTripId.add(objNode.get("tripId").asText());
                        listRouteId.add(objNode.get("routeId").asText());
                        listTimes.add(ConventTimer(objNode.get("startTimeReality").asText()));

                        log.info(ConventTimer(objNode.get("startTimeReality").asText()));
                        log.info("thành công");

                    }
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

    public Object QuantitiesTickets(String data) throws JsonProcessingException {
        Map<String, Object> quantitys = new HashMap<>();
        JsonNode node = objectMapper.readTree(data);
        JsonNode tickets = node.get("data");
        JsonNode ti = tickets.get("tickets");

        log.info(ti);
        log.info("6");
        int count = 0;
        if (ti.isArray()) {
            log.info("7");

            int temp = 0;
            for (JsonNode ticket : ti) {
                if (ticket.get("ticketStatus").asInt() == 7) {
                    count++;
                    log.info(ticket.get("originalTicketPrice").asInt());
                    int code = ticket.get("originalTicketPrice").asInt();
                    if (temp == 0) {
                        temp = code;
                    } else if (code < temp) {
                        temp = code;
                    }
                }
            }
            log.info(count);
            quantitys.put("ticket_qtt", count);
            log.info(temp);
            quantitys.put("price", temp);

        }

        log.info(count);
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
                        if (obj.get("routeName").asText().toLowerCase().equals(routeName.toLowerCase())) {
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
                }

            log.info("thành công");
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return listPoints;
    }


}
