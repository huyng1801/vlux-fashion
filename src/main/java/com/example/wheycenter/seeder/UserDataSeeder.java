package com.example.wheycenter.seeder;

import com.example.wheycenter.model.Role;
import com.example.wheycenter.model.User;
import com.example.wheycenter.repository.RoleRepository;
import com.example.wheycenter.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.Optional;

@Component
public class UserDataSeeder {

    private static final Logger logger = LoggerFactory.getLogger(UserDataSeeder.class);

    @Bean
    CommandLineRunner seedUsers(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.count() == 0) { // Prevent duplicate entries

                // Fetch roles from the RoleRepository
                Optional<Role> adminRoleOpt = roleRepository.findByRoleName("ADMIN");
                Optional<Role> employeeRoleOpt = roleRepository.findByRoleName("EMPLOYEE");

                if (adminRoleOpt.isEmpty() || employeeRoleOpt.isEmpty()) {
                    logger.error("Roles not found, please seed roles first!");
                    return;
                }

                Role adminRole = adminRoleOpt.get();
                Role employeeRole = employeeRoleOpt.get();

                // Create an admin user
                User adminUser = new User();
                adminUser.setUserId(UUID.randomUUID().toString());
                adminUser.setFirstName("Huy");
                adminUser.setLastName("Nguyễn");
                adminUser.setEmail("huynguyendev18012003@gmail.com");
                adminUser.setHashPassword(passwordEncoder.encode("123456")); // Encrypt the password
                adminUser.setIsActive(true);
                Set<Role> adminRoles = new HashSet<>();
                adminRoles.add(adminRole);
                adminUser.setRoles(adminRoles);

                // Create an employee user
                User employeeUser = new User();
                employeeUser.setUserId(UUID.randomUUID().toString());
                employeeUser.setFirstName("Huy");
                employeeUser.setLastName("Nguyễn");
                employeeUser.setEmail("huyng.1801@gmail.com");
                employeeUser.setHashPassword(passwordEncoder.encode("123456")); // Encrypt the password
                employeeUser.setIsActive(true);
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
