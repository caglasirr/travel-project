package com.travel.traveladmin.Client;

import com.travel.traveladmin.Dto.TicketResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value="payment-service", url="http://localhost:8086")
public interface TravelClient {

    @GetMapping(value="/users/tickets")
    TicketResponse findAmountAndNumberOfTickets();

}

