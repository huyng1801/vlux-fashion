package com.example.wheycenter.repository;

import com.example.wheycenter.model.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, Integer> {

    // Custom query method to find images by product ID
    List<ProductImage> findByProduct_ProductId(String productId);
}
