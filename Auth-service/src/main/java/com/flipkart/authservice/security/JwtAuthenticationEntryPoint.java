package com.flipkart.authservice.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        authException.printStackTrace();
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        Map<String, Object> errorObject = new HashMap<>();
        errorObject.put("message", "Unauthorized access of protected resource, Invalid credentials");
        errorObject.put("error", HttpStatus.UNAUTHORIZED);
        errorObject.put("code", HttpStatus.UNAUTHORIZED.value());
        errorObject.put("timestamp", new Timestamp(new Date().getTime()));
        response.getWriter().write(new ObjectMapper().writeValueAsString(errorObject));
    }
}

