package com.example.wheycenter.controller;

import com.example.wheycenter.model.Category;
import com.example.wheycenter.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryService.findAllCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable("id") String categoryId) {
        Optional<Category> category = categoryService.findCategoryById(categoryId);
        return category.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        Category createdCategory = categoryService.saveCategory(category);
        return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable("id") String categoryId, @RequestBody Category category) {
        if (!categoryService.findCategoryById(categoryId).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        category.setCategoryId(categoryId);
        Category updatedCategory = categoryService.saveCategory(category);
        return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable("id") String categoryId) {
        if (!categoryService.findCategoryById(categoryId).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.noContent().build();
    }
}
