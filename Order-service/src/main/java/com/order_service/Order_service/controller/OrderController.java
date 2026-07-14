package com.order_service.Order_service.controller;

import com.order_service.Order_service.dto.CreateOrderRequestDTO;
import com.order_service.Order_service.dto.OrderDto;
import com.order_service.Order_service.dto.OrderResponseDTO;
import com.order_service.Order_service.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/save")
    public ResponseEntity<OrderResponseDTO> createOrder(@RequestBody CreateOrderRequestDTO createOrderRequestDTO){
        OrderResponseDTO orderResponseDTO = orderService.createOrder(createOrderRequestDTO);
        return new ResponseEntity<> (orderResponseDTO, HttpStatus.CREATED);
    }

    @GetMapping("/getByOrderId/{orderNumber}")
    public ResponseEntity<OrderResponseDTO> getOrder(@PathVariable String orderNumber){
        return new ResponseEntity<>(orderService.findOrderByOrderNumber(orderNumber), HttpStatus.FOUND);
    }

}
