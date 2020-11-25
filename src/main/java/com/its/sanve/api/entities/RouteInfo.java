/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.its.sanve.api.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.util.List;

/**
 * @author quangdt
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "route_info")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RouteInfo {
    @Id
    @Column(name = "id")
    @JsonProperty("routeId")
    String id;

    @Column(name = "company_id")
    @JsonProperty("companyId")
    String companyId;

    @Column(name = "route_name")
    @JsonProperty("routeName")
    String name;

    @Column(name = "route_name_short")
    @JsonProperty("routeNameShort")
    String nameShort;

    @JsonProperty("displayPrice")
    @Column(name = "display_price")
    double displayPrice;

//    @JsonProperty("listPoint")
//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "routeInfo")
//    private List<ListPoint> listPoints = new ArrayList<>();
    @JsonProperty("listPoint")
    @Transient
    List<Point> listPoints;



}
