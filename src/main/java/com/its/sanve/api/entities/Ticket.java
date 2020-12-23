package com.its.sanve.api.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter

@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)

public class Ticket {
    @JsonProperty("seatId")
    String seatId;

    @JsonProperty("seatType")
    Integer seatType;
    @JsonProperty("isAdult")
    Boolean isAdult;
    @JsonProperty("fullName")
    String fullName;
    @JsonProperty("birthday")
    String birthday;
    @JsonProperty("phoneNumber")
    String phoneNumber;
    @JsonProperty("email")
    String email;
    @JsonProperty("image")
    String image;
    @JsonProperty("note")
    String note;
    @JsonProperty("sendSMS")
    Boolean sendSMS;
    @JsonProperty("paymentType")
    Integer paymentType;
    @JsonProperty("foreignKey")
    String foreignKey;
    @JsonProperty("agencyPrice")
    Double agencyPrice;
    @JsonProperty("paidMoney")
    Double paidMoney;
    @JsonProperty("surcharges")
    Object[] surcharges;
    @JsonProperty("pointUp")
    Point pointUp;
    @JsonProperty("pointDown")
    Point pointDown;
    @JsonProperty("orderer")
    String order;
    @JsonProperty("startDate")
    String startDate;
    @JsonProperty("startTime")
    String startTime;
    @JsonProperty("ordererPhoneNumber")
    String orderPhoneNumber;
}
