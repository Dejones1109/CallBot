//package com.its.sanve.api.facede;
//
//
//import java.util.ArrayList;
//        import java.util.HashMap;
//        import java.util.List;
//        import java.util.Map;
//
//        import org.springframework.beans.factory.annotation.Autowired;
//        import org.springframework.beans.factory.annotation.Value;
//        import org.springframework.stereotype.Component;
//
//
//        import com.fasterxml.jackson.databind.JsonNode;
//        import com.fasterxml.jackson.databind.ObjectMapper;
//        import com.its.sanve.api.communication.SanVeCommunicate;
//        import com.its.sanve.api.communication.sanve.SanveClient;
//        import com.its.sanve.api.communication.sanve.SanveResponse;
//        import com.its.sanve.api.repositories.CompanyRepository;
//        import com.its.sanve.api.repositories.DistrictRepository;
//        import com.its.sanve.api.repositories.ProvinceRepository;
//        import com.its.sanve.api.repositories.RouteInfoRepository;
//        import com.its.sanve.api.utils.MessageUtils;
//
//        import lombok.extern.log4j.Log4j2;
//
//        import com.its.sanve.api.entities.*;
//
//@Log4j2
//@Component
//public class GetDataFacade {
//    @Value(value = "${SV.apiKey}")
//    String apiKey;
//    @Value(value = "${SV.secretKey}")
//    String SecretKey;
//    @Value("${sanve.endpoint}")
//    private String baseUrl;
//
//    @Autowired
//    private SanveClient sanVeClient;
//
//    @Autowired
//    MessageUtils messageUtils;
//
//    private SanVeCommunicate sanVeCommunicate;
//
//    @Autowired(required = true)
//    private CompanyRepository companyRepository;
//
//    @Autowired
//    private DistrictRepository districRepository;
//
//    @Autowired
//    private ProvinceRepository provinceRepository;
//
//    @Autowired
//    private RouteInfoRepository routeInfoRepository;
//
//    @Autowired
//    private ObjectMapper objectMapper = new ObjectMapper();
//
//    @Autowired
//    private SanveClient sanveClient;
//// public
//
////    public Object getDataProvinceDistrict() throws Exception {
//////        SanveResponse data = (SanveResponse) sanveClient.getProvinceDistrict();
//////        log.info("1");
//////        List<District> listDistrict = new ArrayList<>();
//////        List<Province> listProvince = new ArrayList<>();
//////        String string = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(data.getData());
//////        log.info("2");
//////        log.info(string);
//////        JsonNode jsonProvinces = objectMapper.readTree(string);
//////        log.info("3");
//////        log.info(jsonProvinces);
//////        for (JsonNode provinces : jsonProvinces) {
//////            listProvince.add(new Province(provinces.get("id").asText(), provinces.get("provinceName").asText(), provinces.get("unitName").asText()));
//////            log.info("2");
//////            log.info(listProvince);
//////            JsonNode jsonDistrict = provinces.get("listDistrict");
//////            for (JsonNode districts : jsonDistrict) {
//////                listDistrict
//////                        .add(new District(districts.get("districtId").asText(), districts.get("provinceId").asText(),
//////                                districts.get("districtName").asText(), districts.get("unitName").asText()));
//////            }
//////        }
//////        provinceRepository.saveAll(listProvince);
//////        districRepository.saveAll(listDistrict);
//////
//////        return data;
////    }
//
//    public Object getDataCompanies() throws Exception {
//        SanveResponse data = (SanveResponse) sanveClient.getCompanies();
//        List<CompanyInfo> companyInfos = new ArrayList<>();
//
//// CompanyInfo companyInfo = new CompanyInfo(id, name, phoneNumber, reputation,
//// logo);
//        String string = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(data.getData());
//// log.info(string1);
//        JsonNode jsonNodeCompanies = objectMapper.readTree(string);
//
//        for (JsonNode obj : jsonNodeCompanies) {
//// companyInfo.setId(obj.get("companyId").asText());
//            companyInfos.add(new CompanyInfo(obj.get("companyId").asText(), obj.get("companyName").asText(), obj.get("telecomPhoneNumber").asText(), obj.get("reputation").asDouble(), obj.get("companyLogo").asText()));
//// log.info("10");
//
//        }
//// log.info("6");
//// log.info(companyInfos);
//        companyRepository.saveAll(companyInfos);
//
//// log.info("7");
//        return data;
//    }
//
//    public Object getDataRoutes(String companyId) throws Exception {
//        SanveResponse<Map<String,RouteInfo>> data = (SanveResponse<Map<String, RouteInfo>>) sanveClient.getCompaniesRoutes(companyId);
////        String string = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(data.getData());
////// log.info(string1);
////        JsonNode jsonNodeRoute = objectMapper.readTree(string);
////        List<RouteInfo> listRouteInfor = new ArrayList<>();
////        for (JsonNode obj : jsonNodeRoute) {
////// listTrip.add(new Trip(obj.get(index), status, startDateReality,
////// startTimeReality, runTimeReality, note));
////            listRouteInfor.add(new RouteInfo(obj.get("routeId").asText(), obj.get("companyId").asText(),
////                    obj.get("routeName").asText(), obj.get("routeNameShort").asText(),
////                    obj.get("childrenTicketRatio").asDouble(), obj.get("note").asText()));
////
////        }
//       // routeInfoRepository.saveAll(data.getData());
//        return data;
//    }
//
//    public Object getInfoCompany(String company_phone) throws Exception {
//        Map<String, Object> p = new HashMap<>();
//
//         String data = companyRepository.findPhone(company_phone);
//         log.info(data);
////        log.info(data);
//       String sub[] =  data.split(",");
////        for(int i=0;i<data.length;i++){
//            p.put("companyId",sub[0]);
//            p.put("companyName",sub[1]);
//            p.put("companyShortName",sub[2]);
////        }
//
//        return p;
//    }
//
//}