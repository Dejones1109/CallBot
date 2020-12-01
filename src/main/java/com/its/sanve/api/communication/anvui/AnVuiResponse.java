package com.its.sanve.api.communication.anvui;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AnVuiResponse<T>  {
    @JsonProperty("code")
    int code;
    @JsonProperty("count")
    int count;
    @JsonProperty("status")
    String status;
    @JsonProperty("type")
    String type;
    @JsonProperty("results")
    T results;
    @JsonProperty("tokenKey")
    String tokenKey;
}
