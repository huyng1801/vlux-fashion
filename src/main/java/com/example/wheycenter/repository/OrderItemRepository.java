package com.example.wheycenter.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.wheycenter.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer>{
    
}
