package com.product_service.Product_service.service.impl;

import com.product_service.Product_service.Exception.ResourceNotFoundException;
import com.product_service.Product_service.config.InventoryServiceClient;
import com.product_service.Product_service.dto.CreateProductRequestDto;
import com.product_service.Product_service.dto.InventoryResponseDto;
import com.product_service.Product_service.dto.ProductResponseDto;
import com.product_service.Product_service.entity.Category;
import com.product_service.Product_service.entity.Product;
import com.product_service.Product_service.repository.CategoryRepository;
import com.product_service.Product_service.repository.ProductRepository;
import com.product_service.Product_service.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private InventoryServiceClient inventoryServiceClient;
    @Autowired
    private CategoryRepository categoryRepository;
//    private
    @Override
    public Page<ProductResponseDto> searchProduct(String name, String categoryId, BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable) {
        Page<Product> products = productRepository.searchProduct(name, categoryId, minPrice, maxPrice, pageable);

        List<String> productIds = products.stream().map(p -> p.getProductId()).toList();

        List<InventoryResponseDto> inventoryList = null;
        try {
            inventoryList = inventoryServiceClient.getInventoryStock(productIds);
        } catch (Exception e) {
            log.warn("Issue fetching inventory data");
        }


        Map<String, InventoryResponseDto> inventoryMap = new HashMap<>();
        inventoryMap = inventoryList.stream().collect(Collectors.toMap(InventoryResponseDto::getProductId, Function.identity()));

        Map<String, InventoryResponseDto> finalMap = inventoryMap;
        return products.map(product -> {
            InventoryResponseDto inventory =
                    finalMap.get(product.getProductId());
            return toResponse(product, inventory);
        });
    }

    private ProductResponseDto toResponse(Product product, InventoryResponseDto inventoryResponseDto){
        return ProductResponseDto.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .brand(product.getBrand())
                .price(product.getPrice())
                .category(product.getCategory())
                .inStock((inventoryResponseDto.getStatus().equals("IN_STOCK") || inventoryResponseDto.getStatus().equals("LOW_STOCK")))
                .availableQuantity(inventoryResponseDto.getQuantityAvailable())
                .stockStatus(inventoryResponseDto.getStatus())
                .imageUrl(product.getImageUrl())
                .productId(product.getProductId())
                .build();
    }

    @Override
    public ProductResponseDto createProduct(CreateProductRequestDto createProductRequestDto){
        Category category = categoryRepository.findById(createProductRequestDto.getCategoryId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Category not found")
                );

        if (productRepository.findByProductId(createProductRequestDto.getProductId()).isPresent()) {
            throw new RuntimeException("ProductId already exists: " + createProductRequestDto.getProductId());
        }

        Product product = Product.builder()
                .name(createProductRequestDto.getName())
                .description(createProductRequestDto.getDescription())
                .productId(createProductRequestDto.getProductId())
                .price(createProductRequestDto.getPrice())
                .imageUrl(createProductRequestDto.getImageUrl())
                .brand(createProductRequestDto.getBrand())
                .category(category)
                .active(true)
                .build();

        InventoryResponseDto inventory = null;
        try {
            inventory = inventoryServiceClient.getInventoryByProductId(product.getProductId());
        } catch (Exception e) {
            log.error("Inventory call failed", e);
            log.warn("Could not fetch inventory for productId: {}", product.getProductId());
            throw new RuntimeException("Getting error to get product from Inventory");
        }

        log.info("Inventory>>>>>>>", inventory);
        return toResponse(productRepository.save(product), inventory);
    }

    @Override
    public ProductResponseDto getProductByProductId(String productId) {
        Product product = productRepository.findByProductId(productId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product not found: " + productId)
                );

        InventoryResponseDto inventory = null;
        try {
            inventory = inventoryServiceClient.getInventoryByProductId(product.getProductId());
        } catch (Exception e) {
            log.warn("Could not fetch inventory for productId: {}", product.getProductId());
        }

        return toResponse(product, inventory);
    }
}
