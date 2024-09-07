package com.example.wheycenter.response;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class ProductResponse {
    private String productId;
    private String productName;
    private String description;
    private BigDecimal originalPrice;
    private BigDecimal unitPrice;
    private Boolean isVisible;
    private Date createdAt;
    private Date updatedAt;
    private OriginResponse origin;
    private ManufacturerResponse manufacturer;
    private List<ProductFlavorResponse> flavors;
    private List<ProductImageResponse> images;

    // Getters and Setters

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(BigDecimal originalPrice) {
        this.originalPrice = originalPrice;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Boolean getIsVisible() {
        return isVisible;
    }

    public void setIsVisible(Boolean isVisible) {
        this.isVisible = isVisible;
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

    public OriginResponse getOrigin() {
        return origin;
    }

    public void setOrigin(OriginResponse origin) {
        this.origin = origin;
    }

    public ManufacturerResponse getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(ManufacturerResponse manufacturer) {
        this.manufacturer = manufacturer;
    }

    public List<ProductFlavorResponse> getFlavors() {
        return flavors;
    }

    public void setFlavors(List<ProductFlavorResponse> flavors) {
        this.flavors = flavors;
    }

    public List<ProductImageResponse> getImages() {
        return images;
    }

    public void setImages(List<ProductImageResponse> images) {
        this.images = images;
    }
}
