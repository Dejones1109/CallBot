package com.its.sanve.api.communication.sanve;

import com.its.sanve.api.communication.AbstractCommunication;
import com.its.sanve.api.communication.SanVeCommunicate;

import com.its.sanve.api.communication.SanVeCommunicateV1;
import com.its.sanve.api.entities.Point;
import com.its.sanve.api.utils.MessageUtils;

import javax.annotation.PostConstruct;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
@Component
@Log4j2
@NoArgsConstructor
@AllArgsConstructor
public class SanVeClientV1 extends AbstractCommunication {
    @Value(value = "${SV.apiKey}")
    String apiKey;
    @Value(value = "${SV.secretKey}")
    String secretKey;
    @Value("${sanve.endpoint1}")
    private String baseUrl;


    @Autowired
    MessageUtils messageUtils;

    private SanVeCommunicateV1 sanVeCommunicateV1;


    @PostConstruct
    public void intConnection() {
        this.sanVeCommunicateV1 = buildSetting();
    }

    private SanVeCommunicateV1 buildSetting() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(JacksonConverterFactory.create())
                .client(this.buildCommunication())
                .build();

        return  retrofit.create(SanVeCommunicateV1.class);
    }
    public String getListPoint() {

        try {
            Call<String> request = sanVeCommunicateV1.getListPoint(apiKey, secretKey);
            log.debug("request:,{}", request);

            Response<String> response = request.execute();
            log.debug("response: ,{}", response);


            if (response.isSuccessful()) {
                log.info("response successfully!!!");
               String data = response.message();
                log.info("Successfully!!! {}", data);

                return data;

            } else {
                log.warn("response failed!!!");
                return null;
            }

        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return null;
        }


    }
}
