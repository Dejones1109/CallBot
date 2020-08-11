/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.its.sanve.api.communication.SanVe.Order_Cal_Price.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.its.sanve.api.entities.Ticket;
import java.util.List;
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
@ToString
public class CalculatePriceResponse {
    // lấy danh sách vé của chuyến
    @JsonProperty("listTicket")
    List<Ticket> listTicket;
    Double total;
}
