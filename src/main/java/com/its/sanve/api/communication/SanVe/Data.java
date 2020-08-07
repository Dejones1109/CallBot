package com.its.sanve.api.communication.SanVe;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.its.sanve.api.entities.Province;
import com.its.sanve.api.entities.Trip;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class Data {

    List<Trip> trips;

    public List<Trip> getProvince() {
        return trips;
    }

    public void setProvince( List<Trip>  trips) {
        this.trips = trips;
    }
}
