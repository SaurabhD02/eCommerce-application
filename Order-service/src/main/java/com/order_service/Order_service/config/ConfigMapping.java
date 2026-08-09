package com.order_service.Order_service.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigMapping {

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
