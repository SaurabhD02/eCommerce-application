package com.inventory_service.Inventory_service.service;

import com.inventory_service.Inventory_service.dto.InventoryDto;
import org.springframework.stereotype.Service;

import java.util.List;


public interface InventoryService {

    public InventoryDto createInventory(InventoryDto inventoryDto);
    public InventoryDto findByProductId(String productId);
    public List<InventoryDto> getInventoryByProductIds(List<String> productIds);
}
