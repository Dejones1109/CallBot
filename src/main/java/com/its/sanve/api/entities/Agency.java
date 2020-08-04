/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.its.sanve.api.entities;

import java.io.Serializable;
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
@Entity
@Table(name = "agency")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Agency implements Serializable {
    @Id
    String id;
    @Column(name = "full_name")
    String fullName;
    @Column(name = "commission_Type")
    Integer commissionType;
    @Column(name = "commission_Value")
    Integer commissionValue;
    @Column(name = "level")
    Integer level;
    @Column(name = "debt_limit")
    Integer debtLimit;
}
