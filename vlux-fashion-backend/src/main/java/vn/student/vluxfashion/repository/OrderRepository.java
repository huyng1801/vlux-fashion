package vn.student.vluxfashion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.student.vluxfashion.model.Order;

@Repository  
public interface OrderRepository extends JpaRepository<Order, String> { 
}
