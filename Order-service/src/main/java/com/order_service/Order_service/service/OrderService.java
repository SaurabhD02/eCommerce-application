package com.order_service.Order_service.service;

import com.order_service.Order_service.dto.CreateOrderRequestDTO;
import com.order_service.Order_service.dto.OrderResponseDTO;
import com.order_service.Order_service.dto.PaymentStatusReqDto;
import com.order_service.Order_service.dto.PaymentStatusRespDto;

public interface OrderService {

    public OrderResponseDTO createOrder(CreateOrderRequestDTO createOrderRequestDTO);

    public OrderResponseDTO findOrderByOrderNumber(String orderNumber);

    public PaymentStatusRespDto paymentStatusUpdate(PaymentStatusReqDto paymentStatusReq);
}
