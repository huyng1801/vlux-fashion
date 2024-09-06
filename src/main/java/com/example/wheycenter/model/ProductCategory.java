package com.example.wheycenter.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "product_category")
public class ProductCategory {

    @Id
    @Column(name = "product_id", length = 64) // Specify length if needed
    private String productId;

    @Id
    @Column(name = "category_id", length = 64) // Specify length if needed
    private String categoryId;

    // Getters and Setters

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
}
