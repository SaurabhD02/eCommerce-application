package com.product_service.Product_service.service;

import com.product_service.Product_service.dto.CreateProductRequestDto;
import com.product_service.Product_service.dto.ProductResponseDto;
import com.product_service.Product_service.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.Optional;

public interface ProductService {

    public Page<ProductResponseDto> searchProduct(String name, String categoryId, BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);
    public ProductResponseDto getProductByProductId(String productId);
    public ProductResponseDto createProduct(CreateProductRequestDto createProductRequestDto);
}
