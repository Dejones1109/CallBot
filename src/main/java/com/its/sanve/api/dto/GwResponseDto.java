package com.its.sanve.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.annotation.PostConstruct;
import java.util.Map;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GwResponseDto<T> {

    @JsonIgnore
    private HttpStatus httpStatus;

    @JsonIgnore
    private HttpHeaders httpHeaders;

    private int code;
    private T data;

    @JsonIgnore
    private Map errors;

    private String message = "Success";

    public static <T> GwResponseDto<T> build() {
        return new GwResponseDto<>();
    }


    @PostConstruct
    private void init() {
        httpStatus = HttpStatus.OK;
        //code = httpStatus.value();
    }

    public GwResponseDto<T> withHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
        //this.code = httpStatus.value();
        return this;
    }
    
    public GwResponseDto<T> withData(T data) {
        this.data = data;
        return this;
    }

    public GwResponseDto<T> withCode(int code) {
        this.code = code;
        return this;
    }
    
    public GwResponseDto<T> withCode(HttpStatus httpStatus) {
        this.code = httpStatus.value();
        return this;
    }

    public GwResponseDto<T> withHttpHeaders(HttpHeaders httpHeaders) {
        this.httpHeaders = httpHeaders;
        return this;
    }

    public GwResponseDto<T> withMessage(String message) {
        this.message = message;
        return this;
    }

    public GwResponseDto<T> withErrors(Map errors) {
        this.errors = errors;
        return this;
    }

    public ResponseEntity<GwResponseDto> toResponseEntity() {
        return new ResponseEntity<>(this, httpHeaders, httpStatus);
    }
}
