package com.its.sanve.api.communication.SanVe;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.its.sanve.api.communication.AbstractCommunication;
import com.its.sanve.api.communication.SanVeCommunicate;

import com.its.sanve.api.communication.dto.CalculatePriceRequest;
import com.its.sanve.api.communication.dto.CalculatePriceResponse;
import com.its.sanve.api.entities.Province;
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
import javax.jws.Oneway;
import java.util.List;

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
            request = sanVeCommunicate.getProvinceDistrict(apiKey.trim(), SecretKey.trim());

           Response<SanveResponse> response =  request.execute();
            log.info("3");
            if (response.isSuccessful()) {
                log.info("4");
                data = response.body();
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

          Response<SanveResponse> response =  request.execute();
            log.info("3");
            if (response.isSuccessful()) {
                log.info("4");
                data =  response.body();
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
        try {
            log.info("1");
            request = sanVeCommunicate.getCompanyRoute(apiKey.trim(),SecretKey.trim(),CompanyId.trim());
            log.info(request);
            log.info("2");
            Response<SanveResponse> response =  request.execute();
            log.info("3");
            if (response.isSuccessful()) {
                log.info("4");
                data =  response.body();
                log.info("5");

            } else {
                log.info("jambalaya");
            }

        } catch (Exception e) {
            log.info(e.getMessage());
        }

        return data;
    }
    public Object getTripsbyPoints(int page,int size,String date,String startPoint,String endPoint,String startTimeFrom,
                           String startTimeTo) throws Exception {

        SanveResponse data = null;
        Call<SanveResponse> request = null;
        try {
            log.info("1");
            request = sanVeCommunicate.getTripsByPoint(apiKey.trim(),SecretKey.trim(),page,size,date.trim(),startPoint.trim(),endPoint.trim(),startTimeFrom.trim(),startTimeTo.trim());
            log.info(request);
            log.info("2");
            Response<SanveResponse> response =  request.execute();
            log.info("3");
            if (response.isSuccessful()) {
                log.info("4");
                data =  response.body();
                log.info("5");

            } else {
                log.info("jambalaya");
            }

        } catch (Exception e) {
            log.info(e.getMessage());
        }

        return data;
    }
    public Object getTripsbyRouteId(int page,int size,String date,String startTimeFrom,
                           String startTimeTo,String RouteId) throws Exception {

        SanveResponse data = null;
        Call<SanveResponse> request = null;
        try {
            log.info("1");
            request = sanVeCommunicate.getTripsByRouteId(apiKey.trim(),SecretKey.trim(),size,page,date.trim(),startTimeFrom.trim(),startTimeTo.trim(),RouteId.trim());

            log.info("2");
            Response<SanveResponse> response =  request.execute();
            log.info("3");
            if (response.isSuccessful()) {
                log.info("4");
                data =  response.body();
                log.info("5");

            } else {
                log.info("jambalaya");
            }

        } catch (Exception e) {
            log.info(e.getMessage());
        }

        return data;
    }
    public Object getTripsTickets(String tripId,String pointUpId,
                                    String pointDownId) throws Exception {

        SanveResponse data = null;
        Call<SanveResponse> request = null;
        try {
            log.info("1");
            request = sanVeCommunicate.getTickets(apiKey.trim(),SecretKey.trim(),tripId.trim(),pointUpId,pointDownId.trim());
            log.info("2");
            Response<SanveResponse> response =  request.execute();
            log.info("3");
            if (response.isSuccessful()) {
                log.info("4");
                data =  response.body();
                log.info("5");

            } else {
                log.info("jambalaya");
            }

        } catch (Exception e) {
            log.info(e.getMessage());
        }

        return data;
    }
    public CalculatePriceResponse calculatePrice(CalculatePriceRequest request ) throws Exception {
        CalculatePriceResponse results= null;
        log.info("1");
       Call<CalculatePriceResponse> requests = sanVeCommunicate.calculatePrice(request);
        log.info("2");
       Response<CalculatePriceResponse> response = requests.execute();
       log.info(response);
        log.info("3");
       if(response.isSuccessful()){
           log.info("4");
           results = response.body();
           log.info("5");
       }
       return  results;
    }

}
