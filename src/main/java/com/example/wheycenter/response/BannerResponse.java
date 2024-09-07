package com.example.wheycenter.response;

import java.util.Date;

public class BannerResponse {

    private Integer bannerId;
    private String title;
    private String imageUrl;
    private String link;
    private Boolean isVisible;
    private Date createdAt;
    private Date updatedAt;

    // Constructors
    public BannerResponse() {
    }

    public BannerResponse(Integer bannerId, String title, String imageUrl, String link, Boolean isVisible, Date createdAt, Date updatedAt) {
        this.bannerId = bannerId;
        this.title = title;
        this.imageUrl = imageUrl;
        this.link = link;
        this.isVisible = isVisible;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters and Setters
    public Integer getBannerId() {
        return bannerId;
    }

    public void setBannerId(Integer bannerId) {
        this.bannerId = bannerId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
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
}
