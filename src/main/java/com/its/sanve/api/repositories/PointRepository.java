/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.its.sanve.api.repositories;


import com.its.sanve.api.entities.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PointRepository extends JpaRepository<Point, String> {

//    @Query("SELECT c.pointId FROM Point c WHERE c.routeId = :routeId and c.province =:province")
//    List<String> listPointId(@Param("routeId") String routeId, @Param("province") String province);
//
//    @Query("SELECT c.province FROM Point c WHERE c.routeId = :routeId and c.province =:province")
//    List<String>  listProvince(@Param("routeId") String routeId,@Param("province") String province);

}
