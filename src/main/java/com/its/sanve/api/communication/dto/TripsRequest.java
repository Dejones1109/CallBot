package com.its.sanve.api.communication.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.ArrayList;

@Getter
@Setter
@ToString(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class TripsRequest {
    @JsonProperty("page")
    int page;
    @JsonProperty("count")
    int count;
    @JsonProperty("timeZone")
    int timeZone;
    @JsonProperty("sortSelections")
    ArrayList sortSelections;
    @JsonProperty("companyId")
    String companyId;
    @JsonProperty("date")
    String date;
    @JsonProperty("startPoint")
    String startPoint;
    @JsonProperty("endPoint")
    String endPoint;

    @JsonProperty("searchPointOption")
    int searchPointOption;
    @JsonProperty("source")
    int source;
    @JsonProperty("platform")
    int platform;
    @JsonProperty("newVersion")
    Boolean newVersion;
    @JsonProperty("routeId")
    String routeId;
    @JsonProperty("numberTicket")
    int numberTicket;
    @JsonProperty("startHour")
    String startHour;
}
