/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.its.sanve.api.communication.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author quangdt
 */
@Getter
@Setter
@ToString(callSuper = true)
@Builder
public class CalculatePriceRequest extends Request{
    
    @JsonProperty("seat_selected")
    String  seat;
    @JsonProperty("point_selected")
    String point;
    @JsonProperty("trip_id")
    String tripId;
    @JsonProperty("route_id")
    String routeId;
}
