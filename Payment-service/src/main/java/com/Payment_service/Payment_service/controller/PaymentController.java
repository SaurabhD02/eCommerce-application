package com.Payment_service.Payment_service.controller;

import com.Payment_service.Payment_service.dto.PaymentDto;
import com.Payment_service.Payment_service.dto.PaymentResponseDto;
import com.Payment_service.Payment_service.service.PaymentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/payments")
public class PaymentController {

    @Value("${razorpay.key.id}")
    private String KEY_ID;

    @Value("${razorpay.key.secret}")
    private String KEY_SECRET;

    @Autowired
    PaymentService paymentService;

    @Autowired
    ObjectMapper objectMapper;

    @PostMapping("/create-order")
    public ResponseEntity<PaymentResponseDto> createOrder(@RequestBody PaymentDto paymentDto) throws RazorpayException {
        PaymentResponseDto paymentResponseDto = paymentService.createPayment(paymentDto);

        System.out.println("paymentResponseDto>>>>>>" + paymentResponseDto);

        return new ResponseEntity<>(paymentResponseDto, HttpStatus.OK);
    }

    @GetMapping("/process-payment/{orderNumber}")
    public String processPayment(@PathVariable("orderNumber") String orderNumber, Model model) throws JsonProcessingException {
        PaymentResponseDto paymentResponseDto = paymentService.processPayment(orderNumber);

        System.out.println("paymentResponseDto111>>>>>>" + paymentResponseDto);

        // Serialize to JSON so the Thymeleaf template can use it as a real JS object
        model.addAttribute("paymentResponseDtoJson", objectMapper.writeValueAsString(paymentResponseDto));
        model.addAttribute("paymentResponseDto", paymentResponseDto);
        model.addAttribute("key", KEY_ID);

        return "index";
    }

    @PostMapping("/payment-callback/{orderNumber}")
    public RedirectView paymentCallback(
            @RequestParam("razorpay_order_id") String razorpayOrderId,
            @RequestParam("razorpay_payment_id") String razorpayPaymentId,
            @RequestParam("razorpay_signature") String razorpaySignature,
            @PathVariable("orderNumber") String orderNumber) throws RazorpayException {
        try {
            // Verify the payment signature here
            String signature = razorpayOrderId + "|" + razorpayPaymentId;
            boolean isValid = Utils.verifySignature(signature, razorpaySignature, KEY_SECRET);

            System.out.println("Hey I am here in the callback");

            if (isValid) {
                // Payment successful
                return new RedirectView("/success.html?orderId=" + razorpayOrderId);
            } else {
                // Payment failed
                return new RedirectView("/failure.html"); // Create failure.html if needed
            }
        } catch (RazorpayException e) {
            System.err.println("Razorpay Exception during callback: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            System.err.println("General Exception during callback: " + e.getMessage());
            throw new RazorpayException("General exception during callback");
        }
    }

    @PostMapping("/get-key")
    @ResponseBody
    public String getKey() {
        return KEY_ID;
    }
}
