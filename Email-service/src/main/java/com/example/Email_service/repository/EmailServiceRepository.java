package com.example.Email_service.repository;

import com.example.Email_service.entity.EmailTemplateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailServiceRepository extends JpaRepository<EmailTemplateEntity, Long> {

}
