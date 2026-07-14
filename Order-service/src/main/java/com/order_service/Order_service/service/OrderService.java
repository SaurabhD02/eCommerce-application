package com.order_service.Order_service.service;

import com.order_service.Order_service.dto.CreateOrderRequestDTO;
import com.order_service.Order_service.dto.OrderDto;
import com.order_service.Order_service.dto.OrderResponseDTO;

public interface OrderService {

    public OrderResponseDTO createOrder(CreateOrderRequestDTO createOrderRequestDTO);

    public OrderResponseDTO findOrderByOrderNumber(String orderNumber);
}
