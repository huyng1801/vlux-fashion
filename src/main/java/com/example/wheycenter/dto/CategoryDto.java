package com.example.wheycenter.dto;

public class CategoryDto {

    private String categoryId; // Added field for category ID
    private String categoryName;

    // Constructors
    public CategoryDto() {
    }

    public CategoryDto(String categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    // Getters and Setters
    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
