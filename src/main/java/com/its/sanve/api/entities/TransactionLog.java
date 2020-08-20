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
    String Phone_order;
    @Column(name = "call_ID")
    @JsonProperty("call_ID")
    String Call_ID;
    @Column(name = "hotline")
    @JsonProperty("hotline")
    String Hotline;
    @Column(name = "intent")
    @JsonProperty("intent")
    String Intent;
    @Column(name = "point_up")
    @JsonProperty("point_up")
    String Point_up;
    @Column(name = "point_down")
    @JsonProperty("point_down")
    String Point_down;
    @Column(name = "start_time_reality")
    @JsonProperty("start_time_reality")
    String Start_time_reality;
    @Column(name = "start_date")
    @JsonProperty("start_date")
    Date Start_date;
    @Column(name = "created_at")
    @JsonProperty("created_at")
    LocalDateTime Created_at;
    @Column(name = "route")
    @JsonProperty("route")
    String Route;
    @Column(name = "status")
    @JsonProperty("status")
    Integer Status;
}
