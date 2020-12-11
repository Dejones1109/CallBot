package com.its.sanve.api.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter

@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class Ticket {
    @JsonProperty("fullName")
    String fullName;
    @JsonProperty("pointUp")
    Point pointUp;
    @JsonProperty("pointDown")
    Point pointDown;

}
