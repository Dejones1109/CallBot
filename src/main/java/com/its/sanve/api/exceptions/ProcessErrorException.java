/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.its.sanve.api.exceptions;

import org.springframework.http.HttpStatus;

/**
 *
 * @author quangdt
 */
public class ProcessErrorException extends absException{
    
    
    public ProcessErrorException(int code, String message) {
        super(HttpStatus.OK, code, message);
    }
    public ProcessErrorException(HttpStatus httpStatus, int code, String message) {
        super(httpStatus, code, message);
    }

    public ProcessErrorException(int code, String message, Throwable cause) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, code, message, cause);
    }

    public ProcessErrorException(int code, Throwable cause) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, code, cause);
    }

   
}
