package com.musicstore.musicstore.service.impl;

import com.musicstore.musicstore.dto.request.LoginRq;
import com.musicstore.musicstore.dto.request.RegisterRq;
import com.musicstore.musicstore.dto.response.LoginResponse;
import com.musicstore.musicstore.dto.response.RegisterResponse;
import com.musicstore.musicstore.dto.response.UserResponseDto;
import com.musicstore.musicstore.model.User;
import com.musicstore.musicstore.repository.UserRepository;
import com.musicstore.musicstore.security.JwtTokenProvider;
import com.musicstore.musicstore.service.AuthService;
import com.musicstore.musicstore.service.CloudinaryService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final CloudinaryService cloudinaryService;

    @Override
    public RegisterResponse register(RegisterRq registerRq, MultipartFile file ) {
        if (userRepository.existsByEmail(registerRq.getEmail())) {
            throw new RuntimeException("Error: Email is already in use!");
        }

        // 1. Handle the file upload
        String imageUrl = null; // Default to null if no file is provided
        if (file != null && !file.isEmpty()) {
            // If a file is present, upload it to Cloudinary in a "profile_pictures" folder
            imageUrl = cloudinaryService.uploadFile(file, "profile_pictures");
        }



        User user = new User();
        user.setEmail(registerRq.getEmail());
        user.setFirstName(registerRq.getFirstName());
        user.setLastName(registerRq.getLastName());
        user.setPassword(passwordEncoder.encode(registerRq.getPassword()));
        user.setRole(registerRq.getRole());
        user.setProfilePictureUrl(imageUrl);

        User savedUser = userRepository.save(user);

        RegisterResponse registerResponse = new RegisterResponse();
        registerResponse.setMessage("User registered successfully!");
        registerResponse.setEmail(savedUser.getEmail());
        registerResponse.setFirstName(savedUser.getFirstName());
        registerResponse.setLastName(savedUser.getLastName());
        registerResponse.setProfilePictureUrl(imageUrl);
        return registerResponse;
    }

    @Override
    public LoginResponse login(LoginRq loginRq) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRq.getEmail(), loginRq.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenProvider.generateToken(authentication);

        // 3. Fetch the full User object from the database. We need this for the details.
        User user = userRepository.findByEmail(loginRq.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + loginRq.getEmail()));

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setMessage("User logged in successfully!");
        loginResponse.setAccessToken(token);


        UserResponseDto userResponseDto = new UserResponseDto();

        userResponseDto.setEmail(user.getEmail());
        userResponseDto.setFirstName(user.getFirstName());
        userResponseDto.setLastName(user.getLastName());
        userResponseDto.setRole(user.getRole());
        userResponseDto.setProfilePictureUrl(user.getProfilePictureUrl());
        loginResponse.setUser(userResponseDto);

        return loginResponse;
    }
}