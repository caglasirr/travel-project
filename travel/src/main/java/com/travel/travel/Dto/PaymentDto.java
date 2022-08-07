package com.travel.travel.Dto;

import com.travel.travel.Enum.CurrencyType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDto {
    private int userId;
    private LocalDateTime paymentDate;
    private CurrencyType currencyType;
    private BigDecimal amount;
}
