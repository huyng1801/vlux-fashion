package vn.student.vluxfashion.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.student.vluxfashion.dto.CategoryDto;
import vn.student.vluxfashion.model.Category;
import vn.student.vluxfashion.repository.CategoryRepository;
import vn.student.vluxfashion.response.CategoryResponse;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(this::convertToCategoryResponse)
                .collect(Collectors.toList());
    }

    public CategoryResponse getCategoryById(Integer categoryId) {
        return categoryRepository.findById(categoryId)
                .map(this::convertToCategoryResponse)
                .orElse(null);
    }

    public boolean isCategoryNameExists(String categoryName) {
        return categoryRepository.existsByCategoryName(categoryName);
    }

    public boolean isCategoryNameExistsForOtherId(String categoryName, Integer categoryId) {
        return categoryRepository.existsByCategoryNameAndCategoryIdNot(categoryName, categoryId);
    }

    public CategoryResponse createCategory(CategoryDto categoryDto) {
        Category category = new Category();
        category.setCategoryName(categoryDto.getCategoryName());
        category.setCreatedAt(new Date());
        category.setUpdatedAt(new Date());
        Category savedCategory = categoryRepository.save(category);
        return convertToCategoryResponse(savedCategory);
    }

    public CategoryResponse updateCategory(Integer categoryId, CategoryDto categoryDto) {
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

    public boolean deleteCategory(Integer categoryId) {
        if (!categoryRepository.existsById(categoryId)) {
            return false; 
        }
        categoryRepository.deleteById(categoryId);
        return true; 
    }

    private CategoryResponse convertToCategoryResponse(Category category) {
        return new CategoryResponse(
                category.getCategoryId(),
                category.getCategoryName(),
                category.getCreatedAt(),
                category.getUpdatedAt()
        );
    }
}
