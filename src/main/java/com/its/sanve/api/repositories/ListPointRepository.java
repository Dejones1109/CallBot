/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.its.sanve.api.repositories;


import com.its.sanve.api.entities.ListPoint;
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
public interface ListPointRepository extends JpaRepository<ListPoint,String>{
   @Query(value="select new ListPoint(u.entityType,u.keyword,u.address,u.synonyms,u.status) from ListPoint u where u.id <= :limit")
   List<ListPoint> getPointAll(@Param("limit") Long limit);
   @Query(value="select new ListPoint(u.entityType,u.keyword,u.address,u.synonyms,u.status) from ListPoint u where u.companyId = :companyId")
   List<ListPoint> getPointByCompanyId( @Param("companyId") String companyId);
   @Query(value="select u.keyword from ListPoint u where u.pointId = :pointId")
   String getPointByPointId(@Param("pointId") String pointId);

}
