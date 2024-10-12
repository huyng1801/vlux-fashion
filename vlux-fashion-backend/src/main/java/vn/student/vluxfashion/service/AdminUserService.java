package vn.student.vluxfashion.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.student.vluxfashion.dto.AdminUserDto;
import vn.student.vluxfashion.dto.ChangePasswordDto;
import vn.student.vluxfashion.exception.ResourceNotFoundException;
import vn.student.vluxfashion.response.AdminUserResponse;
import vn.student.vluxfashion.model.AdminUser;
import vn.student.vluxfashion.repository.AdminUserRepository;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminUserService {
    private final AdminUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminUserService(AdminUserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public AdminUserResponse createUser(AdminUserDto userDto) {
        AdminUser newUser = new AdminUser();
        mapDtoToEntity(userDto, newUser);
        newUser.setHashPassword(passwordEncoder.encode(userDto.getPassword()));
        newUser.setCreatedAt(new Date());
        newUser.setUpdatedAt(new Date());
        userRepository.save(newUser);
        return mapToResponse(newUser);
    }

    public AdminUserResponse updateUser(String userId, AdminUserDto userDto) {
        AdminUser existingUser = findUserById(userId);
        mapDtoToEntity(userDto, existingUser);
        existingUser.setUpdatedAt(new Date());
        userRepository.save(existingUser);
        return mapToResponse(existingUser);
    }

    public void changePassword(String userId, ChangePasswordDto changePasswordDto) {
        AdminUser user = findUserById(userId);
        
        // Validate old password
        if (!passwordEncoder.matches(changePasswordDto.getOldPassword(), user.getHashPassword())) {
            throw new IllegalArgumentException("Old password is incorrect");
        }
        
        // Update to new password
        user.setHashPassword(passwordEncoder.encode(changePasswordDto.getNewPassword()));
        userRepository.save(user);
    }
    

    public void deleteUser(String userId) {
        AdminUser user = findUserById(userId);
        userRepository.delete(user);
    }

    public AdminUserResponse getUserById(String userId) {
        return mapToResponse(findUserById(userId));
    }

    public AdminUserResponse findByEmail(String email) {
        AdminUser user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
        return mapToResponse(user);
    }

    public List<AdminUserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private AdminUser findUserById(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));
    }

    private void mapDtoToEntity(AdminUserDto userDto, AdminUser user) {
        user.setEmail(userDto.getEmail());
        user.setFullName(userDto.getFullName());
        user.setRole(userDto.getRole());
        user.setIsActive(userDto.getIsActive());
    }

    private AdminUserResponse mapToResponse(AdminUser user) {
        return new AdminUserResponse(
            user.getAdminUserId(),
            user.getEmail(),
            user.getFullName(),
            user.getRole(),
            user.getIsActive(),
            user.getCreatedAt(),
            user.getUpdatedAt()
        );
    }
}
