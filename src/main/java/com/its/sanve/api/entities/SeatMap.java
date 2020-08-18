/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.its.sanve.api.entities;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
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
public class SeatMap {
    @Id
    @Column(name = "id")
    String seatMapId;
    @Column(name = "seat_map_name")
    String seatMapName;
    @Column(name = "number_rows")
    int numberOfRows;
    @Column(name = "number_columns")
    int numberOfColumns;
    @Column(name = "number_floor")
    int numberOfFloors;
    @Transient
    List<Seat> seatList;
    
}
