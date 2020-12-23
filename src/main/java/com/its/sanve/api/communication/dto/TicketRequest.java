package com.its.sanve.api.communication.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.its.sanve.api.entities.Ticket;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TicketRequest {
    @JsonProperty("companyId")
    String companyId;
    @JsonProperty("tripId")
    String tripId;
    @JsonProperty("platform")
    Integer platform;
    @JsonProperty("informationsBySeats")
    Ticket[] informationsBySeats;
}
