package com.travel.travel.Service;

import com.travel.travel.Dto.Response.TicketResponse;
import com.travel.travel.Model.Ticket;
import com.travel.travel.Repository.TicketRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class TicketServiceTest {

    @InjectMocks
    private TicketService ticketService;

    @Mock
    private TicketRepository ticketRepository;

    @Test
    void deneme(){
        //given
        List<Ticket> ticketList = prepareTickets();
        Mockito.when(ticketRepository.findAll()).thenReturn(ticketList);
        TicketResponse response_ = new TicketResponse(2,BigInteger.valueOf(60));

        //when
        TicketResponse response = ticketService.findAmountAndNumberOfTickets();

        //then
        assertEquals(response,response_);
        verify(ticketRepository, times(2)).findAll();
    }

    private List<Ticket> prepareTickets(){
        List list = new ArrayList<>();
        Ticket ticket1 = Ticket.builder().totalAmount(BigInteger.valueOf(30)).build();
        Ticket ticket2 = Ticket.builder().totalAmount(BigInteger.valueOf(30)).build();
        list.add(ticket1);
        list.add(ticket2);
        return list;
    }
}
