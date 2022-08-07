package com.example.payment.model;

import com.example.payment.enums.CurrencyType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

//db'ye ekle
@Getter
@Setter
@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int userId;
    private LocalDateTime paymentDate;
    @Enumerated(EnumType.STRING)
    private CurrencyType currencyType;
    private BigDecimal amount;

    @Override
    public String toString() {
        return "Payment{" +
                "localDateTime=" + paymentDate +
                ", currencyType=" + currencyType +
                ", amount=" + amount +
                '}';
    }
}
