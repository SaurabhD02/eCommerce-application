package com.flipkart.authservice.service;

import com.flipkart.authservice.dto.VerifyOtpResponse;
import com.flipkart.authservice.entity.OtpVerification;
import com.flipkart.authservice.exception.OtpException;
import com.flipkart.authservice.repository.OtpVerificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class OtpVerificationService {

    private final int MAX_RETRY_ATTEMPTS = 3;

    @Autowired
    private OtpVerificationRepository otpVerificationRepository;

    @Value("${isTwilioEnabled}")
    private boolean isTwilioEnabled;

    public String createOtp(String mobileNumber){
        String otp = String.valueOf(ThreadLocalRandom.current().nextInt(100000, 999999));
        OtpVerification otpVerification = OtpVerification.builder()
                .mobileNumber(mobileNumber)
                .otp(otp)
                .retryCount(0)
                .expirationTime(LocalDateTime.now().plusMinutes(5))
                .build();

        otpVerificationRepository.save(otpVerification);

        return "Your OTP for logging into Ekart is " + otp + ". This code is valid for 5 minutes. Do not" +
                " share this OTP with anyone for security reasons.";
    }

    public VerifyOtpResponse verifyOtp(String mobile, String otp){
        VerifyOtpResponse verifyOtpResponse = new VerifyOtpResponse();
        if(null != otp && !StringUtils.isEmpty(otp)) {
            if(isTwilioEnabled) {
                Optional<OtpVerification> otpVerificationOptional = otpVerificationRepository.findByMobileNumber(mobile);
                if (otpVerificationOptional.isPresent()) {

                    //Further to implements with Redis cache for faster implementation
                    OtpVerification otpVerification = otpVerificationOptional.get();
                    otpVerification.setRetryCount(otpVerification.getRetryCount() + 1);

                    if(otpVerification.getRetryCount() >= MAX_RETRY_ATTEMPTS){
                        otpVerificationRepository.deleteById(otpVerificationOptional.get().getId());
                        throw new OtpException("Maximum attempts exceeded. Please request a new OTP");
                    }
                    if (!otpVerification.getOtp().equals(otp)) {
                        otpVerificationRepository.save(otpVerification);
                        throw new OtpException("Invalid OTP entered. Remaining attempts " + (MAX_RETRY_ATTEMPTS - otpVerification.getRetryCount()));
                    }

                    if (otpVerification.getExpirationTime().isBefore(LocalDateTime.now())) {
                        otpVerificationRepository.deleteById(otpVerificationOptional.get().getId());
                        throw new OtpException(" OTP is expired, Please generate a new one");
                    }

                    verifyOtpResponse.setValid(true);
                    verifyOtpResponse.setMessage("OTP verified successfully");

                    if(verifyOtpResponse.isValid())
                        otpVerificationRepository.deleteById(otpVerificationOptional.get().getId());

                    return verifyOtpResponse;
                } else {
                    throw new OtpException("Invalid OTP number");
                }
            } else if(otp.equals("12345")) {
                verifyOtpResponse.setValid(true);
                verifyOtpResponse.setMessage("OTP verified successfully");
            }
        }
        return verifyOtpResponse;
    }
}
