package com.its.sanve.api.interceptors;

import com.its.sanve.api.exceptions.AuthenException;
import com.its.sanve.api.limititer.RequestRateLimiter;
import com.its.sanve.api.utils.Client;
import com.its.sanve.api.utils.ClientManager;
import com.its.sanve.api.utils.GwJwtTokenFactory;
import com.its.sanve.api.utils.MessageUtils;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PreDestroy;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;

import static org.springframework.http.HttpStatus.TOO_MANY_REQUESTS;

@Log4j2
@Component
public class GwInterceptor implements HandlerInterceptor {

    private static final int AUTHEN_FAIL =1 ;
    private static final int FORBIDDEN = 2;
    private static final int NON_AUTHEN_INFO =3 ;


    @Autowired
    private MessageUtils messageUtils;

    @Autowired
    private GwJwtTokenFactory jwtTokenFactory;

    public static final String requestTime = "requestTime";
    public static final String CLIENT_ID = "client_id";

    @Value("${rate.limiter}")
    private int rateLimiter;

    //    @Value("#{'${client.id}'.toLowerCase().split(',')}")
//    private List<String> clientID;
    @Autowired
    ClientManager clientMgr;


    private Map<String, RequestRateLimiter> limiters = new HashMap<>();

    @Autowired
    public GwInterceptor() {
    }

    @PostConstruct
    public void init(){
        //log.info("Client="+ clientMgr.getClients());
    }

    @PreDestroy
    public void onDestroy() {
//        if (limiters != null) {
//            for (Map.Entry<String, GwRateLimiter> entry : limiters.entrySet()) {
//                entry.getValue().release();
//            }
//        }
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler)
            throws Exception {
        log.info("===========================================================================");
        long startTime = System.currentTimeMillis();
        request.setAttribute(requestTime, startTime);
        String sessionId = request.getSession().getId();
        String clientId = request.getHeader(CLIENT_ID);
        if(clientId == null || clientId.isEmpty()){
            throw new  AuthenException(AUTHEN_FAIL, messageUtils.getMessage(
                    MessageUtils.ERR_HEADER_002));
        }
        Client client = clientMgr.getClient(clientId);
        if(client == null){
            throw new  AuthenException(FORBIDDEN, messageUtils.getMessage(
                    MessageUtils.ERR_HEADER_003));
        }

        String jwt = request.getHeader("x-access-token");
        if (Strings.isBlank(jwt)) {
            log.info("{} Missing token", sessionId);
            throw new AuthenException(NON_AUTHEN_INFO,
                    messageUtils.getMessage(MessageUtils.ERR_HEADER_001));
        }
        if (!jwtTokenFactory.validateJwt(client, jwt)) {
            log.info("{} Token invalid", sessionId);
            throw new  AuthenException(AUTHEN_FAIL, messageUtils.getMessage(
                    MessageUtils.ERR_UNAUTHORIZED));
        }
        RequestRateLimiter limiter = createLimiter(clientId);

        boolean allowRequest = limiter.tryAcquire();
        if (!allowRequest) {
            response.setStatus(TOO_MANY_REQUESTS.value());
//            throw new ProcessErrorException(TOO_MANY_REQUESTS,
//                    TOO_MANY_REQUESTS, messageUtils.getMessage(
//                    MessageUtils.TOO_MANY_REQ));
        }

        response.addHeader("X-RateLimit-Limit", String.valueOf(rateLimiter));
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, //
                           Object handler, ModelAndView modelAndView) throws Exception {

//        Log logContent = null;
//        try {
        log.info("end for request = {} , response = {} in {}ms",
                request.getSession().getId(), response.getStatus(),
                (System.currentTimeMillis() - (Long) request.getAttribute(
                        requestTime)));

    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, //
                                Object handler, Exception ex) throws Exception {
        log.info("Completed session {} at {}", request.getSession().getId(),
                new Date());

        String clientId = request.getHeader(CLIENT_ID);
        RequestRateLimiter limiter = getLimiter(clientId);
        if (null != limiter) {
            limiter.release();
        }
    }

    private RequestRateLimiter getLimiter(String clientId) {
        return limiters.get(clientId);
    }

    private RequestRateLimiter createLimiter(String clientId) {

        synchronized (clientId.intern()) {
            RequestRateLimiter limiter;
            limiter = limiters.get(clientId);
            if (limiter == null) {
                limiter = RequestRateLimiter.createLimiter(rateLimiter,
                        TimeUnit.SECONDS);
                limiters.put(clientId, limiter);
            }
            return limiter;
        }

    }
}
