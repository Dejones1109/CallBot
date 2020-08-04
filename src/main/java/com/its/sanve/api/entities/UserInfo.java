/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.its.sanve.api.entities;

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
@Table(name = "user_info")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {

    @Id
    @Column(name = "id")
    String id;
    @Column(name = "username")
    String userName;
    @Column(name = "full_name")
    String fullName;
    @Column(name = "phone")
    String phoneNumber;
    @Column(name = "email")
    String email;
    @Column(name = "avatar")
    String avatar;
    @Column(name = "licence_level")
    String licenceLevel;
    @Column(name = "license_code")
    String licenceCode;
    @Column(name = "expired")
    Boolean licenceExpired;
    @Column(name = "birthday")
    String birthday;
}
