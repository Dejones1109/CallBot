/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.its.sanve.api.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Getter
@Setter
@Table(name = "province")
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
public class Province {
	
	public Province() {
		super();
	}
	public Province(String id, String name, String unit) {
		super();
		this.id = id;
		this.name = name;
		this.unit = unit;
	}
    @Id
    @Column(name = "id")
    String id;
	@Column(name = "province_name")
    @JsonProperty("provinceName")
    String name;
    @Column(name = "unit_name")
    @JsonProperty("unitName")
    String unit;
    @Transient
    List<District> listDistrict;
}
