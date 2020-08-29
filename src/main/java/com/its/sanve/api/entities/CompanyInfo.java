/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.its.sanve.api.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class CompanyInfo {

    @Id
    @JsonProperty("companyId")
    String id;
    @Column(name = "company_name")
    @JsonProperty("companyName")
    String name;
    @Column(name = "phone")
    @JsonProperty("phoneNumber")
    String phoneNumber;
    //uy tín
    @Column(name = "reputation")
    @JsonProperty("reputation")
    Double reputation;
    @Column(name = "logo")
    @JsonProperty("companyLogo")
    String logo;
    @Column(name = "company_shortname")
    @JsonProperty("name_short")
    String nameShort;
}
