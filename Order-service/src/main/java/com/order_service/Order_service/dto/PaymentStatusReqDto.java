package com.order_service.Order_service.dto;

import com.order_service.Order_service.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PaymentStatusReqDto {
    public String OrderNumber;
    public PaymentStatus paymentStatus;
}
