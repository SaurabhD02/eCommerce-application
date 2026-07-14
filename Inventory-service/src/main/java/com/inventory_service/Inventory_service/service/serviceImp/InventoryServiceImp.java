package com.inventory_service.Inventory_service.service.serviceImp;

import com.inventory_service.Inventory_service.dto.InventoryDto;
import com.inventory_service.Inventory_service.entity.InventoryEntity;
import com.inventory_service.Inventory_service.repository.InventoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import com.inventory_service.Inventory_service.service.InventoryService;
import org.springframework.stereotype.Service;

@Service
public class InventoryServiceImp implements InventoryService{
    public InventoryRepository inventoryRepository;
    public ModelMapper modelMapper;

    @Autowired
    public InventoryServiceImp(InventoryRepository inventoryRepository, ModelMapper modelMapper){
        super();
        this.inventoryRepository = inventoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public InventoryDto createInventory(InventoryDto inventoryDto){
        InventoryEntity inventory = modelMapper.map(inventoryDto, InventoryEntity.class);
        InventoryEntity saveInventory = inventoryRepository.save(inventory);
        return modelMapper.map(saveInventory, InventoryDto.class);
    };

    @Override
    public InventoryDto findByProductId(String id){
        InventoryEntity inventory = inventoryRepository.findByProductId(id);
        return modelMapper.map(inventory, InventoryDto.class);
    }

}
