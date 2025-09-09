package com.musicstore.musicstore.service.impl;

import com.musicstore.musicstore.dto.request.LoginRq;
import com.musicstore.musicstore.dto.request.RegisterRq;
import com.musicstore.musicstore.dto.response.LoginResponse;
import com.musicstore.musicstore.dto.response.RegisterResponse;
import com.musicstore.musicstore.model.User;
import com.musicstore.musicstore.repository.UserRepository;
import com.musicstore.musicstore.security.JwtTokenProvider;
import com.musicstore.musicstore.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public RegisterResponse register(RegisterRq registerRq) {
        if (userRepository.existsByEmail(registerRq.getEmail())) {
            throw new RuntimeException("Error: Email is already in use!");
        }

        User user = new User();
        user.setEmail(registerRq.getEmail());
        user.setFirstName(registerRq.getFirstName());
        user.setLastName(registerRq.getLastName());
        user.setPassword(passwordEncoder.encode(registerRq.getPassword()));
        user.setRole(registerRq.getRole());

        User savedUser = userRepository.save(user);

        RegisterResponse registerResponse = new RegisterResponse();
        registerResponse.setMessage("User registered successfully!");
        registerResponse.setEmail(savedUser.getEmail());
        registerResponse.setFirstName(savedUser.getFirstName());
        registerResponse.setLastName(savedUser.getLastName());

        return registerResponse;
    }

    @Override
    public LoginResponse login(LoginRq loginRq) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRq.getEmail(), loginRq.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenProvider.generateToken(authentication);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setMessage("User logged in successfully!");
        loginResponse.setAccessToken(token);

        return loginResponse;
    }
}