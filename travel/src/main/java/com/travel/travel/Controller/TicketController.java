package com.travel.travel.Controller;

import com.travel.travel.Dto.Response.TicketResponse;
import com.travel.travel.Dto.TripDto;
import com.travel.travel.Service.TicketService;
import com.travel.travel.Service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/admins")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @GetMapping(value = "/tickets")
    public ResponseEntity<TicketResponse> findAmountAndNumberOfTickets(){
        TicketResponse response = ticketService.findAmountAndNumberOfTickets();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
