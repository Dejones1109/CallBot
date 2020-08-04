/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.its.sanve.api.exceptions;

import lombok.ToString;
import org.springframework.http.HttpStatus;

import javax.print.DocFlavor;

/**
 *
 * @author quangdt
 */
@ToString
public abstract class absException extends RuntimeException{
    private final int code;
    final HttpStatus httpStatus;
    public absException(HttpStatus httpStatus, int code, String message) {
        super(message);
        this.code = code;
        this.httpStatus = httpStatus;
    }

    public absException(HttpStatus httpStatus, int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.httpStatus = httpStatus;
    }
    public absException(HttpStatus httpStatus, int code, Throwable cause) {
        super(cause);
        this.code = code;
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus(){
       if(null != httpStatus)
           return httpStatus;
       return HttpStatus.OK;
   }

    public int getCode() {
        return code;
    }
    
}
