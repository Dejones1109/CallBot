package com.its.sanve.api.utils;


import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;
import java.util.Map;

@Log4j2
@Component
public class GwRequestBodyBuilder {


    public StringBuilder buildRequestBody(HttpServletRequest request) {
        StringBuilder requestBody = new StringBuilder();

        String body = "[]";

        // Building path variables
        String pathVariable = getPathVariable(request);

        requestBody.append("{body: ").append(body).append(", ");
        requestBody.append("pathVariables: ").append(pathVariable).append("}");

        return requestBody;
    }

    private String getPathVariable(HttpServletRequest request) {
        StringBuilder pathVariables = new StringBuilder();
        Map<String, String> pathVariablesMap = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        Iterator<?> it = pathVariablesMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> entry = (Map.Entry<String, String>) it.next();
            if (!Strings.isBlank(entry.getValue())) {
                continue;
            }

            if (Strings.isNotEmpty(pathVariables)) {
                pathVariables.append(", ");
            }
            pathVariables.append("{" + entry.getKey() + ": " + entry.getValue() + "}");
        }
        pathVariables.insert(0, "[");
        pathVariables.append("]");

        return !Strings.isBlank(pathVariables.toString()) ? pathVariables.toString() : "[]";
    }

}
