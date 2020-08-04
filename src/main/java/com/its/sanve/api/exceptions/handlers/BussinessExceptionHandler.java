/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.its.sanve.api.exceptions.handlers;

import com.its.sanve.api.dto.GwResponseDto;
import com.its.sanve.api.exceptions.AuthenException;
//import com.its.sanve.api.exceptions.GWException;
import com.its.sanve.api.exceptions.ProcessErrorException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 *
 * @author quangdt
 */

@RestControllerAdvice
public class BussinessExceptionHandler extends CommonExceptionHandler {


    @ExceptionHandler(AuthenException.class)
    public ResponseEntity handleException(AuthenException ex) {
        logger.info(ex.getMessage());
        return GwResponseDto.build().withHttpStatus(ex.getHttpStatus())
                .withCode(ex.getCode()).withMessage(ex.getMessage()).toResponseEntity();
    }

    @ExceptionHandler(ProcessErrorException.class)
    public ResponseEntity handleException(ProcessErrorException ex) {
        logger.info(ex.getMessage());
        return GwResponseDto.build().withHttpStatus(ex.getHttpStatus())
                .withCode(ex.getCode()).withMessage(ex.getMessage()).toResponseEntity();
    }

}
