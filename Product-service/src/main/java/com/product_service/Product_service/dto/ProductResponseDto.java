package com.product_service.Product_service.dto;

import com.product_service.Product_service.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponseDto {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private String imageUrl;
    private String brand;
    private Category category;
    private String productId;

    // Comes from Inventory Service
    private Integer availableQuantity;
    private boolean inStock;         // true if quantity > 0
    private String stockStatus;
}
