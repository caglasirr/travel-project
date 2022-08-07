package com.travel.traveladmin.Service;

import com.travel.traveladmin.Client.TravelClient;
import com.travel.traveladmin.Dto.TicketResponse;
import com.travel.traveladmin.Exception.TravelException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class TicketServiceTest {

    @InjectMocks
    private TicketService ticketService;

    @Mock
    private TravelClient travelClient;

    @Test
    void itShouldReturnTicketResponse(){
        //given
        TicketResponse response = prepareTicketResponse();
        Mockito.when(travelClient.findAmountAndNumberOfTickets()).thenReturn(response);

        //when
        TicketResponse response_ = ticketService.findAmountAndNumberOfTickets();

        //then
        assertEquals(response,response_);
        verify(travelClient, times(1)).findAmountAndNumberOfTickets();
    }

    @Test
    void itShouldThrowException(){
        //given
        TicketResponse response = prepareTicketResponse();
        Mockito.when(travelClient.findAmountAndNumberOfTickets()).thenThrow(new RuntimeException());

        //when
        TravelException e = assertThrows(TravelException.class,
                ()->ticketService.findAmountAndNumberOfTickets());

        //then
        assertEquals("Sistemsel bir hata oluştu..",e.getMessage());
        verify(travelClient, times(1)).findAmountAndNumberOfTickets();
    }

    public TicketResponse prepareTicketResponse(){
        return TicketResponse.builder().totalNumberOfTickets(5)
                .totalAmountOfTickets(BigInteger.valueOf(150)).build();
    }
}
