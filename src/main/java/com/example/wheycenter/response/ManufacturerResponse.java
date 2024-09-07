package com.example.wheycenter.response;

import java.util.Date;

public class ManufacturerResponse {

    private String manufacturerId;
    private String manufacturerName;
    private String description;
    private Date createdAt;
    private Date updatedAt;

    public ManufacturerResponse(String manufacturerId, String manufacturerName, String description, Date createdAt, Date updatedAt) {
        this.manufacturerId = manufacturerId;
        this.manufacturerName = manufacturerName;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters and Setters

    public String getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(String manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    public String getManufacturerName() {
        return manufacturerName;
    }

    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
}
