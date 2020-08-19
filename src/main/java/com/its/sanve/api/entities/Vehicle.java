/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.its.sanve.api.entities;

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
@Entity
@Table(name = "vehicle")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Vehicle {
    @Id
    @Column(name = "id")
    String id;
    @Column(name = "number_plate")
    String numberPlate;
    @Column(name = "type")
    int vehicleType;
    @ElementCollection(targetClass=String.class)
    @Column(name = "images")
    List<String> listImage;
    
    public static final int GIUONG_NAM =0;
    public static final int GHE_NGOI = 1;
    public static final int LIMOUSINE_GIUONG_NAM = 2;
    public static final int LIMOUSINE_GHE_NGOI = 3;
    public static final int XE_PHONG = 4;
}
