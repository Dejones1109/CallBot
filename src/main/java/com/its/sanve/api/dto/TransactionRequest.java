package com.its.sanve.api.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@Data
@ApiModel(description = "Request send gift")
@JsonAutoDetect
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionRequest {

	    @JsonProperty("phone")
	    String phone;

	    @JsonProperty("phone_order")
	    String phoneOrder;

	    @JsonProperty("call_id")
	    String callId;

	    @JsonProperty("hotline")
	    String hotLine;

	    @JsonProperty("intent")
	    String intent;

	    @JsonProperty("point_up")
	    String pointUp;
	    
	    @JsonProperty("point_down")
	    String pointDown;
	    
	    @JsonProperty("start_time_reality")
	    String startTimeReality;
	    
	    @JsonProperty("start_date")
	    String startDate;
	    
	    @JsonProperty("created_at")
	    LocalDateTime createdAt;
	    
	    @JsonProperty("route_id")
	    String route;
	    
	    @JsonProperty("status")
	    Integer status;

	    @JsonProperty("seat_id")
	    List<String> seatId;
	    
	    @JsonProperty("trip_id")
	    String tripId;
	    
	    @JsonProperty("company_id")
	    String companyId;
	    
	    @JsonProperty("point_selected")
	    String pointSelected;


}
