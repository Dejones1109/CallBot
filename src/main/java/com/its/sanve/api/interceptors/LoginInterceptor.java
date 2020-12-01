/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.its.sanve.api.interceptors;


import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author thanh
 */
@Log4j2
@Component
public class LoginInterceptor implements HandlerInterceptor{
//    @Value("#{'${client.id}'.toLowerCase().split(',')}")
//    private List<String> clientID;
//    @Autowired
//    private MessageUtils messageUtils;
    @Value("${rate.limiter}")
    private int rateLimiter;
//    
//    public static final String CLIENT_ID = "client_id";
//    public static final String requestTime = "requestTime";
    
    @Autowired
    LoginInterceptor(){
        
    }
    
    @Override
    public boolean preHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler)
            throws Exception {
        log.info("===========================================================================");


        response.addHeader("X-RateLimit-Limit", String.valueOf(rateLimiter));
        response.addHeader("Content-Type", "application/json");
        response.addHeader("DOBODY6969", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJUQzBDSzFybFV5bjFxcXV5IiwiaXNzIjoiYW52dWkiLCJleHAiOjE2MDY2MTQzMTcsImp0aSI6IlVTMEpVMXViQmlqN3JZdUwifQ.lpLfd91LyIhdRhJ5LEN8PAtnOiWTnu3oUyZnmiIpD_U");

        return true;
    }
}
