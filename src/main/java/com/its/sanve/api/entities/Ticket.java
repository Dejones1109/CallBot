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
import javax.persistence.ManyToOne;
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
@JsonIgnoreProperties(ignoreUnknown = true)
public class Ticket {
    @Id
    @Column(name = "id")
    @JsonProperty("ticketId")
    String ticketId;
    @Column(name = "code")
    @JsonProperty("ticketCode")
    String code;
    /**
     * Thời gian khởi hành của vé, tính theo milisecond, múi giờ +0
     */
    @Column(name = "get_in_time")
    Long getInTimePlan;
    /**
     * Ngày khởi hành của vé, format yyyyMMdd
     */
    @Column(name = "get_int_time_date")
    Integer getInTimePlanInt;
    /**
     * Thời gian kết thúc của vé, tính theo milisecond, múi giờ +0
     */
    @Column(name = "get_off_time")
    Long getOffTimePlan;
    /**
     * Tên đầy đủ của khách hàng
     */
    @Column(name = "customer_name")
    String fullName;
    /**
     * Số đt khách hàng
     */
    @Column(name = "customer_phone")
    String phoneNumber;
    /**
     * Email khách 
     */
    @Column(name = "customer_email")
    String email;
    @Column(name = "status")
    @JsonProperty("ticketStatus")
    Integer status;
    /**
     * Thời gian hết hạn giữ chỗ, có giá trị khi ticketStatus=2, nếu quá thời gian này, vé bị đẩy khỏi sơ đồ ghế
     */
    @Column(name = "hold_time")
    Long overTime;
    @ManyToOne
    Seat seat;
    @Column(name = "adult")
    @JsonProperty("isAdult")
    Boolean adult;
    
    @ManyToOne
    Agency agency;
    /**
     * Giá tiền gốc của vé, chưa tính các khoản phụ thu, chính sách
     */
    @Column(name = "origin_price")
    Double originalTicketPrice;
    /**
     * Tiền đã thanh toán
     */
    @Column(name = "paid_money")
    Double paidMoney;
    /**
     * Giá thực thu về nhà xe, đã tính cả các chương trình giảm giá, khuyến mại
     */
    @Column(name = "agency_price")
    Double agencyPrice;
    /**
     * Điểm khách lên
     */
    @ManyToOne
    Point pointUp;
    /**
     * Điểm khách xuống
     */
    @ManyToOne
    Point pointDown;
    /**
     * Thông tin seller
     */
    @ManyToOne
    UserInfo seller;
}
