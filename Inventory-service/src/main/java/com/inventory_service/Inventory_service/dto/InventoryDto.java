package com.inventory_service.Inventory_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InventoryDto {

    private Long id;

    private String productId;     // Reference to Product Service
//    private String sku;

    private Integer quantityAvailable;
    private Integer quantityReserved;   // Held for pending orders
    private Integer quantityThreshold;  // Low stock alert level
    private String warehouseLocation;

    private StockStatus status;
}
