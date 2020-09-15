/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.its.sanve.api.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.*;
import java.util.List;

/**
 *
 * @author quangdt
 */
@Getter
@Setter
@ToString


@Table(name = "list_point")
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ListPoint {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "point_id")
    String pointId;

    @Column(name = "company_id")
    String companyId;

    @Column(name = "address")
    String address;

    @Column(name = "province")
    String province;

    @Column(name = "name")
    String name;

    @Column(name = "point_type")
    String pointType;
    @Column(name = "keyword")
    String keyword;
    @Column(name = "status")
    String status;
    @Column(name="entity_type")
    String entityType;
    public ListPoint( String entityType, String keyword, String address, String status){
        this.keyword =keyword;
        this.entityType = entityType;
        this.address = address;
        this.status = status;
    }
}
