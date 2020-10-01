/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.its.sanve.api.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 *
 * @author quangdt
 */
@Getter
@Setter
@ToString
@Table(name = "transactions_order_ticket")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderTicket {
    @Id
    @JsonProperty("id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "phone")
    @JsonProperty("phone")
    String phone;

    @Column(name = "call_Id")
    @JsonProperty("callId")
    String callId;

    @Column(name = "hotline")
    @JsonProperty("hotline")
    String hotLine;

    @Column(name = "intent")
    @JsonProperty("intent")
    String intent;

    @Column(name = "point_up")
    @JsonProperty("point_up")
    String pointUp;

    @Column(name = "point_down")
    @JsonProperty("point_down")
    String pointDown;

    @Column(name = "start_time_reality")
    @JsonProperty("start_time_reality")
    String startTimeReality;

    @Column(name = "start_date")
    @JsonProperty("start_date")
    String startDate;

    @Column(name = "created_at")
    @JsonProperty("created_at")
    LocalDateTime createdAt;

    @Column(name = "route_id")
    @JsonProperty("route_id")
    String routeId;

    @Column(name = "status")
    @JsonProperty("status")
    Integer status;

    @Column(name = "ticket")
    @JsonProperty("ticket")
    int ticket;

    @Column(name = "trips_id")
    @JsonProperty("trip_id")
    String tripId;

    @Column(name = "company_id")
    @JsonProperty("company_id")
    String companyId;

    @Column(name = "point_selected")
    @JsonProperty("point_selected")
    String pointSelected;


}
