package com.its.sanve.api.utils;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;

@Component
public class MessageUtils {

    @Autowired
    private MessageSource messageSource;

    public String getMessage(String key) {
        return this.getMessage(key, "");
    }

    public String getMessage(String key, Object... params) {
        List<String> paramStrs = new ArrayList();
        Object[] inputs = params;
        int length = params.length;

        for(int i = 0; i < length; ++i) {
            Object param = inputs[i];
            paramStrs.add(String.valueOf(param));
        }

        return this.messageSource.getMessage(key, paramStrs.toArray(), LocaleContextHolder.getLocale());
    }

    public String getMessage(FieldError field) {
        return this.messageSource.getMessage(field, LocaleContextHolder.getLocale());
    }
    public static final String REQUIRED = "REQUIRED";
    public static final String MONEY_INVALID = "MONEY_INVALID";
    public static final String COMP_INVALID = "COMPANY_INVALID";
    public static final String OUT_OF_RANGE = "OUT_OF_RANGE";
    public static final String ERR_HEADER_001 = "ERR_HEADER_001";
    public static final String ERR_HEADER_002 = "ERR_HEADER_002";
    public static final String ERR_HEADER_003 = "ERR_HEADER_003";
    public static final String ERR_UNAUTHORIZED = "ERR_UNAUTHORIZED";
    public static final String ERR_TIMEOUT = "ERR_TIMEOUT";
    public static final String ERR_REVOKED = "ERR_REVOKED";
    public static final String PARAM_MISSING = "PARAM_MISSING";
    public static final String TOKEN_EXPIRED = "TOKEN_EXPIRED";
    public static final String FILE_TOO_LARGE = "FILE_TOO_LARGE";
    public static final String LOGIN_FAIL = "LOGIN_FAIL";
    public static final String TOO_MANY_REQ = "TOO_MANY_REQ";

    
}
