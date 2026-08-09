package com.Payment_service.Payment_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PaymentDto {
    private String orderNumber;
    private BigDecimal amount;
    private String currency;
    private String name;
    private String email;
}
