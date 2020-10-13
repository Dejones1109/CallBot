package com.its.sanve.api.communication;

import java.io.File;

import javax.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.its.sanve.api.communication.dto.OrderTicketRequest;
import com.its.sanve.api.communication.sanve.SanVeResponse;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Log4j2
@Component
public class CallBotCommunication extends AbstractCommunication {

    @Value("${sanve.endpoint}")
    private String baseUrl;
    private SanVeCommunicate botCommunicate;

    @Autowired
    private ObjectMapper objectMapper;
    @PostConstruct
    public void intConnection() {
        this.botCommunicate = buildSetting();
    }

    private SanVeCommunicate buildSetting() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(JacksonConverterFactory.create())
                .client(this.buildCommunication())
                .build();

        return retrofit.create(SanVeCommunicate.class);
    }

//    public int orderTicket(OrderTicketRequest orderTicketRequest) {
//        try {
//
//            RequestBody secretPart = RequestBody.create(MediaType.parse("text/plain"), orderTicketRequest.getSecretKey().toString());
//            RequestBody apiPart = RequestBody.create(MediaType.parse("text/plain"), orderTicketRequest.getApiKey().toString());
//            RequestBody seatPart = RequestBody.create(MediaType.parse("text/plain"), orderTicketRequest.getSeat().toString());
//            RequestBody pointPart = RequestBody.create(MediaType.parse("text/plain"), orderTicketRequest.getPoint().toString());
//            RequestBody tripIdPart = RequestBody.create(MediaType.parse("text/plain"), orderTicketRequest.getTripId().toString());
//            RequestBody routeIdPart = RequestBody.create(MediaType.parse("text/plain"), orderTicketRequest.getRouteId().toString());
//            RequestBody fullNamePart = RequestBody.create(MediaType.parse("text/plain"), orderTicketRequest.getCustomerFullname().toString());
//            RequestBody phonePart = RequestBody.create(MediaType.parse("text/plain"), orderTicketRequest.getCustomerPhone().toString());
//            RequestBody companyIdPart = RequestBody.create(MediaType.parse("text/plain"), orderTicketRequest.getCompanyId().toString());
//
//            Call<SanVeResponse> request = botCommunicate.orderTicket(secretPart, apiPart, seatPart, pointPart, routeIdPart, tripIdPart, fullNamePart, phonePart, companyIdPart);
//            log.info(request);
//            Response<SanVeResponse> response = request.execute();
//            log.info("response", response);
//            log.debug("response {}", response);
//            if(response.isSuccessful()) {
//                SanVeResponse data = response.body();
//                log.info("Call api anvui success");
//                return data.getStatus();
//            }else {
//
//                log.info("Call order ticket failed");
//                return 0;
//            }
//
//        } catch (Exception e) {
//            // TODO: handle exception
//            log.debug(e);
//            return 0;
//        }
//    }
public Object orderTicket(String apiKey,String secretKey, Object seatSelected, Object pointSelected,String routeId,String tripId, String phone,String fullName, String companyId, String phoneOrder) {
        try {

            RequestParam secretPart = RequestParam.create(MediaType.parse("text/plain"), apiKey);
            RequestParam apiPart = RequestParam.create(MediaType.parse("text/plain"), secretKey);
            RequestParam seatPart = RequestParam.create(MediaType.parse("text/plain"), seatSelected);
            RequestParam pointPart = RequestParam.create(MediaType.parse("text/plain"), pointSelected);
            RequestParam tripIdPart = RequestParam.create(MediaType.parse("text/plain"), pointSelected);
            RequestParam routeIdPart = RequestParam.create(MediaType.parse("text/plain"), routeId);
            RequestParam fullNamePart = RequestParam.create(MediaType.parse("text/plain"), fullName);
            RequestParam phonePart = RequestParam.create(MediaType.parse("text/plain"), phoneOrder);
            RequestParam companyIdPart = RequestParam.create(MediaType.parse("text/plain"), companyId);

            Call<SanVeResponse> request = botCommunicate.orderTicket1(secretPart, apiPart, seatPart, pointPart, routeIdPart, tripIdPart, fullNamePart, phonePart, companyIdPart);
            log.info(request);
            Response<SanVeResponse> response = request.execute();
            log.info("response", response);
            log.debug("response {}", response);
            if(response.isSuccessful()) {
                SanVeResponse data = response.body();
                log.info("Call api anvui success");
                return data.getData();
            }else {

                log.info("Call order ticket failed");
                return null;
            }

        } catch (Exception e) {
            // TODO: handle exception
            log.debug(e);
            return null;
        }
    }


}