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

public class AuthenException extends absException{

    public AuthenException(int code, String message) {
        super(HttpStatus.OK, code, message);
    }

    public AuthenException(int code, String message, Throwable cause) {
        super(HttpStatus.OK, code, message, cause);
    }
    public AuthenException(int code, Throwable cause) {
        super(HttpStatus.OK, code, cause);
    }

}
