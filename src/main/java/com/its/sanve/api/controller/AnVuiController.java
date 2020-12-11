package com.its.sanve.api.controller;

import com.its.sanve.api.communication.anvui.AnVuiCommunication;
import com.its.sanve.api.communication.dto.OldCustomerRequest;
import com.its.sanve.api.communication.dto.TripsRequest;
import com.its.sanve.api.entities.Customer;
import com.its.sanve.api.entities.Trip;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Log4j2
@RequestMapping("/anVui")
public class AnVuiController {

    @Autowired
    AnVuiCommunication anVuiCommunication;
    @PostMapping("login")
    public String login(@RequestParam String userName,@RequestParam String password,@RequestParam String accessCode,@RequestParam String deviceType,@RequestParam String refreshToken){
       return anVuiCommunication.login(userName,password,accessCode,deviceType,refreshToken);
    }
    @PostMapping("listTrips")
    public List<Trip> listTrips(@RequestBody TripsRequest request){
        return anVuiCommunication.getTrips(request);
    }

    @PostMapping("oldCustomer")
    public Customer[] oldCustomer(@RequestBody OldCustomerRequest request){
        return anVuiCommunication.oldCustomerTicket(request);
    }
}
