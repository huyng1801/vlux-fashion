package vn.student.vluxfashion.service;

import org.springframework.stereotype.Service;

import vn.student.vluxfashion.model.AdminUser;
import vn.student.vluxfashion.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<AdminUser> allUsers() {
        List<AdminUser> users = new ArrayList<>();

        userRepository.findAll().forEach(users::add);

        return users;
    }
}