package com.its.sanve.api.controller;

import com.its.sanve.api.communication.CallBot.CallBotClient;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Log4j2
@RestController
@RequestMapping(value = "/callbot/")
public class CallBotController {
    @Autowired
    CallBotClient callBotClient;
    @GetMapping("StartEndCity")
    public ResponseEntity<Object> getStartEndCity(){
//        Map<String>
        return  null;
    }

}
