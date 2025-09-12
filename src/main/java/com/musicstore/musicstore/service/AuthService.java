package com.musicstore.musicstore.service;

import com.musicstore.musicstore.dto.request.LoginRq;
import com.musicstore.musicstore.dto.request.RegisterRq;
import com.musicstore.musicstore.dto.response.LoginResponse;
import com.musicstore.musicstore.dto.response.RegisterResponse;
import org.springframework.web.multipart.MultipartFile;

public interface AuthService {
    RegisterResponse register(RegisterRq registerRq, MultipartFile file);
    LoginResponse login(LoginRq loginRq);
}