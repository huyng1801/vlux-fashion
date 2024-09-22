package vn.student.vluxfashion.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.student.vluxfashion.model.Brand;

@Repository  
public interface BrandRepository extends JpaRepository<Brand, Integer> {
    Optional<Brand> findByBrandName(String brandName);
}
