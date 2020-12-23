package com.its.sanve.api.entities;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class CustomerOrder {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @Column(name = "trip_id")
    String tripId;
    @Column(name = "company_id")
    String companyId;
    @Column(name = "name")
    String name;
    @Column(name = "phone")
    String phone;
    @Column(name = "phone_order")
    String phoneOrder;
    @Column(name = "start_point_id")
    String startPointId;
    @Column(name = "end_point_id")
    String endPointId;
    @Column(name = "ticket")
    int ticket;
    @Column(name = "start_date")
    String startDate;
    @Column(name = "start_time")
    String startTime;
    @Column(name = "status")
    int status;
    @Column(name = "create_date")
    String createDate;
}
