/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.its.sanve.api.exceptions.handlers;

import com.google.common.collect.Maps;
import com.its.sanve.api.dto.GwResponseDto;
import com.its.sanve.api.utils.MessageUtils;
import java.util.Map;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 *
 * @author quangdt
 */
public class CommonExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    MessageUtils messageUtil;

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity handleMaxUploadSizeExceededException(
            MaxUploadSizeExceededException ex) {
        logger.info(ex.getMessage(), ex);
        GwResponseDto dto = GwResponseDto.build().withHttpStatus(
                HttpStatus.BAD_REQUEST)
                .withCode(HttpStatus.BAD_REQUEST.value())
                .withMessage(messageUtil.getMessage(MessageUtils.FILE_TOO_LARGE));
        logger.info(dto);
        return dto.toResponseEntity();
    }

    @Override
    protected ResponseEntity handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        logger.error("argument invalid", ex);
        Map<String, String> errors = Maps.newHashMap();
        StringBuilder messageBuilder = new StringBuilder();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
            messageBuilder.append(error.getField()).append(": ").append(
                    error.getDefaultMessage()).append(".");
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.put(error.getObjectName(), error.getDefaultMessage());
            messageBuilder.append(error.getObjectName()).append(": ").append(
                    error.getDefaultMessage()).append(".");
        }
        return GwResponseDto.build().withHttpStatus(HttpStatus.BAD_REQUEST)
                .withCode(HttpStatus.BAD_REQUEST)
                .withMessage(messageBuilder.toString())
                .withErrors(errors).withHttpHeaders(headers).toResponseEntity();
    }

    @Override
    protected ResponseEntity handleBindException(BindException ex,
                                                 HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.error("Binding parameter fail", ex);
        Map<String, String> errors = Maps.newHashMap();
        StringBuilder messageBuilder = new StringBuilder();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
            messageBuilder.append(error.getField()).append(": ").append(
                    error.getDefaultMessage()).append(".");
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.put(error.getObjectName(), error.getDefaultMessage());
            messageBuilder.append(error.getObjectName()).append(": ").append(
                    error.getDefaultMessage()).append(".");
        }
        return GwResponseDto.build().withHttpStatus(HttpStatus.BAD_REQUEST)
                .withCode(HttpStatus.BAD_REQUEST)
                .withHttpHeaders(headers)
                .withMessage(messageBuilder.toString())
                .withErrors(errors).toResponseEntity();
    }

    @Override
    protected ResponseEntity handleTypeMismatch(TypeMismatchException ex,
                                                HttpHeaders headers, HttpStatus status, WebRequest request) {

        String error = ex.getValue() + " value for " + ex.getPropertyName() + " should be of type " + ex.
                getRequiredType();
        logger.error(error, ex);
        Map<String, String> errors = Maps.newHashMap();
        errors.put(ex.getPropertyName(), "Required instance of type " + ex.
                getRequiredType());
        return GwResponseDto.build().withHttpStatus(HttpStatus.BAD_REQUEST)
                .withCode(HttpStatus.BAD_REQUEST)
                .withMessage(error).withErrors(errors).withHttpHeaders(headers).
                        toResponseEntity();
    }

    @Override
    protected ResponseEntity handleMissingServletRequestPart(
            MissingServletRequestPartException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {

        String error = ex.getRequestPartName() + " part is missing";
        logger.error(error, ex);
        Map<String, String> errors = Maps.newHashMap();
        errors.put(ex.getRequestPartName(), "Missing partname");
        return GwResponseDto.build().withHttpStatus(HttpStatus.BAD_REQUEST)
                .withCode(HttpStatus.BAD_REQUEST).withErrors(errors).
                        withMessage(error).withHttpHeaders(headers).toResponseEntity();
    }

    @Override
    protected ResponseEntity handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        logger.error("Missing parameter " + ex.getParameterName(), ex);
        String error = ex.getParameterName() + " parameter is missing";
        Map<String, String> errors = Maps.newHashMap();
        errors.put(ex.getParameterName(), "Missing required value");
        return GwResponseDto.build().withHttpStatus(HttpStatus.BAD_REQUEST)
                .withCode(HttpStatus.BAD_REQUEST).withMessage(error).withErrors(
                        errors).withHttpHeaders(headers).toResponseEntity();
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity handleMethodArgumentTypeMismatch(
            MethodArgumentTypeMismatchException ex, WebRequest request) {
        logger.error("Argument Type Mismatch", ex);
        String error = ex.getName() + " should be of type " + ex.
                getRequiredType();
        Map<String, String> errors = Maps.newHashMap();
        errors.put(ex.getName(), "Type mismatch. Required " + ex.
                getRequiredType());
        return GwResponseDto.build().withHttpStatus(HttpStatus.BAD_REQUEST)
                .withCode(HttpStatus.BAD_REQUEST).withMessage(error)
                .withErrors(errors).toResponseEntity();
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity handleConstraintViolation(
            ConstraintViolationException ex, WebRequest request) {
        logger.error("Constrain Violation", ex);
        StringBuilder msgBuilder = new StringBuilder();
        Map<String, String> errors = Maps.newHashMap();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errors.put(violation.getRootBeanClass().getName() + " " + violation.
                    getPropertyPath(), violation.getMessage());
            msgBuilder.append(violation.getRootBeanClass().getName())
                    .append("").append(violation.getPropertyPath())
                    .append(": ").append(violation.getMessage()).append(
                    ".");
        }
        return GwResponseDto.build().withHttpStatus(HttpStatus.BAD_REQUEST)
                .withCode(HttpStatus.BAD_REQUEST).withMessage(msgBuilder.
                        toString())
                .withErrors(errors).toResponseEntity();
    }

    @Override
    protected ResponseEntity handleNoHandlerFoundException(
            NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status,
            WebRequest request) {
        logger.error("No handler found for " + ex.getHttpMethod() + " " + ex.
                getRequestURL(), ex);
        String message = "Invalid request. Please contact admin";
        Map<String, String> errors = Maps.newHashMap();
        errors.put("Request", "Invalid request");
        return GwResponseDto.build().withHttpStatus(HttpStatus.NOT_FOUND)
                .withCode(HttpStatus.NOT_FOUND).withErrors(errors).withErrors(
                        headers).withMessage(message).withHttpHeaders(headers).
                        toResponseEntity();
    }

    @Override
    protected ResponseEntity handleHttpRequestMethodNotSupported(
            HttpRequestMethodNotSupportedException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        logger.error("Not support " + ex.getMethod() + " method", ex);
        StringBuilder builder = new StringBuilder();
        builder.append(ex.getMethod());
        builder.append(
                " method is not supported for this request. Supported methods are ");
        builder.append(ex.getSupportedHttpMethods().stream().map(
                mediaType -> mediaType + " ").collect(Collectors.joining()));

        Map<String, String> errors = Maps.newHashMap();
        errors.put(ex.getMethod(), "method is not supported");

        return GwResponseDto.build().withHttpStatus(
                HttpStatus.METHOD_NOT_ALLOWED)
                .withCode(HttpStatus.METHOD_NOT_ALLOWED)
                .withMessage(builder.toString())
                .withErrors(errors)
                .withHttpHeaders(headers).toResponseEntity();
    }

    @Override
    protected ResponseEntity handleHttpMediaTypeNotSupported(
            HttpMediaTypeNotSupportedException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        logger.error("Not support media type: " + ex.getContentType(), ex);
        StringBuilder builder = new StringBuilder();
        builder.append(ex.getContentType());
        builder.append(
                " media type is not supported. Supported media types are ");
        builder.append(ex.getSupportedMediaTypes().stream().map(
                mediaType -> mediaType + " ").collect(Collectors.joining()));

        Map<String, String> errors = Maps.newHashMap();
        errors.put(ex.getContentType().getType(), "media type is not supported");

        return GwResponseDto.build().withHttpStatus(
                HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                .withCode(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                .withMessage(builder.toString())
                .withErrors(errors).withHttpHeaders(headers).toResponseEntity();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity handleAll(Exception ex, WebRequest request) {

        logger.error("error", ex);
        return GwResponseDto.build().withHttpStatus(
                HttpStatus.INTERNAL_SERVER_ERROR)
                .withCode(HttpStatus.INTERNAL_SERVER_ERROR).withMessage(
                        "System busy, please try again later or contact admin").
                        toResponseEntity();
    }
}
