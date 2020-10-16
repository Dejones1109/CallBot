package com.its.sanve.api.facede;

import com.its.sanve.api.communication.CallBotCommunication;
import com.its.sanve.api.communication.SanVeCommunicate;
import com.its.sanve.api.communication.dto.OrderTicketRequest;
import com.its.sanve.api.communication.sanve.SanVeResponse;
import com.its.sanve.api.dto.TransactionRequest;
import com.its.sanve.api.entities.TransactionLog;
import com.its.sanve.api.repositories.TransactionLogRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

@Log4j2
@Component
public class CallBotFacade {
	
	private SanVeCommunicate sanVeCommunicate;

	@Autowired
    CallBotCommunication callBotCommunication;

	@Autowired
    TransactionLogRepository transactionLogRepository;

	private String convertDateTime(String date) throws ParseException {
		Date initDate = new SimpleDateFormat("dd/MM/yyyy").parse(date);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		String parsedDate = formatter.format(initDate);
		System.out.println(parsedDate);
		return parsedDate;
	}

	public boolean orderTicket(String secretKey, String apiKey, TransactionRequest transactionRequest) throws ParseException {
		try {

			OrderTicketRequest orderTicketRequest = new OrderTicketRequest();
			orderTicketRequest.setSecretKey(secretKey);
			orderTicketRequest.setApiKey(apiKey);
			orderTicketRequest.setSeat(transactionRequest.getSeatId().toString());
			orderTicketRequest.setPoint(transactionRequest.getPointSelected());
			orderTicketRequest.setTripId(transactionRequest.getTripId());
			orderTicketRequest.setRouteId(transactionRequest.getRoute());
			orderTicketRequest.setCustomerFullname(transactionRequest.getFull_name());
			orderTicketRequest.setCustomerPhone(transactionRequest.getPhoneOrder());
			orderTicketRequest.setCompanyId(transactionRequest.getCompanyId());


			log.info(orderTicketRequest);
			boolean result = true;
			int sanVeResponse = callBotCommunication.orderTicket(orderTicketRequest);


			if(sanVeResponse == 1) {
				result = true;
			}else {
				result = false;
			}
			log.debug(sanVeResponse);
//      return sanVeResponse;
			return result;
		} catch (Exception e) {
			log.error("Order Ticket Failed", e);
			return false;
		} finally {
			String seat = transactionRequest.getSeatId().toString().replace("[","").replace("]","");
			TransactionLog transactionLog = new TransactionLog();
			transactionLog.setPhone(transactionRequest.getPhone());
			transactionLog.setIntent(transactionRequest.getIntent());
			transactionLog.setRoute(transactionRequest.getRoute());
			transactionLog.setCreatedAt(LocalDateTime.now());
			transactionLog.setHotLine(transactionRequest.getHotLine());
			transactionLog.setStartDate(transactionRequest.getStartDate());
			transactionLog.setCallId(transactionRequest.getCallId());
			transactionLog.setPointDown(transactionRequest.getPointDown());
			transactionLog.setPointUp(transactionRequest.getPointUp());
			transactionLog.setPhoneOrder(transactionRequest.getPhoneOrder());
			transactionLog.setStartTimeReality(transactionRequest.getStartTimeReality());
			transactionLog.setStatus(transactionRequest.getStatus());
            transactionLog.setTicket(seat);
			transactionLog.setTripId(transactionRequest.getTripId());
			transactionLog.setCompanyId(transactionRequest.getCompanyId());
			transactionLog.setPointSelected(transactionRequest.getPointSelected());
			transactionLogRepository.save(transactionLog);

		}


	}


}
