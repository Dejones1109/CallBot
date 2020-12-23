package com.its.sanve.api.facede;


import com.its.sanve.api.communication.anvui.AnVuiCommunication;
import com.its.sanve.api.communication.dto.TicketRequest;
import com.its.sanve.api.entities.CompanyInfo;
import com.its.sanve.api.entities.CustomerOrder;
import com.its.sanve.api.entities.Ticket;
import com.its.sanve.api.repositories.CompanyRepository;
import com.its.sanve.api.repositories.CustomerOrderRepository;
import com.its.sanve.api.repositories.RouteInfoRepository;
import com.its.sanve.api.repositories.TranshipmentPointRepository;
import com.its.sanve.api.utils.MessageUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;


@Log4j2
@Component
public class GetDataFacade {

    @Autowired
    MessageUtils messageUtils;


    @Autowired
    private CompanyRepository companyRepository;


    @Autowired
    private RouteInfoRepository routeInfoRepository;

    @Autowired
    AnVuiCommunication anVuiCommunication;
    @Autowired
    TranshipmentPointRepository transhipmentPointRepository;
    @Autowired
    CustomerOrderRepository customerOrderRepository;

     DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    public Map<String, Object> getCompanyInfo(String company_phone) throws Exception {
        Map<String, Object> p = new HashMap<>();
        CompanyInfo data = companyRepository.findPhone(company_phone);

        log.info(data);

        p.put("companyId", data.getId());
        p.put("companyName", data.getName());
        p.put("companyShortName", data.getNameShort());
        return p;
    }

    public void orderTicket(TicketRequest request,int status) {
        CustomerOrder customerOrder = new CustomerOrder();
        customerOrder.setTripId(request.getTripId());
        customerOrder.setCompanyId(request.getCompanyId());
        customerOrder.setStatus(status);
        customerOrder.setCreateDate(myFormatObj.format(LocalDateTime.now()));
        for (Ticket ticket : request.getInformationsBySeats()) {
            customerOrder.setName(ticket.getFullName());
            customerOrder.setPhone(ticket.getPhoneNumber());
            customerOrder.setPhoneOrder(ticket.getOrderPhoneNumber());
            customerOrder.setStartPointId(ticket.getPointUp().getPointId());
            customerOrder.setEndPointId(ticket.getPointDown().getPointId());
            String[] countTicket = ticket.getSeatId().split(",");
            customerOrder.setTicket(countTicket.length);
            customerOrder.setStartDate(ticket.getStartDate());
            customerOrder.setStartTime(ticket.getStartTime());
        }
         customerOrderRepository.save(customerOrder);
    }


}