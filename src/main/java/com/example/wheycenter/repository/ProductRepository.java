package com.example.wheycenter.repository;

import com.example.wheycenter.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, String>{
    
}
