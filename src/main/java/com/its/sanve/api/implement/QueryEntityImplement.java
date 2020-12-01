package com.its.sanve.api.implement;

import com.its.sanve.api.entities.Route;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@Component
public  class QueryEntityImplement {
    @Autowired
    EntityManager entityManager;


    public List<Route> getRouteInfo(String startPoint, String endPoint) {
        try {
            Query query = entityManager.createNativeQuery(SQL_SELETE_ROUTES);
            query.setParameter("startPoint", startPoint);
            query.setParameter("endPoint", endPoint);
            List<Route> routeList = new ArrayList<>();
            List<Object[]> list = query.getResultList();
//            list.stream().forEach(x -> routeList.add((Route) x));
            log.info(" success query:{}", list);
            for(Object[] ob :list){
                Route  r = new Route();
                r.setRouteId(ob[0].toString());
                r.setStartPointId(ob[1].toString());
                r.setStartKeyword(ob[2].toString());
                r.setStartProvince(ob[3].toString());
                r.setEndPointId(ob[4].toString());
                r.setEndKeyword(ob[5].toString());
                r.setEndProvince(ob[6].toString());
                routeList.add(r);
            }
             log.info(routeList);
            return routeList;
        } catch (Exception e) {
            log.error("error query!!:{}", e);
            return null;
        }

    }

    static final String SQL_SELETE_ROUTES = "select tbStart.route_id,tbStart.point_id as start_point_id, tbStart.keyword as start_keyword, lower(tbStart.province) as start_province,tbEnd.point_id as end_point_id,tbEnd.keyword as end_keyword, lower(tbEnd.province) as end_province  from  (SELECT * FROM carticket.point c WHERE c.keyword = :startPoint or lower(c.province) =:startPoint) tbStart join (SELECT * FROM carticket.point c WHERE c.keyword = :endPoint or lower(c.province) =:endPoint) tbEnd on tbStart.route_id = tbEnd.route_id";
}
