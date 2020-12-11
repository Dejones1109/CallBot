package com.its.sanve.api.communication.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OldCustomerRequest {
    @JsonProperty("page")
    Integer page;
    @JsonProperty("count")
    Integer count;
    @JsonProperty("phoneNumber")
    String phoneNumber;
    @JsonProperty("routeId")
    String routeId;
}
