package com.example.wheycenter.service;

import com.example.wheycenter.model.Category;
import com.example.wheycenter.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> findAllCategories() {
        return categoryRepository.findAll();
    }

    public Optional<Category> findCategoryById(String categoryId) {
        return categoryRepository.findById(categoryId);
    }

    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    public void deleteCategory(String categoryId) {
        categoryRepository.deleteById(categoryId);
    }
}
