package com.its.sanve.api.entities;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Route {
    String routeId;
    String startPointId;
    String startKeyword;
    String startProvince;
    String endPointId;
    String endKeyword;
    String endProvince;
}
