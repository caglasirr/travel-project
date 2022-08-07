package com.travel.travel.Client;

import com.travel.travel.Dto.PaymentDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value="payment-service", url="http://localhost:3434")
public interface PaymentClient {

    @PostMapping(value = "/payments")
    PaymentDto createPayment(@RequestBody PaymentDto paymentDto);
}