package com.inventory_service.Inventory_service.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class configMapping {

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
