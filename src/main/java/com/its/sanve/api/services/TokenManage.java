package com.its.sanve.api.services;

import com.its.sanve.api.communication.anvui.AnVuiCommunication;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Getter
@Log4j2
public class TokenManage {
    String token;
    @Autowired
    AnVuiCommunication anvui;
   @Value("${token.username}")
   String userName;
   @Value("${token.password}")
   String password;
   @Value("${token.accesscode}")
   String accesscode;
   @Value(("${token.devicetype}"))
   String devicetype;
   @Value("${token.refreshtoken}")
   String refreshtoken;

    @Scheduled(fixedDelay =60*60*1000)
    private void refreshToken(){
        String token = anvui.login(userName,password,accesscode,devicetype,refreshtoken);
        if(token != null){
            this.token = token;
        }
        log.info("token:{}",token);
    }

}
