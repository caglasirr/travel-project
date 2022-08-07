package com.travel.travel.Converter;

import com.travel.travel.Dto.OrderDto;
import com.travel.travel.Dto.TicketDto;
import com.travel.travel.Model.Order;
import com.travel.travel.Model.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderConverter {

    private TicketConvertor ticketConvertor;

    public List<OrderDto> convert(List<Order> orders){
        List<OrderDto> orderDtos = new ArrayList<>();
        orders.forEach(order -> orderDtos.add(convert(order)));
        return orderDtos;
    }

    public OrderDto convert(Order order){
        return OrderDto.builder().orderNumber(order.getId())
                .paymentType(order.getPaymentType())
                .totalPaymentAmount(order.getTotalAmount())
                .tickets(ticketConvertor.convert(order.getTickets()))
                .build();
    }

    @Autowired
    public OrderConverter(TicketConvertor ticketConvertor) {
        this.ticketConvertor = ticketConvertor;
    }
}
