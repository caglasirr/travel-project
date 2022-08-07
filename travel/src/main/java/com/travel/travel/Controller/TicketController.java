package com.travel.travel.Controller;

import com.travel.travel.Dto.OrderDto;
import com.travel.travel.Dto.Request.TicketRequest;
import com.travel.travel.Dto.Response.TicketResponse;
import com.travel.travel.Service.OrderService;
import com.travel.travel.Service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/users")
public class TicketController {

    private TicketService ticketService;
    private OrderService orderService;

    @GetMapping(value = "/tickets")
    public ResponseEntity<TicketResponse> findAmountAndNumberOfTickets(){
        TicketResponse response = ticketService.findAmountAndNumberOfTickets();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/{userId}/ticket")
    public ResponseEntity<String> createOrder(@PathVariable int userId, @RequestBody TicketRequest ticketRequest){
        orderService.createOrder(userId,ticketRequest);
        return new ResponseEntity<>("You have successfully purchased your tickets!", HttpStatus.OK);
    }

    @GetMapping(value = "/{userId}/tickets")
    public ResponseEntity<List<OrderDto>> findTickets(@PathVariable int userId){
        List list = orderService.findTickets(userId);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @Autowired
    public TicketController(TicketService ticketService, OrderService orderService) {
        this.ticketService = ticketService;
        this.orderService = orderService;
    }
}
