/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.its.sanve.api.communication.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author quangdt
 */
@Getter
@Setter

@ToString(exclude = {"secretKey"})
public abstract class Request implements Serializable{
    @JsonProperty("api_key")
    String apiKey;
    @JsonProperty("secret_key")
    String secretKey;
}
