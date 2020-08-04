package com.its.sanve.api.aop;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Date;
import java.util.Enumeration;
//import com.its.payment.gateway.utils.DateUtils;
import org.apache.logging.log4j.util.Strings;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;
import java.util.Map;

@Aspect
public class MonitorAspect {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private ObjectMapper mapper;

    /**
     * Pointcut that matches all repositories, services and Web REST endpoints.
     */
    @Pointcut("within(@org.springframework.stereotype.Repository *)"
            + " || within(@org.springframework.stereotype.Service *)"
            + " || within(@org.springframework.web.bind.annotation.RestController *)")
    public void springBeanPointcut() {
        // Method is empty as this is just a Pointcut, the implementations are in the advices.
    }

    /**
     * Pointcut that matches all Spring beans in the application's main packages.
     */
    @Pointcut("within(com.its.callbot.controller.*..*)")
    public void applicationPackagePointcut() {
        // Method is empty as this is just a Pointcut, the implementations are in the advices.
    }

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void applicationControllerPointcut() {
        // Method is empty as this is just a Pointcut, the implementations are in the advices.
    }

    /**
     * Pointcut that matches all Web REST endpoints.
     */
    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void controllerPointcut() {
        // Method is empty as this is just a Pointcut, the implementations are in the advices.
    }

    /**
     * @param joinPoint
     * @param requestBody
     * @return 
     * @throws Throwable
     * Advice that logs all incoming request and response
     */
    @Around("controllerPointcut() && args(@RequestBody requestBody)")
    public Object logAllIncomingRequest(ProceedingJoinPoint joinPoint, Object requestBody) throws Throwable {
        Logger logger = getLogger(joinPoint);
        // Logs request information
        logger.info(showRequest(requestBody, request));

        // Logs response information
        Object result = joinPoint.proceed();

        if (result instanceof ResponseEntity) {
            ResponseEntity<?> responseEntity = (ResponseEntity<?>) result;
            logger.info(showResponse(requestBody, responseEntity.getBody(), responseEntity.getStatusCode().toString()));
        }

        return result;
    }
    
    
    @Around("controllerPointcut() && args(@RequestBody requestBody, @RequestParam page, @RequestParam pageSize)")
    public Object logAllIncomingPagableRequest(ProceedingJoinPoint joinPoint, Object requestBody, Integer page, Integer pageSize) throws Throwable {
        Logger logger = getLogger(joinPoint);
        // Logs request information
        logger.info(showRequest(requestBody, request, page, pageSize));

        // Logs response information
        Object result = joinPoint.proceed();

        if (result instanceof ResponseEntity) {
            ResponseEntity<?> responseEntity = (ResponseEntity<?>) result;
            logger.info(showResponse(requestBody, responseEntity.getBody(), responseEntity.getStatusCode().toString()));
        }

        return result;
    }

    /**
     * Advice that logs methods throwing exceptions.
     *
     * @param joinPoint join point for advice
     * @param e         exception
     */
    @AfterThrowing(pointcut = "applicationPackagePointcut() && springBeanPointcut()", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) throws JsonProcessingException {
        Logger logger = getLogger(joinPoint);
        String statusCode = HttpStatus.BAD_REQUEST.toString();
        logger.info("Session {} statusCode = {} cause: ", request.getSession().getId(), statusCode, e);
    }
    
    
    

    /**
     * Building request informations
     *
     * @return
     */
    private String showRequest(Object body, HttpServletRequest request, int... pageing) {
        // Building request body
        StringBuilder requestBody = buildRequestBody(body);
        
        Enumeration<String> headers = request.getHeaderNames();
        StringBuilder logBuilder = new StringBuilder();
        while (headers.hasMoreElements()) {
            String headerName = headers.nextElement();
            logBuilder.append(headerName).append(":")
                    .append(request.getHeader(headerName)).append(";");
        }
        
        // Building response
        String template = String.format("Session %s request: {timestamp: %s - ", request.getSession().getId(), new Date().getTime()) +
                String.format("requestUrl: \"%s %s\" - ", request.getMethod(), request.getRequestURI()) +
                String.format("requestHeader:\"%s\"", logBuilder) +
                String.format("requestBody: %s", requestBody.toString());
        if(pageing != null && pageing.length ==2){       
              template +=  String.format("page: %s, pageSize: %s", pageing[0], pageing[1]);
        }
        return escapeHTML(template);
    }

    /**
     * Building response informations
     *
     * @param response
     * @param statusCode
     * @return String
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws JsonProcessingException
     */
    private String showResponse(Object requestBody, Object response, String statusCode) {
        // Building request body
        StringBuilder requestBodySdb = buildRequestBody(requestBody);

        String body = "";
        if (response != null) {
            if (response instanceof ByteArrayResource) {
                body = "{}";
            } else {
                try {
                    body = mapper.writeValueAsString(response);
                } catch (JsonProcessingException e) {
                }
            }
        } else {
            body = "{}";
        }

        // Building response
        StringBuilder template = new StringBuilder();
        template.append(String.format("requestUrl: \"%s %s\" - ", request.getMethod(), request.getRequestURI()));
        template.append(String.format("requestBody: %s - ", requestBodySdb.toString()));
        template.append(String.format("response: %s - ", body));
        template.append(String.format("status: %s}", statusCode));

        return escapeHTML(template.toString());
    }

    /**
     * Escape HTML characters
     *
     * @param template
     * @return String
     */
    private String escapeHTML(String template) {
        String data = template.replace("\"", "");
        data = data.replace("\\", "\\\\");
        data = data.replace("\b", "\\b");
        data = data.replace("\f", "\\f");
        data = data.replace("\r", "\\r");
        data = data.replace("\n", "\\n");
        data = data.replace("\t", "");

        return data;
    }

    private String getPathVariable() {
        StringBuilder pathVariables = new StringBuilder();
        Map<String, String> pathVariablesMap =
                (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        Iterator<?> it = pathVariablesMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> entry = (Map.Entry<String, String>) it.next();
            if (!Strings.isBlank(entry.getValue())) {
                continue;
            }

            if (Strings.isNotEmpty(pathVariables)) {
                pathVariables.append(", ");
            }
            pathVariables.append("{").append(entry.getKey()).append(": ").
                    append(entry.getValue()).append("}");
        }
        pathVariables.insert(0, "[");
        pathVariables.append("]");

        return !Strings.isBlank(pathVariables.toString()) ? pathVariables.toString() : "[]";
    }

    private StringBuilder buildRequestBody(Object body) {
        StringBuilder requestBody = new StringBuilder();

//        String body = "[]";

        // Building path variables
        String pathVariable = getPathVariable();

        requestBody.append("{body: ").append(body).append(", ");
        requestBody.append("pathVariables: ").append(pathVariable).append("}");

        return requestBody;
    }


    private Logger getLogger(JoinPoint joinPoint) {
        return LoggerFactory.getLogger(joinPoint.getTarget().getClass().getName());
    }
}
