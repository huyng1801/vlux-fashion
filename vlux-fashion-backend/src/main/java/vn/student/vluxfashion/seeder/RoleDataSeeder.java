package vn.student.vluxfashion.seeder;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import vn.student.vluxfashion.model.Role;
import vn.student.vluxfashion.repository.RoleRepository;
import vn.student.vluxfashion.util.GenerateUtils;

@Component
public class RoleDataSeeder {

    private static final Logger logger = LoggerFactory.getLogger(RoleDataSeeder.class);

    @Bean
    CommandLineRunner seedRoles(RoleRepository roleRepository) {
        return args -> {
            if (roleRepository.count() == 0) { // To prevent duplicate entries
                
                Role userRole = new Role();
                userRole.setRoleId(GenerateUtils.generateUUID());
                userRole.setRoleName("Quản trị viên");
                userRole.setDescription("Quản trị viên có quyền truy cập tất cả chức năng");
                userRole.setCreatedAt(new Date());
                userRole.setUpdatedAt(new Date());
                
                Role employeeRole = new Role();
                employeeRole.setRoleId(GenerateUtils.generateUUID());
                employeeRole.setRoleName("Nhân viên");
                employeeRole.setDescription("Nhân viên có quyền truy cập một số chức năng được cấp quyền");
                employeeRole.setCreatedAt(new Date());
                employeeRole.setUpdatedAt(new Date());
                
                roleRepository.save(userRole);
                roleRepository.save(employeeRole);
                
                logger.info("Roles seeded successfully!");
            }
        };
    }
}