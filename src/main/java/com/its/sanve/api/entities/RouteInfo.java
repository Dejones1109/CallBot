/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.its.sanve.api.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class RouteInfo {
    @Id
    @Column(name = "id")
    @JsonProperty("routeId")
    String id;
    @Column(name = "company_id")
    String companyId;
    @Column(name = "route_name")
    @JsonProperty("routeName")
    String name;
    @Column(name = "route_name_short")
    @JsonProperty("routeNameShort")
    String nameShort;
    @Transient
    List<String> listImages;
    @Column(name = "children_ticket_ratio")
    Float childrenTicketRatio;
    @Column(name = "note")
    String note;
    @Transient
    List<Point> listPoint;


}
