package com.its.sanve.api.facede;


import java.util.*;

import com.its.sanve.api.communication.sanve.SanVeClient;
import com.its.sanve.api.entities.*;
import com.its.sanve.api.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;


import com.its.sanve.api.communication.SanVeCommunicate;

import com.its.sanve.api.utils.MessageUtils;

import lombok.extern.log4j.Log4j2;


@Log4j2
@Component
public class GetDataFacade {

    @Autowired
    MessageUtils messageUtils;

    private SanVeCommunicate sanVeCommunicate;

    @Autowired(required = true)
    private CompanyRepository companyRepository;

    @Autowired
    private DistrictRepository districRepository;

    @Autowired
    private ProvinceRepository provinceRepository;

    @Autowired
    private RouteInfoRepository routeInfoRepository;
    @Autowired
    SanVeClient sanVeClient;
    @Autowired
    ListPointRepository listPointRepository;

// public

    public String getDataProvinceDistrict() {
        try {

            List<Province> listProvince = sanVeClient.getProvinceDistrict();
            provinceRepository.saveAll(listProvince);
            for (Province province : listProvince) {
                List<District> listDistrict = province.getListDistrict();
                districRepository.saveAll(listDistrict);
            }
            log.info("save database Province!");
            return "get list data province - district successfully!";

        } catch (Exception e) {

            log.error(e);
            return null;
        }


    }
    public  String getRouteInfo(String CompanyId){
        try {
          Map<String, RouteInfo> routeInfoMap = sanVeClient.getCompaniesRoutes(CompanyId);
          Set<String> keys = routeInfoMap.keySet();
          for(String key : keys){
              RouteInfo routeInfo = routeInfoMap.get(key);
              routeInfoRepository.save(routeInfo);

          }
            log.info("save database RouteInfo ");
             return "get list routeInfo successfully!";
        }catch (Exception e){
            log.error(e);
            return null;
        }
    }

    public Map<String, Object> getCompanyInfo(String company_phone) throws Exception {
        Map<String, Object> p = new HashMap<>();
        String data = companyRepository.findPhone(company_phone);
        log.info(data);
        String sub[] = data.split(",");
        p.put("companyId", sub[0]);
        p.put("companyName", sub[1]);
        p.put("companyShortName", sub[2]);
        return p;
    }
    public  List<ListPoint> getListAllRoute(int limit, String companyId)  {
        List<ListPoint> listPoints;

        log.debug("getListAllRoute");
        if(companyId.isEmpty()){
            listPoints = listPointRepository.getPointAll((long) limit);
        }else {
            listPoints = listPointRepository.getPointByCompanyId(companyId);
        }
        log.info("listPoints: {}",listPoints);
//        log.debug("list_points: {}",list_points);
//        for(list_point point : list_points){
//            log.debug("point:{}",point);
//            type.put("entityType","diem_len_xuong");
//            type.put("keyword", point.getDefaultName());
//            type.put("address",point.getAddress());
//            type.put("status",point.getStatus());
//        }

        return listPoints;
    }
    public  List<String> getListRoute(List<String> listPointId)  {
        List<String> listPoints = null;
        for(String pointId : listPointId){
            listPoints.add(listPointRepository.getPointByPointId(pointId));
        }
        return listPoints;
    }


}