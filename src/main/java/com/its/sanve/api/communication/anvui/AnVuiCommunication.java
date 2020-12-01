package com.its.sanve.api.communication.anvui;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.its.sanve.api.communication.AbstractCommunication;
import com.its.sanve.api.communication.AnVuiCommunicate;
import com.its.sanve.api.communication.TokenManage;
import com.its.sanve.api.communication.dto.RouteRequest;
import com.its.sanve.api.communication.dto.TripsRequest;
import com.its.sanve.api.entities.RouteInfo;
import com.its.sanve.api.entities.TransactionToken;
import com.its.sanve.api.entities.Trip;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import retrofit2.http.POST;

import javax.annotation.PostConstruct;
import java.util.*;

@Log4j2
@Component
public class AnVuiCommunication extends AbstractCommunication {
    @Value("${anvui.endpoint}")
    private String baseUrl;
    private AnVuiCommunicate anVuiCommunicate;
   @Autowired
   TokenManage tokenManage;

    @PostConstruct
    public void intConnection() {

        this.anVuiCommunicate =  buildSetting();
    }

    private AnVuiCommunicate buildSetting() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(JacksonConverterFactory.create())
                .client(this.buildCommunication())
                .build();

        return retrofit.create(AnVuiCommunicate.class);
    }
    @POST
    public String login( String userName,String password,   String accessCode,  String deviceType,  String refreshToken) {
        try {
            Call<AnVuiResponse<LinkedHashMap<String, Object>>> request = anVuiCommunicate.login(userName,password, accessCode,deviceType,refreshToken);
            log.debug("request,{}", request);
            Response<AnVuiResponse<LinkedHashMap<String, Object>>> response = request.execute();
            log.debug("response,{}", request);
            // log.info("re");
            if (response.isSuccessful()) {
                log.info("response successfully!!!");
                AnVuiResponse<LinkedHashMap<String, Object>> data =  response.body();
                LinkedHashMap<String,Object> token = (LinkedHashMap<String, Object>) data.results.get("token");
                log.info("token:{}",token);
                ObjectMapper objectMapper = new ObjectMapper();
                String jsonStr = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(token);
                TransactionToken transactionToken = objectMapper.readValue(jsonStr,TransactionToken.class);
                return transactionToken.getToken();

            } else {
                log.warn("response failed!!!");
                return null;
            }

        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return null;
        }


    }
    public List<RouteInfo> getRouteInfo(RouteRequest RouteRequest){
        try {
        Call<AnVuiResponse<Map<String,List<RouteInfo>>>> request = anVuiCommunicate.getListRoute(RouteRequest);
        Response<AnVuiResponse<Map<String,List<RouteInfo>>>> response = request.execute();
        if (response.isSuccessful()) {
            log.info("response successfully!!!");
            AnVuiResponse<Map<String,List<RouteInfo>>> data = response.body();
            log.info("Successfully!!! {}", data.getResults());

            return data.getResults().get("result");

        } else {
            log.warn("response failed!!!");
            return null;
        }

    } catch (Exception ex) {
        log.error(ex.getMessage(), ex);
        return null;
    }
    }
     public List<Trip> getTrips(TripsRequest tripsRequest){
        try {
            Call<AnVuiResponse<LinkedHashMap<String,List<Trip>>>> request = anVuiCommunicate.getTrips(tokenManage.getToken(),tripsRequest);
            log.info("request:{}",request);
            Response<AnVuiResponse<LinkedHashMap<String,List<Trip>>>> response = request.execute();
            log.info("response:{}",response);
            if (response.isSuccessful()) {
                log.info("response successfully!!!");
                AnVuiResponse<LinkedHashMap<String,List<Trip>>> data = response.body();

                LinkedHashMap<String,List<Trip>> map = data.getResults();
                List<Trip> listTrips = map.get("trips");

                return listTrips;

            } else {
                log.error("response failed!!!");
                return null;
            }

        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return null;
        }
    }

}
