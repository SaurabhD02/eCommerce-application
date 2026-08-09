package com.product_service.Product_service.config;

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
