package com.musicstore.musicstore.controller;

import com.musicstore.musicstore.dto.request.LoginRq;
import com.musicstore.musicstore.dto.request.RegisterRq;
import com.musicstore.musicstore.dto.response.LoginResponse;
import com.musicstore.musicstore.dto.response.RegisterResponse;
import com.musicstore.musicstore.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register( RegisterRq registerRq,
                                                      @RequestParam(value = "file",required = false) MultipartFile file) {
        RegisterResponse response = authService.register(registerRq,file);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRq loginRq) {
        LoginResponse response = authService.login(loginRq);
        return ResponseEntity.ok(response);
    }
}