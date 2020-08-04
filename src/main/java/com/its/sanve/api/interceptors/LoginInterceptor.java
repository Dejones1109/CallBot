/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.its.sanve.api.interceptors;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

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
//        long startTime = System.currentTimeMillis();
//        request.setAttribute(requestTime, startTime);
//        String sessionId = request.getSession().getId();
//        String clientId = request.getHeader(CLIENT_ID);
//        if(clientId == null || clientId.isEmpty()){
//            throw new  AuthenException(AUTHEN_FAIL, messageUtils.getMessage(
//                            MessageUtils.ERR_HEADER_002));
//        }
//        
//        if(!clientID.contains(clientId.toLowerCase())){
//            throw new  AuthenException(FORBIDDEN, messageUtils.getMessage(
//                            MessageUtils.ERR_HEADER_003));
//        }
//        
//        

        response.addHeader("X-RateLimit-Limit", String.valueOf(rateLimiter));

        return true;
    }
}
