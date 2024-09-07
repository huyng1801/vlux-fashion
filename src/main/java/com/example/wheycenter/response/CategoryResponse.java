package com.example.wheycenter.response;

import java.util.Date;
import java.util.List;

public class CategoryResponse {

    private String categoryId;
    private String categoryName;
    private Date createdAt;
    private Date updatedAt;
    private List<String> products; // List of product IDs or product details
    private int numberOfProducts;

    // Constructors
    public CategoryResponse() {
    }

    public CategoryResponse(String categoryId, String categoryName, Date createdAt, Date updatedAt, List<String> products, int numberOfProducts) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.products = products;
        this.numberOfProducts = numberOfProducts;
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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<String> getProducts() {
        return products;
    }

    public void setProducts(List<String> products) {
        this.products = products;
    }

    public int getNumberOfProducts() {
        return numberOfProducts;
    }

    public void setNumberOfProducts(int numberOfProducts) {
        this.numberOfProducts = numberOfProducts;
    }
}
