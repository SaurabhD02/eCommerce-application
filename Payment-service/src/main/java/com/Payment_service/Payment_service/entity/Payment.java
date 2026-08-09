package com.Payment_service.Payment_service.entity;

import com.Payment_service.Payment_service.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

// entity/Payment.java
@Entity
@Table(name = "payments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String orderId;
    private String orderNumber;
    private BigDecimal amount;
    private String name;
    private String email;

    private String razorpayOrderId;
//    private String razorpayPaymentId;
//    private String razorpaySignature;
//    private String razorpayPaymentLink;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    private String failureReason;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
