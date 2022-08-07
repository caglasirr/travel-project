package com.travel.travel.Dto;

import com.travel.travel.Model.Passenger;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigInteger;

@Data
@AllArgsConstructor
@Builder
public class TicketDto {
    private int ticketNumber;
    private BigInteger paymentAmount;
    private TripDto tripInfo;
    private PassengerDto passengerInfo;
}
