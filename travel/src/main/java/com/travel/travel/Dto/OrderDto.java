package com.travel.travel.Dto;

import com.travel.travel.Enum.PaymentType;
import com.travel.travel.Model.Ticket;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.math.BigInteger;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
@Slf4j
public class OrderDto {
    private int orderNumber;
    private PaymentType paymentType;
    private BigInteger totalPaymentAmount;
    private List<TicketDto> tickets;

    @Override
    public String toString() {
        return "OrderDto{" +
                "orderNumber=" + orderNumber +
                ", paymentType=" + paymentType +
                ", totalPaymentAmount=" + totalPaymentAmount +
                ", tickets=" + tickets +
                '}';
    }
}
