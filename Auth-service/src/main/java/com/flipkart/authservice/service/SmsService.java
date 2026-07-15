package com.flipkart.authservice.service;

import com.flipkart.authservice.dto.SendOtpResponse;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SmsService {

    @Value("${twilio.phone.number}")
    private String twilioNumber;

    @Value("${twilio.account.sid}")
    private String accountSid;

    @Value("${twilio.auth.token}")
    private String authToken;

    @PostConstruct
    public void init() {
        Twilio.init(accountSid, authToken);
    }

    public SendOtpResponse sendSms(String mobileNumber, String messageBody) {
        SendOtpResponse sendOtpResponse = new SendOtpResponse();
        try {
            Message.creator(
                            new PhoneNumber(mobileNumber),
                            new PhoneNumber(twilioNumber),
                            messageBody)
                    .create();
            sendOtpResponse.setSuccess(true);
        } catch( Exception e){
            throw new RuntimeException(e);
        }
        return sendOtpResponse;
    }
}
