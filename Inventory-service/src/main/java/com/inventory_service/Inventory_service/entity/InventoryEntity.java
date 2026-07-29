package com.inventory_service.Inventory_service.entity;

import com.inventory_service.Inventory_service.dto.StockStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Inventory")
public class InventoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String productId;     // Reference to Product Service

    private Integer quantityAvailable;
    private Integer quantityReserved;   // Held for pending orders
    private Integer quantityThreshold;  // Low stock alert level
    private String warehouseLocation;

    private StockStatus status;
}
