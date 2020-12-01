package com.its.sanve.api.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)// convert from object to json
@JsonIgnoreProperties(ignoreUnknown = true)// convert from json to object
public class Trip {
    @Id
    @JsonProperty("tripId")
    String id;
    @JsonProperty("date")
    int date;
    @JsonProperty("startDateReality")
    String startDateReality;
    @JsonProperty("startTime")
    int startTime;
    @JsonProperty("startTimeReality")
    Double startTimeReality;
    @JsonProperty("price")
    Double price;
    @JsonProperty("totalSeat")
    int totalSeat;
    @JsonProperty("totalEmptySeat")
    int totalEmptySeat;
    @JsonProperty("pointUp")
    @Transient
    Point pointUp;
    @JsonProperty("pointDown")
    @Transient
    Point pointDown;
}
