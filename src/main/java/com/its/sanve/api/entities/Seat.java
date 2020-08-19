/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.its.sanve.api.entities;

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
@Entity
@Getter
@Setter
@ToString
@Table(name = "seat")
@NoArgsConstructor
@AllArgsConstructor
public class Seat {
    @Id
    @Column(name = "id")
    @JsonProperty("seatId")
    String id;
    @Column(name = "row")
    Integer row;
    @Column(name = "column")
    Integer column;
    @Column(name = "floor")
    Integer floor;
    @Column(name = "type")
    @JsonProperty("seatType")
    Integer type;
    @Transient
    List<String> images;
    @Column(name = "price")
    Integer price;
    @Column(name = "extra_price")
    Integer extraPrice;
    /**
     * Vé thêm,(Đặt vé được nhưng không nằm trên ghế)
     */
    public static final int VE_THEM = 0;
    /**
     * Cửa, không đăt được
     */
    public static final int WINDOWN = 1;
    /**
     * Ghế tài, không đặt được
     */
    public static final int DRIVER = 2;
    /**
     * Ghế thường, đặt được, khách ngồi đúng vị trí
     */
    public static final int NORMAL = 3;
    /**
     * Giường nằm, đặt được, khách ngồi đúng vị trí
     */
    public static final int NORMAL_2 = 4;
    /**
     * Nhà WC, không đặt
     */
    public static final int WC = 5;
    /**
     * Ghế phụ, không đặt
     */
    public static final int ASSISTANT = 6;
}
