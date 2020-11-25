package com.its.sanve.api.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString

@Table(name = "transhipment_point")
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TranshipmentPoint {
    @Id
    @Column(name = "tran_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long tranId;

    @Column(name = "point_of_route_id")
    String pointOfRouteId;

    @Column(name = "id")
    @JsonProperty("id")
    String id;

    @Column(name = "name")
    @JsonProperty("name")
    String name;
    @Column(name = "address")
    @JsonProperty("address")
    String address;
    @Column(name = "province")
    @JsonProperty("province")
    String province;

    @Column(name = "district")
    @JsonProperty("district")
    String district;

    @Column(name = "longitude")
    @JsonProperty("longitude")
    Double longitude;

    @Column(name = "latitude")
    @JsonProperty("latitude")
    Double latitude;

    @Column(name = "keyword")
    String keyword;
    @Column(name = "transshipment_price")
    @JsonProperty("transshipmentPrice")
    Double transshipmentPrice;
}
