/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.its.sanve.api.repositories;


import com.its.sanve.api.entities.list_point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author quangdt
 * @param <T>
 * @param <P>
 */
@Repository
public interface PointV1Repository extends JpaRepository<list_point,String>{
   @Query(value="select u.* from list_point u where u.id <= :limit ",nativeQuery = true)
   List<list_point> getPointAll(int limit);
   List<list_point> findByCompanyId(String companyId);
}
