package vn.student.vluxfashion.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.student.vluxfashion.dto.SubCategoryDto;
import vn.student.vluxfashion.response.SubCategoryResponse;
import vn.student.vluxfashion.exception.ResourceNotFoundException;
import vn.student.vluxfashion.model.Category;
import vn.student.vluxfashion.model.Gender;
import vn.student.vluxfashion.model.SubCategory;
import vn.student.vluxfashion.repository.CategoryRepository;
import vn.student.vluxfashion.repository.SubCategoryRepository;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubCategoryService {

    @Autowired
    private SubCategoryRepository subCategoryRepository;

    @Autowired
    private CategoryRepository categoryRepository;
    
    public List<SubCategoryResponse> getAllSubCategories() {
        List<SubCategory> subCategories = subCategoryRepository.findAll();
        return subCategories.stream().map(this::mapToResponse).collect(Collectors.toList());
    }
  // Fetch subcategories based on categoryId and/or gender
    public List<SubCategoryResponse> getSubCategories(Integer categoryId, Gender gender) {
        if (categoryId == null && gender == null) {
            // Case 1: Both categoryId and gender are null
            return getAllSubCategories();
        } else if (categoryId != null && gender == null) {
            // Case 2: Only categoryId is provided
            return getSubCategoriesByCategoryId(categoryId);
        } else if (categoryId == null && gender != null) {
            // Case 3: Only gender is provided
            return getSubCategoriesByGender(gender);
        } else {
            // Case 4: Both categoryId and gender are provided
            return getSubCategoriesByCategoryIdAndGender(categoryId, gender);
        }
    }

    public List<SubCategoryResponse> getSubCategoriesByCategoryId(Integer categoryId) {
        List<SubCategory> subCategories = subCategoryRepository.findByCategory_CategoryId(categoryId);
        return subCategories.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    public List<SubCategoryResponse> getSubCategoriesByGender(Gender gender) {
        List<SubCategory> subCategories = subCategoryRepository.findByGender(gender);
        return subCategories.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    public List<SubCategoryResponse> getSubCategoriesByCategoryIdAndGender(Integer categoryId, Gender gender) {
        List<SubCategory> subCategories = subCategoryRepository.findByCategory_CategoryIdAndGender(categoryId, gender);
        return subCategories.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    // Fetch subcategory by ID
    public SubCategoryResponse getSubCategoryById(Integer id) {
        SubCategory subCategory = subCategoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sub-category with ID " + id + " not found"));
        return mapToResponse(subCategory);
    }

    // Create subcategory
    public SubCategoryResponse createSubCategory(SubCategoryDto subCategoryDto) {
        SubCategory subCategory = new SubCategory();
        subCategory.setSubCategoryName(subCategoryDto.getSubCategoryName());
        subCategory.setGender(subCategoryDto.getGender());
        subCategory.setCreatedAt(new Date());
        subCategory.setUpdatedAt(new Date());

        // Find the category
        Category category = categoryRepository.findById(subCategoryDto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category with ID " + subCategoryDto.getCategoryId() + " not found"));
        subCategory.setCategory(category);

        SubCategory savedSubCategory = subCategoryRepository.save(subCategory);
        return mapToResponse(savedSubCategory);
    }

    // Update subcategory
    public SubCategoryResponse updateSubCategory(Integer id, SubCategoryDto subCategoryDto) {
        SubCategory subCategory = subCategoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sub-category with ID " + id + " not found"));

        subCategory.setSubCategoryName(subCategoryDto.getSubCategoryName());
        subCategory.setGender(subCategoryDto.getGender());
        subCategory.setUpdatedAt(new Date());

        // Update the category if needed
        Category category = categoryRepository.findById(subCategoryDto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category with ID " + subCategoryDto.getCategoryId() + " not found"));
        subCategory.setCategory(category);

        SubCategory updatedSubCategory = subCategoryRepository.save(subCategory);
        return mapToResponse(updatedSubCategory);
    }

    // Delete subcategory
    public void deleteSubCategory(Integer id) {
        SubCategory subCategory = subCategoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sub-category with ID " + id + " not found"));
        subCategoryRepository.delete(subCategory);
    }
    
    
    // Helper method to map SubCategory to SubCategoryResponse
    private SubCategoryResponse mapToResponse(SubCategory subCategory) {
        SubCategoryResponse response = new SubCategoryResponse();
        response.setSubCategoryId(subCategory.getSubCategoryId());
        response.setSubCategoryName(subCategory.getSubCategoryName());
        response.setGender(subCategory.getGender().toString());
        response.setCreatedAt(subCategory.getCreatedAt());
        response.setUpdatedAt(subCategory.getUpdatedAt());
        response.setCategoryName(subCategory.getCategory().getCategoryName()); // Category name from category object
        return response;
    }
}
