/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.its.sanve.api.repositories;


import com.its.sanve.api.entities.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @param <T>
 * @param <P>
 * @author quangdt
 */
@Repository
public interface PointRepository extends JpaRepository<Point, String> {

    @Query("SELECT c FROM Point c WHERE c.keyword = :point or c.province =:point group by c.routeId")
    List<Point> searchPoint(@Param("point") String point);
//    select tbStart.route_id, tbStart.keyword as start_keyword, tbStart.province as start_province,
//    tbEnd.keyword as start_keyword, tbEnd.province as start_province
//    from
//            (SELECT * FROM carticket.point c WHERE c.keyword = "thái bình" or lower(c.province) ='thái bình') tbStart
//    join
//            (SELECT * FROM carticket.point c WHERE c.keyword = "hà nội" or lower(c.province) ='hà nội') tbEnd
//    on tbStart.route_id = tbEnd.route_id


}
