package vn.student.vluxfashion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.student.vluxfashion.model.AdminUser;

@Repository
public interface UserRepository extends JpaRepository<AdminUser, String> {
    AdminUser findByEmail(String email);
}
