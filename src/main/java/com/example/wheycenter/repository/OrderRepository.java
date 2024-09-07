package com.example.wheycenter.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.wheycenter.model.Order;

public interface OrderRepository extends JpaRepository<Order, String> {
    
}
