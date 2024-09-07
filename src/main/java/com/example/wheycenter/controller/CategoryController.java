package com.example.wheycenter.controller;

import com.example.wheycenter.dto.CategoryDto;
import com.example.wheycenter.response.CategoryResponse;
import com.example.wheycenter.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAllCategories() {
        List<CategoryResponse> categories = categoryService.getAllCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable("id") String categoryId) {
        CategoryResponse categoryResponse = categoryService.getCategoryById(categoryId);
        return categoryResponse != null ? ResponseEntity.ok(categoryResponse) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(@RequestBody CategoryDto categoryDto) {
        CategoryResponse createdCategory = categoryService.createCategory(categoryDto);
        return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> updateCategory(@PathVariable("id") String categoryId, @RequestBody CategoryDto categoryDto) {
        CategoryResponse existingCategory = categoryService.getCategoryById(categoryId);
        if (existingCategory == null) {
            return ResponseEntity.notFound().build();
        }
        CategoryResponse updatedCategory = categoryService.updateCategory(categoryId, categoryDto);
        return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable("id") String categoryId) {
        CategoryResponse existingCategory = categoryService.getCategoryById(categoryId);
        if (existingCategory == null) {
            return ResponseEntity.notFound().build();
        }
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.noContent().build();
    }
}
