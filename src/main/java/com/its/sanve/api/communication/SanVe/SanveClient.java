package com.its.sanve.api.communication.SanVe;

import com.fasterxml.jackson.core.JsonProcessingException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.its.sanve.api.communication.AbstractCommunication;
import com.its.sanve.api.communication.SanVeCommunicate;

import com.its.sanve.api.communication.SanVe.Order_Cal_Price.dto.CalculatePriceRequest;
import com.its.sanve.api.communication.SanVe.Order_Cal_Price.dto.CalculatePriceResponse;
import com.its.sanve.api.entities.CompanyInfo;
import com.its.sanve.api.entities.District;
import com.its.sanve.api.entities.Province;
import com.its.sanve.api.entities.RouteInfo;
import com.its.sanve.api.entities.Trip;
import com.its.sanve.api.repositories.CompanyRepository;
import com.its.sanve.api.repositories.DistrictRepository;
import com.its.sanve.api.repositories.ProvinceRepository;
import com.its.sanve.api.repositories.RouteInfoRepository;
import com.its.sanve.api.utils.MessageUtils;

import lombok.extern.log4j.Log4j2;

import org.apache.logging.log4j.Logger;
import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import javax.annotation.PostConstruct;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Log4j2
@Component
@Service
public class SanveClient extends AbstractCommunication {
	// Logger log = (Logger) LoggerFactory.logger(SanveClient.class);
	@Value(value = "${SV.apiKey}")
	String apiKey;
	@Value(value = "${SV.secretKey}")
	String SecretKey;
	@Value("${sanve.endpoint}")
	private String baseUrl;

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
	private ObjectMapper objectMapper = new ObjectMapper();

	@PostConstruct
	public void intConnection() {
		this.sanVeCommunicate = buildSetting();
	}

	private SanVeCommunicate buildSetting() {

		Retrofit retrofit = new Retrofit.Builder().baseUrl(baseUrl)
				.addConverterFactory(JacksonConverterFactory.create()).client(this.buildCommunication()).build();

		return retrofit.create(SanVeCommunicate.class);
	}

	public Object getProvinceDistrict() throws Exception {

		SanveResponse data = null;
		Call<SanveResponse> request = null;
		try {
			log.info("1");
			long time1 = System.currentTimeMillis();
			request = sanVeCommunicate.getProvinceDistrict(apiKey.trim(), SecretKey.trim());

			Response<SanveResponse> response = request.execute();
			long time2 = System.currentTimeMillis();

			log.info("3");
			if (response.isSuccessful()) {
				log.info("4");
				data = response.body();
				log.info(time2 - time1);
				log.info(data);
				// System.out.println(data.getData());
				log.info("5");
				List<District> listDistrict = new ArrayList<>();
				List<Province> listProvinces = new ArrayList<>();

				String string1 = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(data.getData());
				log.info(string1);
				JsonNode jsonProvinces = objectMapper.readTree(string1);
				log.info(jsonProvinces);

				for (JsonNode provinces : jsonProvinces) {
					// listDistrict.add(new District(obj.get("districtId").asText(),
					// obj.get("provinceId").asText(), obj.get("districtName").asText(),
					// obj.get("unitName").asText()));

					listProvinces.add(new Province(provinces.get("id").asText(), provinces.get("provinceName").asText(),
							provinces.get("unitName").asText()));
					JsonNode jsonDistrict = provinces.get("listDistrict");
					for (JsonNode districts : jsonDistrict) {
						listDistrict.add(
								new District(districts.get("districtId").asText(), districts.get("provinceId").asText(),
										districts.get("districtName").asText(), districts.get("unitName").asText()));
					}
					log.info(listProvinces);
					log.info("10");

				}
				provinceRepository.saveAll(listProvinces);
				districRepository.saveAll(listDistrict);

			} else {
				log.info("jambalaya");
			}

		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}

		return data;
	}

	public Object getCompanies() throws Exception {

		SanveResponse data = null;
		Call<SanveResponse> request = null;
		try {
			log.info("");
			request = sanVeCommunicate.getCompanies(apiKey.trim(), SecretKey.trim());

			Response<SanveResponse> response = request.execute();
			log.info("3");
			if (response.isSuccessful()) {
				log.info("4");
				data = response.body();
				log.info("5");
				List<CompanyInfo> companyInfos = new ArrayList<>();

				// CompanyInfo companyInfo = new CompanyInfo(id, name, phoneNumber, reputation,
				// logo);
				String string1 = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(data.getData());
				log.info(string1);
				JsonNode jsonNode = objectMapper.readTree(string1);

				for (JsonNode obj : jsonNode) {
					// companyInfo.setId(obj.get("companyId").asText());
					companyInfos.add(new CompanyInfo(obj.get("companyId").asText(), obj.get("companyName").asText(),
							obj.get("telecomPhoneNumber").asText(), obj.get("reputation").asDouble(),
							obj.get("companyLogo").asText()));
					log.info("10");

				}
				log.info("6");
				log.info(companyInfos);
				companyRepository.saveAll(companyInfos);

				log.info("7");

			} else {
				log.info("jambalaya");
			}

		} catch (Throwable e) {
			log.info(e.getMessage(), e);
		}

		return data;
	}

	public Object getCompaniesRoutes(String CompanyId) throws Exception {

		SanveResponse data = null;
		Call<SanveResponse> request = null;

		log.info("1");
		Long time1 = System.currentTimeMillis();
		request = sanVeCommunicate.getCompanyRoute(apiKey.trim(), SecretKey.trim(), CompanyId.trim());
		log.info(request);
		log.info("2");
		Long time = null;
		Response<SanveResponse> response = request.execute();
		log.info("3");
		if (response.isSuccessful()) {
			log.info("4");
			data = response.body();
			log.info(data.getData());
			ObjectMapper objectMapper = new ObjectMapper();
			String string1 = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(data.getData());
			log.info("5");
			// log.info(string1);
			try {
				log.info("6");
				// Map<Integer, String> map = new HashMap<>();
				JsonNode routeInfos = objectMapper.readTree(string1);
				String routeName = "Hà Nội - Sài Gòn";
				log.info(routeName);
				// List<Trip> listTrip = new ArrayList<>();
				List<RouteInfo> listRouteInfor = new ArrayList<>();
				for (JsonNode obj : routeInfos) {
					// listTrip.add(new Trip(obj.get(index), status, startDateReality,
					// startTimeReality, runTimeReality, note));
					listRouteInfor.add(new RouteInfo(obj.get("routeId").asText(), obj.get("companyId").asText(),
							obj.get("routeName").asText(), obj.get("routeNameShort").asText(),
							obj.get("childrenTicketRatio").asDouble(), obj.get("note").asText()));
					if (obj.get("routeName").asText().toLowerCase().equals(routeName.toLowerCase())) {
						log.info("1");
					}
//                    if (obj.get("routeName").asText().toLowerCase().equals(routeName.toLowerCase())) {
//                        log.info("7");
//                        log.info(obj.get("routeName").asText());
//                        JsonNode arrList = obj.get("listPoint");
//                        if (arrList.isArray()) {
//                            log.info("8");
//                            for (final JsonNode i : arrList) {
//                                log.info("9");
//                                if (i.get("province").asText().toLowerCase().equals("hà nội")) {
//                                    log.info("10");
//                                    map.put(1, i.get("name").asText());
//                                    log.info(map.values());
//                                    log.info("11");
//
//                                }
//
//                            }
//
//                            //  log.info(arrList.get("address").asText()+"|");
//                        }
//                        Long time2 = System.currentTimeMillis();
//                        // log.info(time2-time1);
//
//                        log.info(time2 - time1);
//                        // log.info("7");
//
//                    }
				}
				routeInfoRepository.saveAll(listRouteInfor);
				Long time2 = System.currentTimeMillis();
				log.info(time2 - time1);
			} catch (Exception e) {
				log.info(e.getMessage());
			}
		}
		return data;
	}

	public Object getTripsbyPoints(int page, int size, String date, String startPoint, String endPoint,
			String startTimeFrom, String startTimeTo) throws Exception {

		String start = URLDecoder.decode(startPoint, StandardCharsets.UTF_8.toString());
		log.info("-2");
		log.info(start);
		String end = URLDecoder.decode(endPoint, StandardCharsets.UTF_8.toString());
		log.info("-2");
		log.info(end);
		SanveResponse data = null;
		Call<SanveResponse> request = null;
		long time1 = System.currentTimeMillis();
		try {
			log.info("1");
			request = sanVeCommunicate.getTripsByPoint(apiKey.trim(), SecretKey.trim(), page, size, date.trim(), start,
					end, startTimeFrom.trim(), startTimeTo.trim());
			log.info(request);
			log.info("2");
			Response<SanveResponse> response = request.execute();
			log.info("3");
			if (response.isSuccessful()) {
				log.info("4");
				data = response.body();
				log.info("5");
				ObjectMapper map = new ObjectMapper();
				log.info(data.getData());
				String string1 = map.writerWithDefaultPrettyPrinter().writeValueAsString(data.getData());

				log.info("6");
				log.info(string1);

				try {
					log.info("7");
					JsonNode jsonNode = map.readTree(string1);
					log.info(jsonNode);
					log.info("8");
					JsonNode arraynode = jsonNode.get("trips");
					log.info("9");
//                    String test = "20200810";

					if (arraynode.isArray()) {
						log.info("10");
						for (final JsonNode objNode : arraynode) {
							if (objNode.get("startDateReality").asText().equals(date)) {
								log.info(objNode.get("startTimeReality").asText() + ",");

								log.info(ConventTimer(objNode.get("startTimeReality").asText()));
							}
						}
						long time2 = System.currentTimeMillis();
						log.info("a");
						log.info(time2 - time1);
						log.info("11");
					}
					// log.info(jsonNode.get("trips").asText());
					log.info("12");

				} catch (Exception e) {
					log.info(e.getMessage());
				}

			} else {
				log.info("jambalaya");
			}

		} catch (Exception e) {
			log.info(e.getMessage());
		}

		return data;
	}

	public Object getTripsbyRouteId(int page, int size, String date, String startTimeFrom, String startTimeTo,
			String RouteId) throws Exception {

		SanveResponse data = null;
		Call<SanveResponse> request = null;
		try {
			log.info("request");
			request = sanVeCommunicate.getTripsByRouteId(apiKey.trim(), SecretKey.trim(), size, page, date.trim(),
					startTimeFrom.trim(), startTimeTo.trim(), RouteId.trim());

			log.info("2");
			Response<SanveResponse> response = request.execute();
			log.info("responce");
			if (response.isSuccessful()) {
				log.info("4");
				data = response.body();
				log.info("data trả về thành công");
//                String string = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(data.getData());
//                log.info(string);
				log.info("6");

			} else {
				log.info("jambalaya");
			}

		} catch (Exception e) {
			log.info(e.getMessage());
		}

		return data;
	}

	public Object getTripsTickets(String tripId, String pointUpId, String pointDownId) throws Exception {

		SanveResponse data = null;
		Call<SanveResponse> request = null;
		try {
			log.info("1");
			request = sanVeCommunicate.getTickets(apiKey.trim(), SecretKey.trim(), tripId.trim(), pointUpId,
					pointDownId.trim());
			log.info("request");
			Response<SanveResponse> response = request.execute();
			log.info("responce");
			log.info(response);
			if (response.isSuccessful()) {
				log.info("4");
				data = response.body();
				log.info("gọi data thành công về");
//                String string = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(data.getData());
//                log.info(string);
//                log.info("5");
//                JsonNode node = objectMapper.readTree(string);
//                JsonNode tickets = node.get("tickets");
//                log.info(tickets);
//                log.info("6");
//                int count = 0;
//                if (tickets.isArray()) {
//                    log.info("7");
//                    for (JsonNode ticket : tickets) {
//                        if (ticket.get("ticketStatus").asText().equals("7")) {
//                            count++;
//                            log.info(ticket.get("originalTicketPrice") + "");
//
//                        }
//                    }
//                }
//
//                log.info(count);

			} else {
				log.info("jambalaya");
			}

		} catch (Exception e) {
			log.info(e.getMessage());
		}

		return data;
	}

	public CalculatePriceResponse calculatePrice(CalculatePriceRequest request) throws Exception {
		CalculatePriceResponse results = null;
		log.info("1");
		Call<CalculatePriceResponse> requests = sanVeCommunicate.calculatePrice(request);
		log.info("2");
		Response<CalculatePriceResponse> response = requests.execute();
		log.info(response);
		log.info("3");
		if (response.isSuccessful()) {
			log.info("4");
			results = response.body();
			log.info("5");
		}
		return results;
	}

	public int isCheckCity(String data, String route_name) {
		if (data.toLowerCase().contains(route_name.toLowerCase())) {
			return 1;
		}
		return 0;

	}

	public Object listRoutes(String data, String City, String routeName) throws JsonProcessingException {

		Map<Integer, String> listPoints = new HashMap<>();

		try {
			log.info("Chuyển Json thành java Object");
			JsonNode routeInfos = objectMapper.readTree(data);
			log.info(routeInfos);
			JsonNode Routes = routeInfos.get("data");
			log.info(Routes);
			log.info("So sánh chuyến");
			for (JsonNode obj : Routes) {

				if (obj.get("routeName").asText().toLowerCase().equals(routeName.toLowerCase())) {
					JsonNode listPoint = obj.get("listPoint");
					if (listPoint.isArray()) {
						for (JsonNode arr : listPoint) {
							// if(arr.get("province").asText().equals("Sài Gòn"||"Hô"))
							if (arr.get("province").asText().toLowerCase().equals(City.toLowerCase())) {
								listPoints.put(1, arr.get("name").asText());
							}
						}
					}
				}
			}
			log.info("thành công");
		} catch (Exception e) {
			log.info(e.getMessage());
		}
		return listPoints.values();
	}

	public Object listStartTimeReality(String data, String date) {
		Map<String, Object> list = new HashMap<>();
		List listTimes = new ArrayList();
		List listTripId = new ArrayList();

		log.info("cắt chuyễn Json");

		try {
			log.info("7");
			JsonNode jsonNode = objectMapper.readTree(data);
			// log.info(jsonNode);
			log.info("8");
			JsonNode arraynodes = jsonNode.get("data");
			JsonNode arraynode = arraynodes.get("trips");
			log.info("9");

			if (arraynode.isArray()) {
				log.info("So sánh chuỗi");
				for (final JsonNode objNode : arraynode) {
					// listTimes.put("routeId")
					if (objNode.get("startDateReality").asText().equals(date)) {
						log.info(objNode.get("startTimeReality").asText() + ",");
						listTimes.add(ConventTimer(objNode.get("startTimeReality").asText()));
						listTripId.add(objNode.get("tripId").asText());
						log.info(ConventTimer(objNode.get("startTimeReality").asText()));
						log.info("thành công");
					}
				}
				list.put("tripId", listTripId);
				list.put("timer", listTimes);
			}
		} catch (Exception e) {
			log.info(e.getMessage());

		}
		return list;
	}

	private String ConventTimer(String startTimeReality) {
		String timer = null;
		double Time = Double.parseDouble(startTimeReality);
		double number = Time / 3600000;
		int hour = (int) Math.floor(number);
		if (number == hour) {
			timer = hour + " giờ";
		} else {
			timer = hour + " giờ " + Math.round((number - hour) * 60) + " phút";
		}
		return timer;
	}

	public Object QuantitiesTickets(String data) throws JsonProcessingException {
		Map<String, Object> quantitys = new HashMap<>();
		JsonNode node = objectMapper.readTree(data);
		JsonNode tickets = node.get("data");
		JsonNode ti = tickets.get("tickets");

		log.info(ti);
		log.info("6");
		int count = 0;
		if (ti.isArray()) {
			log.info("7");

			int temp = 0;
			for (JsonNode ticket : ti) {
				if (ticket.get("ticketStatus").asInt() == 7) {
					count++;
					log.info(ticket.get("originalTicketPrice").asInt());
					int code = ticket.get("originalTicketPrice").asInt();
					if (temp == 0) {
						temp = code;
					} else if (code < temp) {
						temp = code;
					}
				}
			}
			log.info(count);
			quantitys.put("ticket_qtt", count);
			log.info(temp);
			quantitys.put("price", temp);

		}

		log.info(count);
		return quantitys;
	}
}
