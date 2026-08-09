package com.product_service.Product_service.service;

import com.product_service.Product_service.dto.CategoryResDto;
import com.product_service.Product_service.dto.CreateCategoryReqDto;

public interface CategoryService {

    public CategoryResDto createCategory(CreateCategoryReqDto createCategoryReqDto);
}
