package com.travel.travel.Dto.Request;

import com.travel.travel.Enum.PaymentType;
import com.travel.travel.Model.Passenger;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TicketRequest {
    private int tripId;
    private PaymentType paymentType;
    private List<Passenger> passenger;
}