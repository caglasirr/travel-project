package com.travel.travel.Dto.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketResponse {
    private int totalNumberOfTickets;
    private BigInteger totalAmountOfTickets;
}