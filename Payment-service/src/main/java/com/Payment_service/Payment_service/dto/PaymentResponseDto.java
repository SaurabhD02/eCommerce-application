package com.Payment_service.Payment_service.dto;

import com.Payment_service.Payment_service.enums.PaymentStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PaymentResponseDto {
    private String orderNumber;
    private BigDecimal amount;
    private String name;
    private String email;
    private String razorpayOrderId;
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;
    private String paymentUrl;

    private String failureReason;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
