package com.flipkart.authservice.service;

import com.flipkart.authservice.dto.SendOtpRequest;
import com.flipkart.authservice.dto.SendOtpResponse;
import com.flipkart.authservice.dto.VerifyOtpRequest;
import com.flipkart.authservice.dto.VerifyOtpResponse;

public interface AuthService {

    public SendOtpResponse sendOtp(SendOtpRequest sendOtpRequest);

    public VerifyOtpResponse verifyOtp(VerifyOtpRequest verifyOtpRequest);

}
