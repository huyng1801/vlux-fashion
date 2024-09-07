package com.example.wheycenter.service;

import com.example.wheycenter.dto.CategoryDto;
import com.example.wheycenter.model.Category;
import com.example.wheycenter.model.Product;
import com.example.wheycenter.repository.CategoryRepository;
import com.example.wheycenter.repository.ProductRepository;
import com.example.wheycenter.response.CategoryResponse;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(this::convertToCategoryResponse)
                .collect(Collectors.toList());
    }

    public CategoryResponse getCategoryById(String categoryId) {
        return categoryRepository.findById(categoryId)
                .map(this::convertToCategoryResponse)
                .orElse(null);
    }

    public CategoryResponse createCategory(CategoryDto categoryDto) {
        Category category = new Category();
        category.setCategoryId(categoryDto.getCategoryId()); 
        category.setCategoryName(categoryDto.getCategoryName());
        category.setCreatedAt(new Date());
        category.setUpdatedAt(new Date());
        Category savedCategory = categoryRepository.save(category);
        return convertToCategoryResponse(savedCategory);
    }

    public CategoryResponse updateCategory(String categoryId, CategoryDto categoryDto) {
        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
        if (optionalCategory.isEmpty()) {
            return null;
        }
        Category category = optionalCategory.get();
        category.setCategoryName(categoryDto.getCategoryName());
        category.setUpdatedAt(new Date());
        Category updatedCategory = categoryRepository.save(category);
        return convertToCategoryResponse(updatedCategory);
    }

    public void deleteCategory(String categoryId) {
        categoryRepository.deleteById(categoryId);
    }

    private CategoryResponse convertToCategoryResponse(Category category) {
        List<String> productIds = (category.getProducts() != null) ? 
                category.getProducts().stream()
                        .map(Product::getProductId)
                        .collect(Collectors.toList()) 
                : Collections.emptyList(); 
    
        int numberOfProducts = productIds.size();
        
        return new CategoryResponse(
                category.getCategoryId(),
                category.getCategoryName(),
                category.getCreatedAt(),
                category.getUpdatedAt(),
                productIds,
                numberOfProducts
        );
    }
    
}
