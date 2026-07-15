package com.flipkart.authservice.dto;

import com.flipkart.authservice.entity.Role;
import lombok.Data;

import java.util.Set;

@Data
public class UserRegistrationRequest {

    private int userId;
    private String userName;
    private String password;
    private String email;
    private Set<Role> roles;
    private String mobile;
}
