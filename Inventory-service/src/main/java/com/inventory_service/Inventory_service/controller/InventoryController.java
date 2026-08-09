package com.inventory_service.Inventory_service.controller;

import com.inventory_service.Inventory_service.dto.InventoryDto;
import com.inventory_service.Inventory_service.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.util.List;

@RestController
@RequestMapping("/inventory")
public class InventoryController {
    @Autowired
    private InventoryService inventoryService;

    @PostMapping("/save")
    public ResponseEntity<InventoryDto> saveInventory(@RequestBody InventoryDto inventoryDto){
        return new ResponseEntity<>(inventoryService.createInventory(inventoryDto), HttpStatus.CREATED);
    }

    @GetMapping("/getByProductId/{productId}")
    public ResponseEntity<InventoryDto> getByProduct(@PathVariable String productId){
        return new ResponseEntity<>(inventoryService.findByProductId(productId), HttpStatus.OK);
    }

    @PostMapping("/getInventoryByStock")
    public ResponseEntity<List<InventoryDto>> getByProductIds(@RequestBody List<String> productIds){
        return new ResponseEntity<>(inventoryService.getInventoryByProductIds(productIds), HttpStatus.OK);
    }
}
