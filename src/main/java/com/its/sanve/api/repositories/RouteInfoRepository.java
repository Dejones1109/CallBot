/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.its.sanve.api.repositories;

import com.its.sanve.api.entities.RouteInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * @author quangdt
 * @param <T>
 * @param <P>
 */
@Repository
public interface RouteInfoRepository extends JpaRepository<RouteInfo, String>{
    @Query("SELECT r.id FROM RouteInfo r WHERE r.name = :routeName")
    List<String> searchRouteName(@Param("routeName") String routeName);
}
