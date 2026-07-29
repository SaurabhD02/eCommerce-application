package com.order_service.Order_service.dto;

import com.order_service.Order_service.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponseDTO {
    private Long id;
    private String orderNumber;
    private String customerId;
    private OrderStatus status;

    private BigDecimal subtotal;
    private BigDecimal taxAmount;
    private BigDecimal shippingAmount;
    private BigDecimal totalAmount;

    private String couponCode;

    private List<OrderItemResponseDTO> items;
    private ShippingAddressResponseDTO shippingAddress;
    private PaymentResponseDTO payment;

    private LocalDateTime createdAt;
}
