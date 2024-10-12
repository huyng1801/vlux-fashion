package vn.student.vluxfashion.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import vn.student.vluxfashion.dto.LoginUserDto;
import vn.student.vluxfashion.model.AdminUser;
import vn.student.vluxfashion.repository.AdminUserRepository;

@Service
public class AuthenticationService {
    private final AdminUserRepository userRepository;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(
        AdminUserRepository userRepository,
        AuthenticationManager authenticationManager
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
    }

   public AdminUser authenticate(LoginUserDto input) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                input.getEmail(),
                input.getPassword()
            )
        );

        AdminUser user = userRepository.findByEmail(input.getEmail()).get();
        return user;
    }
}
