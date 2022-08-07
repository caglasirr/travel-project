package com.travel.travel.Service;

import com.travel.travel.Dto.Response.TicketResponse;
import com.travel.travel.Model.Ticket;
import com.travel.travel.Repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

@Service
public class TicketService {

    private TicketRepository ticketRepository;

    public TicketResponse findAmountAndNumberOfTickets(){
        TicketResponse response = new TicketResponse();
        int totalAmount = 0;
        int totalNumber = ticketRepository.findAll().size();
        for(Ticket ticket: ticketRepository.findAll()){
            totalAmount+=ticket.getTotalAmount().intValue();
        }
        response.setTotalAmountOfTickets(BigInteger.valueOf(totalAmount));
        response.setTotalNumberOfTickets(totalNumber);
        return response;
    }

    @Autowired
    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }
}
