package com.example.wheycenter.response;

import java.util.Date;

public class OriginResponse {

    private Integer originId;
    private String country;
    private Date createdAt;
    private Date updatedAt;

    public OriginResponse(Integer originId, String country, Date createdAt, Date updatedAt) {
        this.originId = originId;
        this.country = country;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters and Setters

    public Integer getOriginId() {
        return originId;
    }

    public void setOriginId(Integer originId) {
        this.originId = originId;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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
