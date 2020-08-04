/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.its.sanve.api.repositories;

import com.its.sanve.api.entities.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author quangdt
 * @param <T>
 * @param <P>
 */
@Repository
public interface SeatRepository extends JpaRepository<Seat, String>{
    
}
