package com.its.sanve.api.controller;

import com.its.sanve.api.communication.CallBot.CallBotClient;
import com.its.sanve.api.communication.SanVe.SanveClient;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Log4j2
@RestController
@RequestMapping(value = "/callbot")
public class CallBotController  {
  @Autowired
  SanveClient sanveClient;
    @PostMapping("StartEndCity")
    public ResponseEntity<Object> getStartEndCity(@RequestParam String startCity,@RequestParam String endCity) throws Exception {
        Map<String,Object> p = new HashMap<>();
        String route_name = startCity +" - "+ endCity;
        String CompanyId= "TC01gWSmr8A9Qx";
        Object data = sanveClient.getCompaniesRoutes(CompanyId);
       log.info(data.toString());
        p.put("1",sanveClient.isCheckCity(data.toString(),route_name));
        log.info(p);
        return new ResponseEntity<>(p.values(), HttpStatus.OK);
    }

}
