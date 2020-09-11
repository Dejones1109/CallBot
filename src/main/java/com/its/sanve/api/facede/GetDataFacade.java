package com.its.sanve.api.facede;


import java.util.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
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
    PointV1Repository pointV1Repository;

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
    public  List<list_point> getListAllRoute(int limit,String companyId) throws JsonProcessingException {
        List<list_point> list_points = new ArrayList<>();
        if(companyId.isEmpty()){
            list_points = pointV1Repository.getPointAll(limit);
        }else {
            list_points = pointV1Repository.findByCompanyId(companyId);
        }

    

        return list_points;
    }

}