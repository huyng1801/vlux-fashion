package vn.student.vluxfashion.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.student.vluxfashion.model.Product;

@Repository  
public interface ProductRepository extends JpaRepository<Product, Integer>{
     Optional<Product> findByProductName(String productName);
}
