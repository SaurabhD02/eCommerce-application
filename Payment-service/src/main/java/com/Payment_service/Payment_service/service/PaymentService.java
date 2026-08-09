package com.Payment_service.Payment_service.service;

import com.Payment_service.Payment_service.dto.PaymentDto;
import com.Payment_service.Payment_service.dto.PaymentResponseDto;
import com.razorpay.RazorpayException;

public interface PaymentService {

    public PaymentResponseDto createPayment(PaymentDto paymentDto) throws RazorpayException;
    public PaymentResponseDto processPayment(String orderNumber);
}
