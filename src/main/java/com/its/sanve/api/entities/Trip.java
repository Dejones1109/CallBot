/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.its.sanve.api.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
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

public class Trip {
    @Id
    @Column(name = "id")
    @JsonProperty("tripId")
    String id;
    @Column(name = "status")
    @JsonProperty("tripStatus")
    Integer status;
    @Column(name = "start_date_reality")
    String startDateReality;
    @Column(name = "start_time_reality")
    Integer startTimeReality;
    @Column(name = "run_time_reality")
    Integer runTimeReality;
    @Transient
    Vehicle vehicle;
    @Transient
    List<UserInfo> drivers;
    @Transient
    List<UserInfo> assistants;
    @ManyToOne(targetEntity = Trip.class)
    RouteInfo routeInfo;
    @Transient
    SeatMap seatMap;
    @Column(name = "note")
    String note;
    @Transient
    List<Point> listPoint;
    @Transient
    CompanyInfo companyInfo;
    
    public Trip() {
		super();
	}
	public Trip(String id, Integer status, String startDateReality, Integer startTimeReality, Integer runTimeReality,
			String note) {
		super();
		this.id = id;
		this.status = status;
		this.startDateReality = startDateReality;
		this.startTimeReality = startTimeReality;
		this.runTimeReality = runTimeReality;
		this.note = note;
	}


	
}
