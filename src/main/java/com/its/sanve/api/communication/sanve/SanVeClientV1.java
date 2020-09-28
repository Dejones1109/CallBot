package com.its.sanve.api.communication.sanve;

import com.its.sanve.api.communication.AbstractCommunication;

import com.its.sanve.api.communication.SanVeCommunicateV1;
import com.its.sanve.api.entities.Point;
import com.its.sanve.api.entities.Trip;
import com.its.sanve.api.utils.MessageUtils;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        return retrofit.create(SanVeCommunicateV1.class);
    }

    public Object getListPoint() {

        try {

            Call<Object> request = sanVeCommunicateV1.getListPoint(apiKey, secretKey);
            log.info("request:,{}", request);

            Response<Object> response = request.execute();
            log.info("response: ,{}", response);

            if (response.isSuccessful()) {
                log.info("response successfully!!!");
                Object data = response.body();
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

    public List<Trip> getListTripByNumber(String pointUp, String pointDown, String startDay, String companyId, int numberTicket) {

        try {

            Call<SanVeV1Response<List<Trip>>> request = sanVeCommunicateV1.getListTripsByNumber(apiKey, secretKey, pointUp, pointDown, startDay, companyId, numberTicket);
            log.info("request:,{}", request);

            Response<SanVeV1Response<List<Trip>>> response = request.execute();
            log.info("response: ,{}", response);

            if (response.isSuccessful()) {
                log.info("response successfully!!!");
                SanVeV1Response<List<Trip>> data = response.body();
                log.info("Successfully!!! {}", data.getData());
                List<Trip> listTrips = data.getData();
                log.info("listTrips:{}", listTrips);
                Map<String, Object> map = new HashMap<>();
                int valid = 0;
                ArrayList<String> listTripID = new ArrayList();
                ArrayList<String> listTripName = new ArrayList();
                ArrayList<Boolean> listPickingAtHome = new ArrayList<>();
                ArrayList<Boolean> listDroppingAtHome = new ArrayList<>();
                String startTimer = "25000000";
                log.info("startTime:{}", startTimer);
                int numberAbove = 0;
                int numberBelow = 0;
                boolean req = false;
                if (listTrips.isEmpty()) {
                    valid = 0;
                } else {
                    ArrayList<Integer> listStartTimeReality = new ArrayList();
                    for (Trip trip : listTrips) {
                        Point pUp = trip.getPointUp();
                        Point pDown = trip.getPointDown();
                        log.info("startTimeReality:{}", trip.getStartTimeReality());
                        if (trip.getStartTimeReality().equals(startTimer)) {
                            valid = 1;
                            listTripID.add(trip.getId());
                            listTripName.add(ConventTimer(trip.getStartTimeReality()));
                            listPickingAtHome.add(pUp.getAllowPickingAndDroppingAtHome());
                            listDroppingAtHome.add(pDown.getAllowPickingAndDroppingAtHome());
                            req = true;
                            break;
                        } else {
                            valid = 2;
                            listStartTimeReality.add(Integer.parseInt(trip.getStartTimeReality()));
                        }


                    }
                    log.info("listStartReality:{}", listStartTimeReality);
                    if (req == false) {
                        log.info("listTripID:{}",listTripID);
                        log.info("listTripName:{}",listTripName);
                        int count = listStartTimeReality.size() - 1;
                        int temp = Integer.parseInt(startTimer);
                        if (temp < listStartTimeReality.get(0)) {
                            numberAbove = listStartTimeReality.get(0);
                        } else if (temp > listStartTimeReality.get(count)) {
                            numberBelow = listStartTimeReality.get(count);
                        } else {
                            for (int i = 0; i < count; i++) {
                                if (listStartTimeReality.get(i) > temp&&req==false) {
                                    numberAbove = listStartTimeReality.get(i);
                                    numberBelow = listStartTimeReality.get(i - 1);
                                    break;
                                }
                            }
                        }

                        log.info("numberAbove:{}", numberAbove);
                        log.info("numberBelow:{}", numberBelow);

                    }
                    for (Trip trip : listTrips) {
                        Point pUp = trip.getPointUp();
                        Point pDown = trip.getPointDown();
                        if (trip.getStartTimeReality().equals(String.valueOf(numberAbove)) || trip.getStartTimeReality().equals(String.valueOf(numberBelow))) {
                            listTripID.add(trip.getId());
                            listTripName.add(ConventTimer(trip.getStartTimeReality()));
                            listPickingAtHome.add(pUp.getAllowPickingAndDroppingAtHome());
                            listDroppingAtHome.add(pDown.getAllowPickingAndDroppingAtHome());
                        }
                    }
                }

                map.put("valid", valid);
                map.put("listTripId", listTripID);
                map.put("listTripName", listTripName);
                map.put("pickingAtHome",listPickingAtHome);
                map.put("droppingAtHome",listDroppingAtHome);
                log.info("map:{}", map);


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
}
