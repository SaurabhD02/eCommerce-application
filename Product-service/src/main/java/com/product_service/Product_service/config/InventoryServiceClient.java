package com.product_service.Product_service.config;

import com.product_service.Product_service.dto.InventoryResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Configuration
@FeignClient(name = "INVENTORY-SERVICE", path = "/inventory")
public interface InventoryServiceClient {
    @GetMapping("/getByProductId/{productId}")
    public InventoryResponseDto getInventoryByProductId(@PathVariable String productId);

    @PostMapping("/getInventoryByStock")
    public List<InventoryResponseDto> getInventoryStock(@RequestBody List<String> productIds);
}
