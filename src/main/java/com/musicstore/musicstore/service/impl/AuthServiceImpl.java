package com.musicstore.musicstore.service.impl;


import com.musicstore.musicstore.dto.request.RegisterRq;
import com.musicstore.musicstore.dto.response.RegisterResponse;
import com.musicstore.musicstore.model.User;
import com.musicstore.musicstore.repository.UserRepository;
import com.musicstore.musicstore.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    @Override
    public RegisterResponse register(RegisterRq registerRq) {

       User user = new User();

       user.setEmail(registerRq.getEmail());
       user.setPassword(registerRq.getPassword());
       user.setFirstName(registerRq.getFirstName());
       user.setLastName(registerRq.getLastName());
       user.setRole(registerRq.getRole());

       userRepository.save(user);

       RegisterResponse registerResponse = new RegisterResponse();

       registerResponse.setMessage("Successfully registered");
       registerResponse.setEmail(user.getEmail());
       registerResponse.setFirstName(user.getFirstName());
       registerResponse.setLastName(user.getLastName());

       return registerResponse;


    }
}
