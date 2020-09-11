/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.its.sanve.api.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "list_point")
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class list_point {

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
    @Column(name = "default_name")
    String defaultName;



}
