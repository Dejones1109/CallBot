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
public class RouteRequest {
    @JsonProperty("companyId")
    String companyId;
    @JsonProperty("platform")
    int platform;
}
