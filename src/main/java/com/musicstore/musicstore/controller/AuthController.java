package com.musicstore.musicstore.controller;

import com.musicstore.musicstore.dto.request.RegisterRq;
import com.musicstore.musicstore.dto.response.RegisterResponse;
import com.musicstore.musicstore.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRq registerRq) {

        RegisterResponse registerResponse = authService.register(registerRq);
        return new ResponseEntity<>(registerResponse,HttpStatus.CREATED);

    }
}
