package vn.student.vluxfashion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.student.vluxfashion.model.OrderItem;

@Repository  
public interface OrderItemRepository extends JpaRepository<OrderItem, Integer>{
}
