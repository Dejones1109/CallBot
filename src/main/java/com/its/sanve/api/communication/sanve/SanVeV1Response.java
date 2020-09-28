package com.its.sanve.api.communication.sanve;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SanVeV1Response<T> {

    @JsonProperty("messenger")
    String message;
    @JsonProperty("listTrip")
    T data;
}
