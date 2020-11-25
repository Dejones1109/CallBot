package com.its.sanve.api.controller;

import com.its.sanve.api.communication.anvui.AnVuiCommunication;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
@RequestMapping("/anVui")
public class AnVuiController {

    @Autowired
    AnVuiCommunication anVuiCommunication;
    @PostMapping("login")
    public String login(@RequestParam String userName,@RequestParam String password,@RequestParam String accessCode,@RequestParam String deviceType,@RequestParam String refreshToken){
       return anVuiCommunication.login(userName,password,accessCode,deviceType,refreshToken);
    }

}
