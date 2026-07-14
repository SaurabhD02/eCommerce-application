package com.order_service.Order_service.entity;

import com.order_service.Order_service.enums.PaymentMethod;
import com.order_service.Order_service.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String orderId;
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private PaymentMethod method;
    // CREDIT_CARD, DEBIT_CARD, UPI, NET_BANKING, WALLET, COD

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;
    // PENDING, SUCCESS, FAILED, REFUNDED

    private String transactionId;     // Gateway transaction ID
    private String gatewayName;       // Razorpay, Stripe etc.
    private String gatewayResponse;   // Raw JSON response

    private LocalDateTime paidAt;
}
