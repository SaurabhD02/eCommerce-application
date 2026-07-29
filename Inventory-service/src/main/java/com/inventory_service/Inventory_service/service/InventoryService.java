package com.inventory_service.Inventory_service.service;

import com.inventory_service.Inventory_service.dto.InventoryDto;
import org.springframework.stereotype.Service;


public interface InventoryService {

    public InventoryDto createInventory(InventoryDto inventoryDto);
    public InventoryDto findByProductId(String productId);
}
