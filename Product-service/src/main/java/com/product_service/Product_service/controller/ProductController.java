package com.product_service.Product_service.controller;


import com.product_service.Product_service.dto.CreateProductRequestDto;
import com.product_service.Product_service.dto.ProductResponseDto;
import com.product_service.Product_service.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    public ProductService productService;

    @GetMapping("/get-product")
    public ResponseEntity<Page<ProductResponseDto>> getProduct(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String categoryId,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(defaultValue = "0")     int page,
            @RequestParam(defaultValue = "10")    int size,
            @RequestParam(defaultValue = "createdAt") String sortBy
    ){
        Pageable pageable = PageRequest.of(
                page, size, Sort.by(sortBy).descending()
        );
        return ResponseEntity.ok(
                productService.searchProduct(
                        name, categoryId, minPrice, maxPrice, pageable
                )
        );
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponseDto> getProduct(
            @PathVariable String productId
    ) {
        return ResponseEntity.ok(productService.getProductByProductId(productId));
    }

    @PostMapping("/create")
    public ResponseEntity<ProductResponseDto> createProduct(
            @Valid @RequestBody CreateProductRequestDto request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(productService.createProduct(request));
    }
}
