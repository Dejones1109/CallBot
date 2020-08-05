package com.its.sanve.api.communication.CallBot;

import com.its.sanve.api.communication.SanVe.Data;
import com.its.sanve.api.communication.SanVe.SanveClient;

import com.its.sanve.api.communication.SanVe.SanveResponse;
import com.its.sanve.api.entities.RouteInfo;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Log4j2
@Component
public class CallBotClient {

    SanveClient sanveClient;

    public  Object StartCity_EndCity(String StartCity,String EndCity) throws Exception {
        String CompanyId= "TC01gWSmr8A9Qx";

        String route_name = StartCity +"-"+ EndCity;
     Object data = sanveClient.getCompaniesRoutes(CompanyId);
     Integer
             ischeck = sanveClient.isCheckCity(data.toString(),route_name);
     log.info(ischeck);
      return ischeck;
    }



}
