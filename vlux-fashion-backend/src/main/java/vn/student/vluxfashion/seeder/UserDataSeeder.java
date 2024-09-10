package vn.student.vluxfashion.seeder;

import java.util.Date;
import java.util.Optional;
import java.util.Set;
import java.util.HashSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import vn.student.vluxfashion.model.Role;
import vn.student.vluxfashion.model.User;
import vn.student.vluxfashion.repository.RoleRepository;
import vn.student.vluxfashion.repository.UserRepository;
import vn.student.vluxfashion.util.GenerateUtils;

@Component
public class UserDataSeeder {

    private static final Logger logger = LoggerFactory.getLogger(UserDataSeeder.class);

    @Bean
    CommandLineRunner seedUsers(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.count() == 0) { // Prevent duplicate entries

                // Fetch roles from the RoleRepository
                Optional<Role> adminRoleOpt = roleRepository.findByRoleName("Quản trị viên");
                Optional<Role> employeeRoleOpt = roleRepository.findByRoleName("Nhân viên");

                if (adminRoleOpt.isEmpty() || employeeRoleOpt.isEmpty()) {
                    logger.error(adminRoleOpt.toString());
                    logger.error("Roles not found, please seed roles first!");
                    return;
                }

                Role adminRole = adminRoleOpt.get();
                Role employeeRole = employeeRoleOpt.get();

                // Create an admin user
                User adminUser = new User();
                adminUser.setUserId(GenerateUtils.generateUUID());
                adminUser.setFullName("Huy Nguyễn");
                adminUser.setEmail("huynguyendev18012003@gmail.com");
                adminUser.setHashPassword(passwordEncoder.encode("123456"));
                adminUser.setIsActive(true);
                adminUser.setCreatedAt(new Date());
                adminUser.setUpdatedAt(new Date());
                Set<Role> adminRoles = new HashSet<>();
                adminRoles.add(adminRole);
                adminUser.setRoles(adminRoles);

                // Create an employee user
                User employeeUser = new User();
                employeeUser.setUserId(GenerateUtils.generateUUID());
                employeeUser.setFullName("Huy Nguyễn");
                employeeUser.setEmail("huyng.1801@gmail.com");
                employeeUser.setHashPassword(passwordEncoder.encode("123456")); 
                employeeUser.setIsActive(true);
                employeeUser.setCreatedAt(new Date());
                employeeUser.setUpdatedAt(new Date());
                Set<Role> employeeRoles = new HashSet<>();
                employeeRoles.add(employeeRole);
                employeeUser.setRoles(employeeRoles);

                // Save users to the database
                userRepository.save(adminUser);
                userRepository.save(employeeUser);

                logger.info("Users seeded successfully!");
            }
        };
    }
}