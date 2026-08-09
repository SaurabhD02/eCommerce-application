package com.order_service.Order_service.config;

import com.order_service.Order_service.dto.OrderItemResponseDTO;
import com.order_service.Order_service.dto.OrderResponseDTO;
import com.order_service.Order_service.dto.PaymentResponseDTO;
import com.order_service.Order_service.dto.ShippingAddressResponseDTO;
import com.order_service.Order_service.entity.OrderEntity;
import com.order_service.Order_service.entity.OrderItems;
import com.order_service.Order_service.entity.Payment;
import com.order_service.Order_service.entity.ShippingAddress;
import org.springframework.context.annotation.Configuration;

import java.util.stream.Collectors;

@Configuration
public class OrderMapper {
    public OrderResponseDTO toResponse(OrderEntity order) {
        return OrderResponseDTO.builder()
                .id(order.getId())
                .orderNumber(order.getOrderNumber())
                .customerId(order.getCustomerId())
                .status(order.getStatus())
                .subtotal(order.getSubtotal())
                .taxAmount(order.getTaxAmount())
                .shippingAmount(order.getShippingAmount())
                .totalAmount(order.getTotalAmount())
                .couponCode(order.getCouponCode())
                .items(order.getItems()
                        .stream()
                        .map(this::toItemResponse)
                        .collect(Collectors.toList()))
                .shippingAddress(toAddressResponse(order.getShippingAddress()))
                .payment(toPaymentResponse(order.getPayment()))
                .createdAt(order.getCreatedAt())
                .build();
    }

    private OrderItemResponseDTO toItemResponse(OrderItems item) {
        return OrderItemResponseDTO.builder()
                .id(item.getId())
                .productId(item.getProductId())
                .productName(item.getProductName())
                .quantity(item.getQuantity())
                .unitPrice(item.getUnitPrice())
                .totalPrice(item.getTotalPrice())
                .build();
    }

    private ShippingAddressResponseDTO toAddressResponse(ShippingAddress address) {
        if (address == null) return null;
        return ShippingAddressResponseDTO.builder()
                .id(address.getId())
                .fullName(address.getFullName())
                .phone(address.getPhone())
                .addressLine1(address.getAddressLine1())
                .addressLine2(address.getAddressLine2())
                .city(address.getCity())
                .state(address.getState())
                .country(address.getCountry())
                .postalCode(address.getPostalCode())
                .build();
    }

    private PaymentResponseDTO toPaymentResponse(Payment payment) {
        if (payment == null) return null;
        return PaymentResponseDTO.builder()
                .id(payment.getId())
                .amount(payment.getAmount())
                .method(payment.getMethod())
                .status(payment.getStatus())
                .paidAt(payment.getPaidAt())
                .build();
    }
}
