package com.example.wheycenter.repository;

import com.example.wheycenter.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, String> {

    // Custom method to find Role by its name
    Optional<Role> findByRoleName(String roleName);
}
