package vn.student.vluxfashion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.student.vluxfashion.model.Banner;

@Repository  
public interface BannerRepository extends JpaRepository<Banner, Integer> {
}
