package vn.student.vluxfashion.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import vn.student.vluxfashion.dto.CategoryDto;
import vn.student.vluxfashion.exception.ResourceNotFoundException;
import vn.student.vluxfashion.response.CategoryResponse;
import vn.student.vluxfashion.service.CategoryService;
import vn.student.vluxfashion.util.ValidationUtils;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAllCategories() {
        List<CategoryResponse> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable("id") Integer categoryId) {
        CategoryResponse categoryResponse = categoryService.getCategoryById(categoryId);
        if (categoryResponse == null) {
            throw new ResourceNotFoundException("Category with ID " + categoryId + " not found");
        }
        return ResponseEntity.ok(categoryResponse);
    }

    @PostMapping
    public ResponseEntity<?> createCategory(@RequestBody @Valid CategoryDto categoryDto, BindingResult result) {
        // Check for validation errors
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(ValidationUtils.getErrorMessages(result));
        }

        // Check if the category name already exists
        if (categoryService.isCategoryNameExists(categoryDto.getCategoryName())) {
            return ResponseEntity.badRequest().body("Category name already exists");
        }

        // Attempt to create the category
        CategoryResponse createdCategory = categoryService.createCategory(categoryDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable("id") Integer categoryId, @RequestBody @Valid CategoryDto categoryDto, BindingResult result) {
        // Check for validation errors
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(ValidationUtils.getErrorMessages(result));
        }

        // Check if the category exists
        if (categoryService.getCategoryById(categoryId) == null) {
            throw new ResourceNotFoundException("Category with ID " + categoryId + " not found");
        }

        // Check if the category name already exists for other IDs
        if (categoryService.isCategoryNameExistsForOtherId(categoryDto.getCategoryName(), categoryId)) {
            return ResponseEntity.badRequest().body("Category name already exists for another category");
        }

        // Attempt to update the category
        CategoryResponse updatedCategory = categoryService.updateCategory(categoryId, categoryDto);
        return ResponseEntity.ok(updatedCategory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable("id") Integer categoryId) {
        boolean isDeleted = categoryService.deleteCategory(categoryId);
        if (!isDeleted) {
            throw new ResourceNotFoundException("Category with ID " + categoryId + " not found");
        }
        return ResponseEntity.noContent().build();
    }
}
