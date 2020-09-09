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
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class PointV1 {

    @Id
    @Column(name = "id")
    String id;
    /**
     * Tên điểm dừng
     */
    @Column(name = "name")
    String name;
    /**
     * Địa chỉ điểm dừng
     */
    @Column(name = "address")
    String address;
    /**
     * Tên tỉnh
     */
    @Column(name = "province")
    String province;
    /**
     * Tên huyện
     */
    @Column(name = "district")
    String district;
    
    /**
     * Kinh độ
     */
    @Column(name = "longtitude")
    Double longitude;
    /**
     * Vĩ độ
     */
    @Column(name = "latitude")
    Double latitude;
    /**
     * Tên viết tắt
     */
    @Column(name = "point_name_acronym")
    String pointNameAcronym;
    /**
     * Thời gian đi từ điểm đầu tiên của tuyến đến điểm đang xét (tình theo milisecond)
     */
    @Column(name = "time_intend")
    Integer timeIntend;
    /**
     * Danh sách giá, trong đó giá trị phần tử thứ i là giá từ điểm điểm đang xét đến điểm thứ i.
     * Giá = -1 nghĩa là giữa 2 điểm đó không bán vé
     */
    @ElementCollection(targetClass=Double.class)
    List<Double> listPrice;
    /**
     * Cho phép đưa đón tận nhà tại diểm đang xét
     */
    @Column(name = "allow_pickup_home")
    Boolean allowPickingAndDroppingAtHome;
    @Transient
    List listTransshipmentPoint;
    @Transient
    Object transshipmentId;
    @Column(name ="type")
    Integer pointType;
    @Transient
    Object transshipmentPrice;
    @Transient
    Object transshipmentDriver;
    @Transient
    Object pickUpDropOffType;
    @Transient
    Boolean completedTransshipment;
    @Transient
    Object orderTransshipment;
    @Transient
    Boolean haveTransshipment;
}
