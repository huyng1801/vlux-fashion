package vn.student.vluxfashion.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class OriginDto {

    @NotEmpty(message = "Country name is required")
    @Size(max = 50, message = "Country name cannot exceed 50 characters")
    private String country;

    // Getters and Setters

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
