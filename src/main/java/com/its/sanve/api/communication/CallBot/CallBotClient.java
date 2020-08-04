package com.its.sanve.api.communication.CallBot;

import com.its.sanve.api.communication.SanVe.SanveClient;

import com.its.sanve.api.communication.SanVe.SanveResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Log4j2

public class CallBotClient {
    @Autowired
    SanveClient sanveClient;

    public boolean StartCity_EndCity(String StartCity, String End_City) throws Exception {
        SanveResponse data = (SanveResponse) sanveClient.getProvinceDistrict();
        log.info(data.getData());
        return true;
    }
}
