package com.flipkart.authservice.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExternalConfig {

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
