package com.its.sanve.api.communication;

import java.io.File;

import javax.annotation.PostConstruct;

import lombok.extern.log4j.Log4j2;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

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

    public SanVeResponse orderTicket(OrderTicketRequest orderTicketRequest) {
        try {
            RequestBody secretPart = RequestBody.create(MediaType.parse("text/plain"), orderTicketRequest.getSecretKey());
            RequestBody apiPart = RequestBody.create(MediaType.parse("text/plain"), orderTicketRequest.getApiKey());
            RequestBody seatPart = RequestBody.create(MediaType.parse("text/plain"), orderTicketRequest.getSeat());
            RequestBody pointPart = RequestBody.create(MediaType.parse("text/plain"), orderTicketRequest.getPoint());
            RequestBody tripIdPart = RequestBody.create(MediaType.parse("text/plain"), orderTicketRequest.getTripId());
            RequestBody routeIdPart = RequestBody.create(MediaType.parse("text/plain"), orderTicketRequest.getRouteId());
            RequestBody fullNamePart = RequestBody.create(MediaType.parse("text/plain"), orderTicketRequest.getCustomerFullname());
            RequestBody phonePart = RequestBody.create(MediaType.parse("text/plain"), orderTicketRequest.getCustomerPhone());
            RequestBody companyIdPart = RequestBody.create(MediaType.parse("text/plain"), orderTicketRequest.getCompanyId());

            Call<SanVeResponse> request = botCommunicate.orderTicket(secretPart, apiPart, seatPart, pointPart, routeIdPart, tripIdPart, fullNamePart, phonePart, companyIdPart);
            log.debug("request,{}", request);
            Response<SanVeResponse> response = request.execute();
            log.debug("response,{}", response);

            if (response.isSuccessful()) {
                log.info("response successfully");
                SanVeResponse data = response.body();
                log.info("Successfully!!! {}", data);
                return data;
            } else {
                log.warn("response failed!!!");
                return null;
            }

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }


}
