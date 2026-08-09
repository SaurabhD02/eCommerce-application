package com.product_service.Product_service.controller;

import com.product_service.Product_service.dto.CategoryResDto;
import com.product_service.Product_service.dto.CreateCategoryReqDto;
import com.product_service.Product_service.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product-category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping("/create")
    public ResponseEntity<CategoryResDto> createCategory(@RequestBody CreateCategoryReqDto createCategoryReqDto){
        return new ResponseEntity<>(categoryService.createCategory(createCategoryReqDto), HttpStatus.CREATED);
    }

}
