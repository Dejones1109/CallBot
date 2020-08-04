package com.its.sanve.api.communication.SanVe;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SanveResponse<T> {
    @JsonProperty("status")
    int status;
    @JsonProperty("message")
    String message;
    @JsonProperty("data")
    T data;
}
