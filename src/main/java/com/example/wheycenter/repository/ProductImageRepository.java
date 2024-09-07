package com.example.wheycenter.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.wheycenter.model.ProductImage;

public interface ProductImageRepository extends JpaRepository<ProductImage, Integer> {
    
}
