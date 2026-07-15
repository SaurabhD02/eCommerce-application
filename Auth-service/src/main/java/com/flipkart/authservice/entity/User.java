package com.flipkart.authservice.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private String userId;

    @Column
    private String mobile;

    @Column(name = "user_code")
    private String userCode;

    private String email;

    private String role = "CUSTOMER";
}
