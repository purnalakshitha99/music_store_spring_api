package com.musicstore.musicstore.service;

import com.musicstore.musicstore.dto.request.LoginRq;
import com.musicstore.musicstore.dto.request.RegisterRq;
import com.musicstore.musicstore.dto.response.LoginResponse;
import com.musicstore.musicstore.dto.response.RegisterResponse;

public interface AuthService {
    RegisterResponse register(RegisterRq registerRq);
    LoginResponse login(LoginRq loginRq);
}