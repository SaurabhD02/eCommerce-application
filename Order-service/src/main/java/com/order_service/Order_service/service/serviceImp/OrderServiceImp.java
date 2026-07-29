package com.order_service.Order_service.service.serviceImp;

import com.order_service.Order_service.config.OrderMapper;
import com.order_service.Order_service.dto.CreateOrderRequestDTO;
import com.order_service.Order_service.dto.OrderDto;
import com.order_service.Order_service.dto.OrderResponseDTO;
import com.order_service.Order_service.entity.OrderEntity;
import com.order_service.Order_service.entity.OrderItems;
import com.order_service.Order_service.entity.Payment;
import com.order_service.Order_service.entity.ShippingAddress;
import com.order_service.Order_service.enums.OrderStatus;
import com.order_service.Order_service.enums.PaymentStatus;
import com.order_service.Order_service.repository.OrderRepository;
import com.order_service.Order_service.service.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImp implements OrderService {

    private final ModelMapper modelMapper;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Autowired
    public OrderServiceImp(ModelMapper modelMapper, OrderRepository orderRepository, OrderMapper orderMapper) {
        super();
        this.modelMapper = modelMapper;
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }

    @Override
    public OrderResponseDTO createOrder(CreateOrderRequestDTO createOrderRequestDTO){
//        OrderEntity order = modelMapper.map(createOrderRequestDTO, OrderEntity.class);
//        OrderEntity orderCreate = orderRepository.save(order);
//        return modelMapper.map(orderCreate, OrderDto.class);
        List<OrderItems> items = createOrderRequestDTO.getItems().stream().map(i ->
                OrderItems.builder().productId(i.getProductId())
                        .quantity(i.getQuantity())
                        .unitPrice(i.getUnitPrice())
                        .totalPrice(i.getUnitPrice().multiply(BigDecimal.valueOf(i.getQuantity())))
                        .build()).collect(Collectors.toList());

        BigDecimal subTotal = items.stream().map(OrderItems::getTotalPrice).
                reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal tax      = subTotal.multiply(new BigDecimal("0.18"));
        BigDecimal shipping = new BigDecimal("50.00");
        BigDecimal total    = subTotal.add(tax).add(shipping);

        ShippingAddress address = ShippingAddress.builder()
                .fullName(createOrderRequestDTO.getShippingAddress().getFullName())
                .phone(createOrderRequestDTO.getShippingAddress().getPhone())
                .addressLine1(createOrderRequestDTO.getShippingAddress().getAddressLine1())
                .addressLine2(createOrderRequestDTO.getShippingAddress().getAddressLine2())
                .city(createOrderRequestDTO.getShippingAddress().getCity())
                .state(createOrderRequestDTO.getShippingAddress().getState())
                .country(createOrderRequestDTO.getShippingAddress().getCountry())
                .postalCode(createOrderRequestDTO.getShippingAddress().getPostalCode())
                .build();

        Payment payment = Payment.builder()
                .method(createOrderRequestDTO.getPaymentMethod())
                .status(PaymentStatus.PENDING)
                .amount(total)
                .build();

        // 5. Build and save order
        OrderEntity order = OrderEntity.builder()
                .orderNumber("ORD-" + System.currentTimeMillis())
                .customerId(createOrderRequestDTO.getCustomerId())
                .status(OrderStatus.PENDING)
                .subtotal(subTotal)
                .taxAmount(tax)
                .shippingAmount(shipping)
                .totalAmount(total)
                .couponCode(createOrderRequestDTO.getCouponCode())
                .shippingAddress(address)
                .payment(payment)
                .build();

        items.forEach(item -> item.setOrder(order));
        order.setItems(items);

        return orderMapper.toResponse(orderRepository.save(order));
    }

    @Override
    public OrderResponseDTO findOrderByOrderNumber(String orderNumber){
        OrderEntity order = orderRepository.findByOrderNumber(orderNumber);
        return orderMapper.toResponse(order);
    }
}
