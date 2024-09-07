package com.example.wheycenter.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

public class BannerDto {

    @NotEmpty(message = "Title cannot be empty")
    @Size(max = 100, message = "Title cannot exceed 100 characters")
    private String title;

    @Size(max = 512, message = "Link cannot exceed 512 characters")
    private String link;

    @NotNull(message = "Visibility status cannot be null")
    private Boolean isVisible;

    private MultipartFile imageFile;  // Add this line

    // Getters and Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public MultipartFile getImageFile() {
        return imageFile;
    }

    public void setImageFile(MultipartFile imageFile) {
        this.imageFile = imageFile;
    }
}
