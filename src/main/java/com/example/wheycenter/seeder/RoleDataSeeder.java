package com.example.wheycenter.seeder;

import com.example.wheycenter.model.Role;
import com.example.wheycenter.repository.RoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component
public class RoleDataSeeder {

    private static final Logger logger = LoggerFactory.getLogger(RoleDataSeeder.class);

    @Bean
    CommandLineRunner seedRoles(RoleRepository roleRepository) {
        return args -> {
            if (roleRepository.count() == 0) { // To prevent duplicate entries
                
                Role userRole = new Role();
                userRole.setRoleId(UUID.randomUUID().toString());
                userRole.setRoleName("Quản trị viên");
                userRole.setDescription("Quản trị viên có quyền truy cập tất cả chức năng");
                userRole.setCreatedAt(new Date());
                userRole.setUpdatedAt(new Date());
                
                Role employeeRole = new Role();
                employeeRole.setRoleId(UUID.randomUUID().toString());
                employeeRole.setRoleName("Nhân viên");
                employeeRole.setDescription("Nhân viên có quyền truy cập một số chức năng được cấp quyền");
                userRole.setCreatedAt(new Date());
                userRole.setUpdatedAt(new Date());
                
                roleRepository.save(userRole);
                roleRepository.save(employeeRole);
                
                logger.info("Roles seeded successfully!");
            }
        };
    }
}
