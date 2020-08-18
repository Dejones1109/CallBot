/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.its.sanve.api.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Date;

/**
 *
 * @author quangdt
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class TransactionLog {
    @Id
    @JsonProperty("id")
    String id;
    @Column(name = "point_up")
    @JsonProperty("point_up")
    String point_up;
    @Column(name = "point_down")
    @JsonProperty("point_down")
    String point_down;
    @Column(name = "start_time_reality")
    @JsonProperty("start_time_reality")
    String start_time_reality;
    @Column(name = "start_date")
    @JsonProperty("start_date")
    Date start_date;
    @Column(name = "route")
    @JsonProperty("route")
    String route;
    @Column(name = "status")
    @JsonProperty("status")
    Integer status;
}
