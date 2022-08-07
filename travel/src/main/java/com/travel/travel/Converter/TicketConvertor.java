package com.travel.travel.Converter;

import com.travel.travel.Dto.TicketDto;
import com.travel.travel.Dto.TripDto;
import com.travel.travel.Model.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TicketConvertor {

    private TripConverter tripConverter;
    private PassengerConverter passengerConverter;

    public List<TicketDto> convert(List<Ticket> tickets){
        List<TicketDto> ticketDtos = new ArrayList<>();
        tickets.forEach(ticket -> ticketDtos.add(convert(ticket)));
        return ticketDtos;
    }

    public TicketDto convert(Ticket ticket){
        return TicketDto.builder().ticketNumber(ticket.getId())
                .paymentAmount(ticket.getTotalAmount())
                .tripInfo(tripConverter.convert(ticket.getTrip()))
                .passengerInfo(passengerConverter.convert(ticket.getPassenger())).build();
    }

    @Autowired
    public TicketConvertor(TripConverter tripConverter, PassengerConverter passengerConverter) {
        this.tripConverter = tripConverter;
        this.passengerConverter = passengerConverter;
    }
}
