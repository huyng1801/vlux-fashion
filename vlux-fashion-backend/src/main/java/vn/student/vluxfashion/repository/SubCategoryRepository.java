package vn.student.vluxfashion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.student.vluxfashion.model.SubCategory;

@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategory, String> {
}
