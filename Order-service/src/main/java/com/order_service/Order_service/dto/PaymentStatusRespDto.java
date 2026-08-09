package com.order_service.Order_service.dto;

import com.order_service.Order_service.enums.PaymentStatus;

public class PaymentStatusRespDto {
    private boolean status;
    private String orderNumber;
    private PaymentStatus paymentStatus;
}
