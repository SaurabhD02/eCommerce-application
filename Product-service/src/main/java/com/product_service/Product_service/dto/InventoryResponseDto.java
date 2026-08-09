package com.product_service.Product_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryResponseDto {
    private String productId;
    private String status;

    private Integer quantityAvailable;
    private Integer quantityReserved;   // Held for pending orders
    private Integer quantityThreshold;  // Low stock alert level
    private String warehouseLocation;

}
