/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.its.sanve.api.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class District {
    @Id
    @JsonProperty("districtId")
    String id;
    @Column(name = "province_id")
    @JsonProperty("provinceId")
    String provinceId;
    @Column(name = "district_name")
    @JsonProperty("districtName")
    String name;
    /**
     * Đơn vị (huyện, quận...)
     */
    @Column(name = "unit_name")
    @JsonProperty("unitName")
    String unitName;
}
