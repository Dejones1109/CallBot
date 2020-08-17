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
@Table(name = "company_info")
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CompanyInfo {
    public  CompanyInfo(String id){
        this.id = id;
    }
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
}
