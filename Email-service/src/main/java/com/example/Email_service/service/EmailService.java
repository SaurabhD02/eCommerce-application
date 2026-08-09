package com.example.Email_service.service;

import com.example.Email_service.dto.EmailTemplateDto;
import com.example.Email_service.dto.SendEmailReqDto;
import com.example.Email_service.dto.SendEmailResDto;

public interface EmailService {

    public SendEmailResDto sendEmail(SendEmailReqDto sendEmailDto);

    public EmailTemplateDto saveTemplate(EmailTemplateDto emailTemplateDto);
}
