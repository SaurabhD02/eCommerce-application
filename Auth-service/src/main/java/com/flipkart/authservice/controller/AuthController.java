package com.flipkart.authservice.controller;

import com.flipkart.authservice.constant.EndPointMapper;
import com.flipkart.authservice.dto.SendOtpRequest;
import com.flipkart.authservice.dto.SendOtpResponse;
import com.flipkart.authservice.dto.VerifyOtpRequest;
import com.flipkart.authservice.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping(EndPointMapper.ACCOUNT)
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping(EndPointMapper.SEND_OTP)
    public ResponseEntity<SendOtpResponse> sendOtp(@RequestBody SendOtpRequest sendOtpRequest) {
        return new ResponseEntity<>(authService.sendOtp(sendOtpRequest), HttpStatus.OK);
    }

    @PostMapping(EndPointMapper.VERIFY_OTP)
    public ResponseEntity<?> verifyOtp(@RequestBody VerifyOtpRequest verifyOtpRequest){
        return new ResponseEntity<>(authService.verifyOtp(verifyOtpRequest), HttpStatus.OK);
    }
}
