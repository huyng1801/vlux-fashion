package vn.student.vluxfashion.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.student.vluxfashion.model.Gender;
import vn.student.vluxfashion.model.SubCategory;

@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategory, Integer> {
    Optional<SubCategory> findBySubCategoryName(String subCategoryName);
    List<SubCategory> findByCategory_CategoryId(Integer categoryId);
    List<SubCategory> findByGender(Gender gender);
    List<SubCategory> findByCategory_CategoryIdAndGender(Integer categoryId, Gender gender);
    
}
