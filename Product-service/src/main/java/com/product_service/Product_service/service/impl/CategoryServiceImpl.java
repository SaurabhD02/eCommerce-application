package com.product_service.Product_service.service.impl;

import com.product_service.Product_service.dto.CategoryResDto;
import com.product_service.Product_service.dto.CreateCategoryReqDto;
import com.product_service.Product_service.entity.Category;
import com.product_service.Product_service.repository.CategoryRepository;
import com.product_service.Product_service.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryResDto createCategory(CreateCategoryReqDto createCategoryReqDto){

        Category category = Category.builder()
                        .name(createCategoryReqDto.getName())
                        .description(createCategoryReqDto.getDescription()).build();
        Category createdCategory = categoryRepository.save(category);

        CategoryResDto categoryResDto = modelMapper.map(createdCategory, CategoryResDto.class);
        return categoryResDto;
    }
}
