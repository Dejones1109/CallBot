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
import javax.persistence.Table;

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
@Table(name = "district")
@ToString
@Entity
public class District {
	public District() {
		super();
	}
	public District(String id, String provinceId, String name, String unitName) {
		super();
		this.id = id;
		this.provinceId = provinceId;
		this.name = name;
		this.unitName = unitName;
	}
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
