package com.example.wheycenter.repository;

import com.example.wheycenter.model.ProductFlavor;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductFlavorRepository extends JpaRepository<ProductFlavor, Integer> {
    List<ProductFlavor> findByProduct_ProductId(String productId);
}
