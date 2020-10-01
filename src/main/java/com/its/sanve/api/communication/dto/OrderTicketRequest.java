/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.its.sanve.api.communication.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 *
 * @author quangdt
 */
@Getter
@Setter
@ToString(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderTicketRequest extends Request{
    // đặt vé
    @JsonProperty("seat_selected")
    String seat;
    @JsonProperty("point_selected")
    String point;
    @JsonProperty("trip_id")
    String tripId;
    @JsonProperty("route_id")
    String routeId;
    @JsonProperty("full_name")
    String customerFullname;
    @JsonProperty("phone")
    String customerPhone;
    @JsonProperty("gender")
    Integer customerGender;
    @JsonProperty("age")
    Integer customerAge;
    @JsonProperty("email")
    String customerEmail;
    @JsonProperty("company_id")
    String companyId;
    @JsonProperty("addr_point_up")
    String pointUp;
    @JsonProperty("addr_point_down")
    String pointDown;
    @JsonProperty("start_time_reality")
    String startTimeReality;
    @JsonProperty("start_date")
    String startDate;
    @JsonProperty("hot_line")
    String hotLine;
    @JsonProperty("call_id")
    String callId;
    @JsonProperty("intent")
    String intent;


}
