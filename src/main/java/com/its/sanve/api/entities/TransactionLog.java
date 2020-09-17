/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.its.sanve.api.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.SortedSet;

/**
 *
 * @author quangdt
 */
@Getter
@Setter
@ToString
@Table(name = "transactions")
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class TransactionLog {
    @Id
    @JsonProperty("id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "phone")
    @JsonProperty("phone")
    String phone;

    @Column(name = "phone_order")
    @JsonProperty("phone_order")
    String phoneOrder;

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

    @Column(name = "route")
    @JsonProperty("route")
    String route;

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

    public TransactionLog(String phone, String phoneOrder, String callId, String hotLine, String intent, String pointUp, String pointDown, String startTimeReality, String startDate, LocalDateTime now, String route, Integer status) {
    }
}
