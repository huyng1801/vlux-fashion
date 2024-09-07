package com.example.wheycenter.response;


public class ProductFlavorResponse {
    private Integer flavorId;
    private String flavorName;
    private Integer stockQuantity;

    
    public ProductFlavorResponse(Integer flavorId, String flavorName, Integer stockQuantity) {
        this.flavorId = flavorId;
        this.flavorName = flavorName;
        this.stockQuantity = stockQuantity;
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

  
}
