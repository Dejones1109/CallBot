/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.its.sanve.api.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author quangdt
 */
@Entity
@Table(name = "trips")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Trip {
    @Id
    @Column(name = "id")
    @JsonProperty("tripId")
    String id;
    @JsonProperty("routeId")
    String routeId;
    @Column(name = "status")
    @JsonProperty("tripStatus")
    Integer status;
    @Column(name = "allowPickingAndDroppingAtHome")
    @JsonProperty("allowPickingAndDroppingAtHome")
    Boolean allowPickingAndDroppingAtHome;
    @Column(name = "start_date_reality")
    String startDateReality;
    @Column(name = "start_time_reality")
    String startTimeReality;
    @Column(name = "run_time_reality")
    Integer runTimeReality;
    @Transient
    Vehicle vehicle;
    @Transient
    List<UserInfo> drivers;
    @Transient
    List<UserInfo> assistants;
    @ManyToOne
    RouteInfo routeInfo;
    @Transient
    SeatMap seatMap;
    @Column(name = "note")
    String note;
    @Transient
    List<Point> listPoint;
    @Transient
    CompanyInfo companyInfo;
    @Transient
    @JsonProperty("pointUp")
    Point pointUp;
    @Transient
    @JsonProperty("pointDown")
    Point pointDown;
}
