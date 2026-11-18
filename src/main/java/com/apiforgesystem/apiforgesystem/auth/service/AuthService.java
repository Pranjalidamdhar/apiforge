package com.apiforgesystem.apiforgesystem.auth.service;


import com.apiforgesystem.apiforgesystem.common.exception.EmailAlreadyExistsException;
import com.apiforgesystem.apiforgesystem.common.exception.InvalidRoleException;
import com.apiforgesystem.apiforgesystem.user.Role;
import com.apiforgesystem.apiforgesystem.user.User;
import com.apiforgesystem.apiforgesystem.user.UserRepository;
import com.apiforgesystem.apiforgesystem.user.UserStatus;
import com.apiforgesystem.apiforgesystem.user.dto.RegisterRequest;
import com.apiforgesystem.apiforgesystem.user.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Transactional
    public UserResponse register(RegisterRequest request) {
        String email = request.getEmail().trim().toLowerCase();

        if (request.getRole() == Role.ADMIN) {
            throw new InvalidRoleException("Role must be API_OWNER or API_CONSUMER");
        }

        if (userRepository.existsByEmail(email)) {
            throw new EmailAlreadyExistsException("Email already registered");
        }

        User user = new User();
        user.setUsername(request.getUsername().trim());
        user.setEmail(email);
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());
        user.setStatus(UserStatus.ACTIVE);

        User saved = userRepository.save(user);

        return new UserResponse(saved.getId(), saved.getUsername(),
                saved.getEmail(), saved.getRole(), saved.getStatus());

    }

}
