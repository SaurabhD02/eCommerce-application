package com.Payment_service.Payment_service.service.impl;

import com.Payment_service.Payment_service.Repository.PaymentRepository;
import com.Payment_service.Payment_service.dto.PaymentDto;
import com.Payment_service.Payment_service.dto.PaymentResponseDto;
import com.Payment_service.Payment_service.entity.Payment;
import com.Payment_service.Payment_service.enums.PaymentStatus;
import com.Payment_service.Payment_service.service.PaymentService;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    ModelMapper modelMapper;

    @Value("${razorpay.key.id}")
    private String KEY_ID;// = "rzp_test_THeiDXPuGtstGk";

    @Value("${razorpay.key.secret}")
    private String KEY_SECRET; // = "2qZGj9DqHe0tcTJm0HBqtW4f";

    @Value("${appUrl}")
    private String app_url;

    @Override
    public PaymentResponseDto createPayment(PaymentDto paymentDto) throws RazorpayException {
        Payment payment = modelMapper.map(paymentDto, Payment.class);
        System.out.println("KEY_ID>>>>"+ KEY_ID +" KEY_SECRET>>>>>"+ KEY_SECRET);
        RazorpayClient razorpay = new RazorpayClient(KEY_ID, KEY_SECRET);
        JSONObject orderRequest = new JSONObject();
        orderRequest.put("amount", paymentDto.getAmount().multiply(BigDecimal.valueOf(100)));
        orderRequest.put("currency", paymentDto.getCurrency());
        Order order = razorpay.orders.create(orderRequest);

        payment.setRazorpayOrderId(order.get("id"));
        payment.setStatus(PaymentStatus.INPROGRESS);

        System.out.println("payment>>>>"+ payment);

        Payment savePayment = paymentRepository.save(payment);

        String paymentUrl = app_url+"/process-payment/"+paymentDto.getOrderNumber();

        PaymentResponseDto paymentResponseDto = PaymentResponseDto.builder().
                name(paymentDto.getName())
                .orderNumber(paymentDto.getOrderNumber())
                .amount(paymentDto.getAmount())
                .razorpayOrderId(order.get("id"))
                .email(paymentDto.getEmail())
                .status(PaymentStatus.INPROGRESS)
                .paymentUrl(paymentUrl).build();


        return paymentResponseDto;
    }

    @Override
    public PaymentResponseDto processPayment(String orderNumber){
        Payment payment = paymentRepository.findByOrderNumber(orderNumber);

        if (payment == null) {
            throw new IllegalArgumentException("No payment found for order number: " + orderNumber);
        }

        System.out.println("payment>>>>>"+ payment);

        PaymentResponseDto paymentResponseDto = PaymentResponseDto.builder().
                name(payment.getName())
                .orderNumber(payment.getOrderNumber())
                .amount(payment.getAmount())
                .razorpayOrderId(payment.getRazorpayOrderId())
                .email(payment.getEmail())
                .build();


        return paymentResponseDto;
    }
}
