package com.its.sanve.api.communication.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.its.sanve.api.entities.Point;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PriceTicketRequest {
    @JsonProperty("tripId")
    String tripId;
    @JsonProperty("numberOfTicket")
    Integer numberOfTicket;
    @JsonProperty("informationsBySeats")
    InformationSeat[] infoSeat;
}

class InformationSeat {
    @JsonProperty("seatId")
    String seatId;
    @JsonProperty("pointUp")
    Point pointUp;
    @JsonProperty("pointDown")
    Point pointDown;
}
