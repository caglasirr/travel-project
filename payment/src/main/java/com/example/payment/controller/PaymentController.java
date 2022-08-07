package com.example.payment.controller;

import com.example.payment.model.Payment;
import com.example.payment.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    //dönüş tipini direkt Payment değil de PaymentRequest ya da PaymentResponse olarak değiştir!!
    @PostMapping
    public ResponseEntity<Payment> createPayment(@RequestBody Payment payment){
        paymentService.createPayment(payment);
        return new ResponseEntity<>(paymentService.createPayment(payment), HttpStatus.OK);
    }

    //public PaymentResponse createPayment(@RequestBody PaymentRequest payment){
    //    return paymentService.createPayment(payment);
    //}

    @GetMapping
    public Payment aa(@PathVariable Integer paymentId){
        return paymentService.getPaymentById(paymentId);
    }

}
