package com.order_service.Order_service.dto;

import com.order_service.Order_service.enums.PaymentMethod;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderRequestDTO {
    @NotBlank(message = "Customer ID is required")
    private String customerId;

    @NotNull(message = "Payment method is required")
    private PaymentMethod paymentMethod;   // UPI, COD, CREDIT_CARD

    private String couponCode;

    @NotEmpty(message = "Order must have at least one item")
    private List<OrderItemRequestDTO> items;

    @NotNull(message = "Shipping address is required")
    private ShippingAddressRequestDTO shippingAddress;
}
