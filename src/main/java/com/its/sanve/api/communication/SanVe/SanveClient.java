package com.its.sanve.api.communication.SanVe;

import com.fasterxml.jackson.core.JsonProcessingException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.its.sanve.api.communication.AbstractCommunication;
import com.its.sanve.api.communication.SanVeCommunicate;

import com.its.sanve.api.communication.SanVe.Order_Cal_Price.dto.CalculatePriceRequest;
import com.its.sanve.api.communication.SanVe.Order_Cal_Price.dto.CalculatePriceResponse;

import com.its.sanve.api.utils.MessageUtils;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;

@Log4j2
@Component
public class SanveClient extends AbstractCommunication {


    @Value(value = "${SV.apiKey}")
    String apiKey;
    @Value(value = "${SV.secretKey}")
    String SecretKey;
    @Value("${sanve.endpoint}")
    private String baseUrl;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    MessageUtils messageUtils;

    private SanVeCommunicate sanVeCommunicate;

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


    public Object getProvinceDistrict() throws Exception {

        SanveResponse data = null;
        Call<SanveResponse> request = null;
        try {
            log.info("1");
            long time1 = System.currentTimeMillis();
            request = sanVeCommunicate.getProvinceDistrict(apiKey.trim(), SecretKey.trim());


            Response<SanveResponse> response = request.execute();
            long time2 = System.currentTimeMillis();


            log.info("3");
            if (response.isSuccessful()) {
                log.info("4");
                data = response.body();
                log.info(time2 - time1);
                log.info(data);
                //   System.out.println(data.getData());
                log.info("5");

            } else {
                log.info("jambalaya");
            }

        } catch (Exception e) {
            log.info(e.getMessage());
        }

        return data;
    }

    public Object getCompanies() throws Exception {

        SanveResponse data = null;
        Call<SanveResponse> request = null;
        try {
            log.info("1");
            request = sanVeCommunicate.getCompanies(apiKey.trim(), SecretKey.trim());

            Response<SanveResponse> response = request.execute();
            log.info("3");
            if (response.isSuccessful()) {
                log.info("4");
                data = response.body();
                log.info("5");

            } else {
                log.info("jambalaya");
            }

        } catch (Exception e) {
            log.info(e.getMessage());
        }

        return data;
    }

    public Object getCompaniesRoutes(String CompanyId) throws Exception {

        SanveResponse data = null;
        Call<SanveResponse> request = null;

        log.info("1");
        request = sanVeCommunicate.getCompanyRoute(apiKey.trim(), SecretKey.trim(), CompanyId.trim());
        log.info(request);
        log.info("2");
        Long time = null;
        Response<SanveResponse> response = request.execute();
        log.info("3");
        if (response.isSuccessful()) {
            log.info("4");
            data = response.body();
//            log.info(data.getData());
//            ObjectMapper objectMapper = new ObjectMapper();
//            String string1 = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(data.getData());
//            log.info("5");
//            log.info(string1);
//            try {
//                log.info("6");
//                Map<Integer, String> map = new HashMap<>();
//                JsonNode routeInfos = objectMapper.readTree(string1);
//                String routeName = "Hà Nội - Sài Gòn";
//                log.info(routeName);
//                Long time1 = System.currentTimeMillis();
//                for (JsonNode obj : routeInfos) {
//                    if (obj.get("routeName").asText().toLowerCase().equals(routeName.toLowerCase())) {
//                        log.info("7");
//                        log.info(obj.get("routeName").asText());
//                        JsonNode arrList = obj.get("listPoint");
//                        if (arrList.isArray()) {
//                            log.info("8");
//                            for (final JsonNode i : arrList) {
//                                log.info("9");
//                                if (i.get("province").asText().toLowerCase().equals("hà nội")) {
//                                    log.info("10");
//                                    map.put(1, i.get("name").asText());
//                                    log.info(map.values());
//                                    log.info("11");
//
//                                }
//
//                            }
//
//                            //  log.info(arrList.get("address").asText()+"|");
//                        }
//                        Long time2 = System.currentTimeMillis();
//                        // log.info(time2-time1);
//
//                        log.info(time2 - time1);
//                        // log.info("7");
//
//                    }
//                }
//            } catch (Exception e) {
//                log.info(e.getMessage());
//            }
        }
        return data;
    }

    public Object getTripsbyPoints(int page, int size, String date, String startPoint, String endPoint, String startTimeFrom,
                                   String startTimeTo) throws Exception {

        String start = URLDecoder.decode(startPoint, StandardCharsets.UTF_8.toString());
        log.info("-2");
        log.info(start);
        String end = URLDecoder.decode(endPoint, StandardCharsets.UTF_8.toString());
        log.info("-2");
        log.info(end);
        SanveResponse data = null;
        Call<SanveResponse> request = null;
        try {
            log.info("1");
            request = sanVeCommunicate.getTripsByPoint(apiKey.trim(), SecretKey.trim(), page, size, date.trim(), start, end, startTimeFrom.trim(), startTimeTo.trim());
            log.info(request);
            log.info("2");
            Response<SanveResponse> response = request.execute();
            log.info("3");
            if (response.isSuccessful()) {
                log.info("4");
                data = response.body();
                log.info("5");
                ObjectMapper map = new ObjectMapper();
                log.info(data.getData());
                String string1 = map.writerWithDefaultPrettyPrinter().writeValueAsString(data.getData());

                log.info("6");
                log.info(string1);


                try {
                    log.info("7");
                    JsonNode jsonNode = map.readTree(string1);
                    log.info(jsonNode);
                    log.info("8");
                    JsonNode arraynode = jsonNode.get("trips");
                    log.info("9");
                    if (arraynode.isArray()) {
                        log.info("10");
                        for (final JsonNode objNode : arraynode) {
                            log.info(objNode.get("routeId").asText() + ", ");
                            log.info("a");
//                            if(o)
                        }
                        log.info("11");
                    }
                    //  log.info(jsonNode.get("trips").asText());
                    log.info("12");


                } catch (Exception e) {
                    log.info(e.getMessage());
                }

            } else {
                log.info("jambalaya");
            }

        } catch (Exception e) {
            log.info(e.getMessage());
        }

        return data;
    }

    public Object getTripsbyRouteId(int page, int size, String date, String startTimeFrom,
                                    String startTimeTo, String RouteId) throws Exception {

        SanveResponse data = null;
        Call<SanveResponse> request = null;
        try {
            log.info("1");
            request = sanVeCommunicate.getTripsByRouteId(apiKey.trim(), SecretKey.trim(), size, page, date.trim(), startTimeFrom.trim(), startTimeTo.trim(), RouteId.trim());

            log.info("2");
            Response<SanveResponse> response = request.execute();
            log.info("3");
            if (response.isSuccessful()) {
                log.info("4");
                data = response.body();
                log.info("5");

            } else {
                log.info("jambalaya");
            }

        } catch (Exception e) {
            log.info(e.getMessage());
        }

        return data;
    }

    public Object getTripsTickets(String tripId, String pointUpId,
                                  String pointDownId) throws Exception {

        SanveResponse data = null;
        Call<SanveResponse> request = null;
        try {
            log.info("1");
            request = sanVeCommunicate.getTickets(apiKey.trim(), SecretKey.trim(), tripId.trim(), pointUpId, pointDownId.trim());
            log.info("2");
            Response<SanveResponse> response = request.execute();
            log.info("3");
            if (response.isSuccessful()) {
                log.info("4");
                data = response.body();
                log.info("5");

            } else {
                log.info("jambalaya");
            }

        } catch (Exception e) {
            log.info(e.getMessage());
        }

        return data;
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

    public int isCheckCity(String data, String route_name) {
        if (data.toLowerCase().contains(route_name.toLowerCase())) {
            return 1;
        }
        return 0;

    }

    public Object listRoutes(String data, String City, String routeName) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<Integer, String> listPoints = new HashMap<>();

        try {
            log.info("1");
            JsonNode routeInfos = objectMapper.readTree(data);
            log.info(routeInfos);
            JsonNode Routes = routeInfos.get("data");
            log.info(Routes);
            log.info("2");
            for (JsonNode obj : Routes) {

                if (obj.get("routeName").asText().toLowerCase().equals(routeName.toLowerCase())) {
                    JsonNode listPoint = obj.get("listPoint");
                    if (listPoint.isArray()) {
                        for (JsonNode arr : listPoint) {
                            // if(arr.get("province").asText().equals("Sài Gòn"||"Hô"))
                            if (arr.get("province").asText().toLowerCase().equals(City.toLowerCase())) {
                                listPoints.put(1, arr.get("name").asText());
                            }
                        }
                    }
                }
            }
            log.info("4");
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return listPoints.values();
    }


}



