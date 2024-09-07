package com.example.wheycenter.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.wheycenter.model.ProductFlavor;

public interface ProductFlavorRepository extends JpaRepository<ProductFlavor, Integer> {
}
