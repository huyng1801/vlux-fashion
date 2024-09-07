package com.example.wheycenter.dto;

public class ProductFlavorDto {
    private Integer flavorId;
    private String flavorName;
    private Integer stockQuantity;
    private String productId;  // Add productId

    // Constructor, Getters, and Setters

    public ProductFlavorDto(Integer flavorId, String flavorName, Integer stockQuantity, String productId) {
        this.flavorId = flavorId;
        this.flavorName = flavorName;
        this.stockQuantity = stockQuantity;
        this.productId = productId;  // Set productId
    }

    public Integer getFlavorId() {
        return flavorId;
    }

    public void setFlavorId(Integer flavorId) {
        this.flavorId = flavorId;
    }

    public String getFlavorName() {
        return flavorName;
    }

    public void setFlavorName(String flavorName) {
        this.flavorName = flavorName;
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
}
