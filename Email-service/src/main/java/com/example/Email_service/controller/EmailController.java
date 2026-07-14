package com.example.Email_service.controller;

import com.example.Email_service.dto.EmailTemplateDto;
import com.example.Email_service.dto.SendEmailReqDto;
import com.example.Email_service.dto.SendEmailResDto;
import com.example.Email_service.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/send")
    public ResponseEntity<SendEmailResDto> sendEmail(@RequestBody SendEmailReqDto sendEmailReqDto){
        return new ResponseEntity<>(emailService.sendEmail(sendEmailReqDto), HttpStatus.OK);
    }

    @PostMapping("/save-template")
    public ResponseEntity<EmailTemplateDto> saveTemplate(@RequestBody EmailTemplateDto emailTemplateDto){
        return new ResponseEntity<>(emailService.saveTemplate(emailTemplateDto), HttpStatus.CREATED);
    }

}
