package vn.student.vluxfashion.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.student.vluxfashion.model.AdminUser;
import vn.student.vluxfashion.service.UserService;

import java.util.List;

@RequestMapping("/users")
@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<AdminUser> authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        AdminUser currentUser = (AdminUser) authentication.getPrincipal();

        return ResponseEntity.ok(currentUser);
    }

    @GetMapping
    public ResponseEntity<List<AdminUser>> allUsers() {
        List <AdminUser> users = userService.allUsers();

        return ResponseEntity.ok(users);
    }
}