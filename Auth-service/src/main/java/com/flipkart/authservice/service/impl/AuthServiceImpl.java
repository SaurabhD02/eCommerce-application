package com.flipkart.authservice.service.impl;

import com.flipkart.authservice.dto.SendOtpRequest;
import com.flipkart.authservice.dto.SendOtpResponse;
import com.flipkart.authservice.dto.VerifyOtpRequest;
import com.flipkart.authservice.dto.VerifyOtpResponse;
import com.flipkart.authservice.entity.User;
import com.flipkart.authservice.repository.UserRepository;
import com.flipkart.authservice.service.AuthService;
import com.flipkart.authservice.service.OtpVerificationService;
import com.flipkart.authservice.service.SmsService;
import com.flipkart.authservice.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Value("${isTwilioEnabled}")
    private boolean isTwilioEnabled;

    @Autowired
    private SmsService smsService;

    @Autowired
    private OtpVerificationService otpVerificationService;

    @Override
    public SendOtpResponse sendOtp(SendOtpRequest sendOtpRequest) {
        SendOtpResponse sendOtpResponse = null;
        String messageBody = otpVerificationService.createOtp(sendOtpRequest.getMobile());
            if(isTwilioEnabled) {
                sendOtpResponse = smsService.sendSms(sendOtpRequest.getMobile(), messageBody);
            }
        return sendOtpResponse;
    }

    @Override
    public VerifyOtpResponse verifyOtp(VerifyOtpRequest verifyOtpRequest) {
        VerifyOtpResponse  verifyOtpResponse = otpVerificationService.verifyOtp(verifyOtpRequest.getMobile(), verifyOtpRequest.getOtp());
        if(verifyOtpResponse.isValid()) {
            Optional<User> optionalUser = userRepository.findByMobile(verifyOtpRequest.getMobile());
              if(optionalUser.isPresent()) {
                  String token = jwtUtil.generateToken(optionalUser.get());
                  verifyOtpResponse.setToken(token);
                  return verifyOtpResponse;
            } else {
                  User user = User.builder()
                          .mobile(verifyOtpRequest.getMobile())
                          .build();
                  User savedUser = userRepository.save(user);
                  String userCode = String.format("CUS%06d", savedUser.getUserId().toString());
                  savedUser.setUserCode(userCode);
                  userRepository.save(savedUser);
                  String token = jwtUtil.generateToken(savedUser);
                  verifyOtpResponse.setToken(token);
                  return verifyOtpResponse;
              }
        }
        return verifyOtpResponse;
    }
}
