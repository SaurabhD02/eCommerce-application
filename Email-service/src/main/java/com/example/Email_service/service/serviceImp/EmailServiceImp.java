package com.example.Email_service.service.serviceImp;

import com.example.Email_service.dto.EmailTemplateDto;
import com.example.Email_service.dto.SendEmailReqDto;
import com.example.Email_service.dto.SendEmailResDto;
import com.example.Email_service.entity.EmailTemplateEntity;
import com.example.Email_service.repository.EmailServiceRepository;
import com.example.Email_service.service.EmailService;
import jakarta.mail.internet.MimeMessage;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImp implements EmailService {

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private EmailServiceRepository emailServiceRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public SendEmailResDto sendEmail(SendEmailReqDto sendEmailReqDto) {
        try{
//            System.out.println("Hiii"+ sendEmailReqDto.toString());
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.addTo(sendEmailReqDto.to);
            helper.setFrom("noreply@gmail.com");
            helper.setSubject(sendEmailReqDto.getSubject());
            helper.setText(sendEmailReqDto.emailContent, true);

            mailSender.send(message);
            return new SendEmailResDto(true, "Mail send successfully");

        }catch (Exception e){
            System.out.println("Exception in send mail " + e.getMessage());
            return new SendEmailResDto(false, "Error sending email");
        }
    }

    @Override
    public EmailTemplateDto saveTemplate(EmailTemplateDto emailTemplateDto) {
        EmailTemplateEntity emailTemplateEntity = emailServiceRepository.save(modelMapper.map(emailTemplateDto, EmailTemplateEntity.class));
        return modelMapper.map(emailTemplateEntity, EmailTemplateDto.class);
    }


}
