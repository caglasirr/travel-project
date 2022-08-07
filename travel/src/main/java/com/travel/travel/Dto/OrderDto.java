package com.travel.travel.Dto;

import com.travel.travel.Enum.PaymentType;
import com.travel.travel.Model.Ticket;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigInteger;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class OrderDto {
    private int orderNumber;
    private PaymentType paymentType;
    private BigInteger totalPaymentAmount;
    private List<TicketDto> tickets;
}
