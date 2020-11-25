/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.its.sanve.api.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

/**
 * @author quangdt
 */
@Getter
@Setter
@ToString

@Table(name = "point")
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Point {

    @Id
    @Column(name = "point_of_route_id")
    @JsonProperty("pointOfRouteId")
    String pointOfRouteId;
    @Column(name = "point_id")
    @JsonProperty("id")
    String pointId;

    @Column(name = "route_id")
    @JsonProperty("routeId")
     String routeId;


    @Column(name = "name")
    @JsonProperty("name")
    String name;

    @Column(name = "address")
    @JsonProperty("address")
    String address;

    @Column(name = "province")
    @JsonProperty("province")
    String province;

    @Column(name = "district")
    @JsonProperty("district")
    String district;

    @Column(name = "keyword")
    @JsonProperty("keyword")

    String keyword;

    @Column(name = "longitude")
    @JsonProperty("longitude")
    Double longitude;

    @Column(name = "latitude")
    @JsonProperty("latitude")
    Double latitude;


    @JsonProperty("listTransshipmentPoint")
    @Transient
    List<TranshipmentPoint> transhipmentPoints;

}
