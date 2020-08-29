package com.its.sanve.api.communication.sanve;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.its.sanve.api.communication.AbstractCommunication;
import com.its.sanve.api.communication.SanVeCommunicate;

import com.its.sanve.api.communication.dto.CalculatePriceRequest;
import com.its.sanve.api.communication.dto.CalculatePriceResponse;
import com.its.sanve.api.communication.dto.OrderTicketRequest;
import com.its.sanve.api.entities.*;
import com.its.sanve.api.utils.MessageUtils;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Component;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;



import javax.annotation.PostConstruct;
import java.io.IOException;

import java.net.URLDecoder;

import java.nio.charset.StandardCharsets;
import java.util.*;

@Log4j2
@Component
@Configurable
public class SanveClient extends AbstractCommunication {


    @Value(value = "${SV.apiKey}")
    String apiKey;
    @Value(value = "${SV.secretKey}")
    String secretKey;
    @Value("${sanve.endpoint}")
    private String baseUrl;

    @Autowired
    MessageUtils messageUtils;

    private SanVeCommunicate sanVeCommunicate;
    @Autowired
    private
    ObjectMapper objectMapper = new ObjectMapper();

    @PostConstruct
    public void intConnection() {
        this.sanVeCommunicate = buildSetting();
    }

    private SanVeCommunicate buildSetting() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(JacksonConverterFactory.create())
                .client(this.buildCommunication())
                .build();

        return retrofit.create(SanVeCommunicate.class);
    }


    public List<Province> getProvinceDistrict() {

        try {
            Call<SanveResponse<List<Province>>> request = sanVeCommunicate.getProvinceDistrict(apiKey, secretKey);
            log.debug("request:,{}", request);

            Response<SanveResponse<List<Province>>> response = request.execute();
            log.debug("response: ,{}", response);


            if (response.isSuccessful()) {
                log.info("response successfully!!!");
                SanveResponse<List<Province>> data = response.body();
                log.info("Successfully!!! {}", data.getData());
                return data.getData();

            } else {
                log.warn("response failed!!!");
                return null;
            }

        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return null;
        }


    }

    public Map<String, CompanyInfo> getCompanies() {
        try {
            Call<SanveResponse<Map<String, CompanyInfo>>> request = sanVeCommunicate.getCompanies(apiKey.trim(), secretKey.trim());
            log.debug("request,{}", request);
            Response<SanveResponse<Map<String, CompanyInfo>>> response = request.execute();
            log.debug("response,{}", request);
            // log.info("re");
            if (response.isSuccessful()) {
                log.info("response successfully!!!");
                SanveResponse<Map<String, CompanyInfo>> data = response.body();
                log.info("Successfully!!! {}", data.getData());
                return data.getData();

            } else {
                log.warn("response failed!!!");
                return null;
            }

        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return null;
        }


    }

    public Map<String, RouteInfo> getCompaniesRoutes(String companyId) {
        log.debug("request get ");

        try {
            Call<SanveResponse<Map<String, RouteInfo>>> request = sanVeCommunicate.getCompanyRoute(apiKey.trim(), secretKey.trim(), companyId.trim());
            log.debug("request,{}", request);
            Response<SanveResponse<Map<String, RouteInfo>>> response = request.execute();
            log.debug("response,{}", response);

            if (response.isSuccessful()) {
                log.info("response successfully");
                SanveResponse<Map<String, RouteInfo>> data = response.body();
                log.info("Successfully!!! {}", data.getData());
//                return objectMapper.convertValue(data.getData(),List.class);
                return data.getData();
            } else {
                log.warn("response failed!!!");
                return null;
            }

        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return null;
        }
    }

    public Map<String, List<Trip>> getTripByPoints(int page, int size, String date, String startPoint, String endPoint, String startTimeFrom, String startTimeTo) throws Exception {
        String start = URLDecoder.decode(startPoint, StandardCharsets.UTF_8.toString());
        log.info("-2");
        log.info(start);
        String end = URLDecoder.decode(endPoint, StandardCharsets.UTF_8.toString());
        log.info("-2");
        log.info(end);
        try {
            Call<SanveResponse<Map<String, List<Trip>>>> request = sanVeCommunicate.getTripsByPoint(apiKey.trim(), secretKey.trim(), page, size, date.trim(), start, end, startTimeFrom.trim(), startTimeTo.trim());
            log.debug("request,{}", request);
            Response<SanveResponse<Map<String, List<Trip>>>> response = request.execute();
            log.debug("response,{}", response);
            if (response.isSuccessful()) {
                log.info("response successfully");

                SanveResponse<Map<String, List<Trip>>> data = response.body();
                log.info("Successfully!!! {}", data.getData());
//                return objectMapper.convertValue(data.getData(),List.class);
                return data.getData();
            } else {
                log.warn("response failed!!!");
                return null;
            }
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return null;
        }
    }

//    public Object getTripsbyRouteId(int page, int size, String date, String startTimeFrom,
//                                    String startTimeTo, String RouteId) throws Exception {
//
//        SanveResponse data = null;
//        Call<SanveResponse> request = null;
//        try {
//            log.info("request");
//            request = sanVeCommunicate.getTripsByRouteId(apiKey.trim(), SecretKey.trim(), size, page, date.trim(), startTimeFrom.trim(), startTimeTo.trim(), RouteId.trim());
//
//            log.info("2");
//            Response<SanveResponse> response = request.execute();
//            log.info("responce");
//            if (response.isSuccessful()) {
//                log.info("4");
//                data = response.body();
//                log.info("data trả về thành công");
//
//
//            } else {
//                log.info("failed");
//            }
//
//        } catch (Exception e) {
//            log.info(e.getMessage());
//        }
//
//        return data;
//    }

    public Map<String, List<Ticket>> getTripsTickets(String tripId, String pointUpId,
                                                     String pointDownId) {


        try {

            Call<SanveResponse<Map<String, List<Ticket>>>> request = sanVeCommunicate.getTickets(apiKey, secretKey, tripId, pointUpId, pointDownId);
            log.debug("request,{}", request);
            Response<SanveResponse<Map<String, List<Ticket>>>> response = request.execute();
            log.debug("response,{}", response);

            if (response.isSuccessful()) {
                log.info("response successfully");
                SanveResponse<Map<String, List<Ticket>>> data = response.body();
                log.info("Successfully!!! {}", data.getData());

                Map<String, List<Ticket>> tickets = data.getData();
                log.info("data successful!");
                List<Ticket> listTickets = tickets.get("tickets");

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
                log.info("the qualities of ticket,{}",temp);
                return data.getData();
            } else {
                log.warn("response failed!!!");
                return null;
            }

        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return null;
        }


    }

    public CalculatePriceResponse calculatePrice(CalculatePriceRequest request) throws Exception {
        CalculatePriceResponse results = null;
        log.info("1");
        Call<CalculatePriceResponse> requests = sanVeCommunicate.calculatePrice(request);
        log.info("2");
        Response<CalculatePriceResponse> response = requests.execute();
        log.info(response);
        log.info("3");
        if (response.isSuccessful()) {
            log.info("4");
            results = response.body();
            log.info("5");
        }
        return results;
    }

    public Object createOrderTicket(OrderTicketRequest request) throws IOException {
        SanveResponse data = null;
        log.info("request");
        Call<SanveResponse> requests = sanVeCommunicate.orderTicket(request);
        log.info("response");
        Response<SanveResponse> response = requests.execute();
        if (response.isSuccessful()) {
            data = response.body();
            log.info("success!!!");
            log.info(data);
        }
        return data;
    }

//    public int isCheckCity(String data, String route_name) {
//        if (data.toLowerCase().contains(route_name.toLowerCase())) {
//            return 1;
//        }
//        return 0;
//
//    }
//
//    public Object listRoutes(String data, String City, String routeName, String RouteId) throws JsonProcessingException {
//
//        Map<Integer, String> listPoints = new HashMap<>();
//
//        try {
//            log.info("Chuyển Json thành java Object");
//            JsonNode routeInfos = objectMapper.readTree(data);
//            log.info(routeInfos);
//            JsonNode Routes = routeInfos.get("data");
//            log.info(Routes);
//            log.info("So sánh chuyến");
//            for (JsonNode obj : Routes) {
//                if (obj.get("routeId").asText().equals(RouteId)) {
//                    if (obj.get("routeName").asText().toLowerCase().equals(routeName.toLowerCase())) {
//                        JsonNode listPoint = obj.get("listPoint");
//                        if (listPoint.isArray()) {
//                            for (JsonNode arr : listPoint) {
//                                                 if (arr.get("province").asText().toLowerCase().equals(City.toLowerCase())) {
//                                    listPoints.put(1, arr.get("name").asText());
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//            log.info("thành công");
//        } catch (Exception e) {
//            log.info(e.getMessage());
//        }
//        return listPoints.values();
//    }
//
//    public Object listStartTimeReality(String data, String date) {
//        Map<String, Object> list = new HashMap<>();
//        Integer valid = 0;
//        List listTripId = new ArrayList();
//        List listRouteId = new ArrayList();
//        List listTimes = new ArrayList();
//
//
//        log.info("cắt chuyễn Json");
//
//
//        try {
//            log.info("7");
//            JsonNode jsonNode = objectMapper.readTree(data);
//            //   log.info(jsonNode);
//            log.info("8");
//            JsonNode arraynodes = jsonNode.get("data");
//            JsonNode arraynode = arraynodes.get("trips");
//            log.info("9");
//
//
//            if (arraynode.isArray()) {
//                log.info("So sánh chuỗi");
//                for (final JsonNode objNode : arraynode) {
//                    // listTimes.put("routeId")
//                    if (objNode.get("startDateReality").asText().equals(date)) {
//                        log.info(objNode.get("startTimeReality").asText() + ",");
//                        listTripId.add(objNode.get("tripId").asText());
//                        listRouteId.add(objNode.get("routeId").asText());
//                        listTimes.add(ConventTimer(objNode.get("startTimeReality").asText()));
//
//                        log.info(ConventTimer(objNode.get("startTimeReality").asText()));
//                        log.info("thành công");
//                    }
//                }
//                if (listRouteId.isEmpty() && listTripId.isEmpty()) {
//                    valid = 0;
//                } else if (!listRouteId.isEmpty() && listTripId.isEmpty()) {
//                    valid = 2;
//                } else {
//                    valid = 1;
//                }
//                list.put("valid", valid);
//                list.put("tripId", listTripId);
//                list.put("routeId", listRouteId);
//                list.put("timer", listTimes);
//            }
//        } catch (Exception e) {
//            log.info(e.getMessage());
//
//        }
//        return list;
//    }
//
//    private String ConventTimer(String startTimeReality) {
//        String timer;
//        double Time = Double.parseDouble(startTimeReality);
//        double number = Time / 3600000;
//        int hour = (int) Math.floor(number);
//        if (number == hour) {
//            if (hour < 10) {
//                timer = "0" + hour + "h00";
//            } else {
//                timer = hour + "h00";
//
//            }
//        } else {
//            if (hour < 10) {
//                timer = "0" + hour + "h" + Math.round((number - hour) * 60);
//            } else {
//                timer = hour + "h" + Math.round((number - hour) * 60);
//
//            }
//
//        }
//        return timer;
//    }
//
//    public Object QuantitiesTickets(String data) throws JsonProcessingException {
//        Map<String, Object> quantitys = new HashMap<>();
//        JsonNode node = objectMapper.readTree(data);
//        JsonNode tickets = node.get("data");
//        JsonNode ti = tickets.get("tickets");
//
//        log.info(ti);
//        log.info("6");
//        int count = 0;
//        if (ti.isArray()) {
//            log.info("7");
//
//            int temp = 0;
//            for (JsonNode ticket : ti) {
//                if (ticket.get("ticketStatus").asInt() == 7) {
//                    count++;
//                    log.info(ticket.get("originalTicketPrice").asInt());
//                    int code = ticket.get("originalTicketPrice").asInt();
//                    if (temp == 0) {
//                        temp = code;
//                    } else if (code < temp) {
//                        temp = code;
//                    }
//                }
//            }
//            log.info(count);
//            quantitys.put("ticket_qtt", count);
//            log.info(temp);
//            quantitys.put("price", temp);
//
//        }
//
//        log.info(count);
//        return quantitys;
//    }
}




