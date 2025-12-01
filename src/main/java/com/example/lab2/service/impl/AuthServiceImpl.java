package com.example.lab2.service.impl;

import com.example.lab2.dto.LoginRequest;
import com.example.lab2.dto.RegistrationRequest;
import com.example.lab2.service.AuthService;
import com.example.lab2.service.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AuthServiceImpl implements AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);
    private final TokenService tokenService;

    public AuthServiceImpl(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public Map<String, Object> registerUser(RegistrationRequest request) {
        logger.info("Спроба реєстрації користувача: {}", request.getUsername());
        
        String token = tokenService.generateToken("ID-" + request.getUsername(), request.getUsername());

        return Map.of(
                "status", "success",
                "message", "Користувач успішно зареєстрований",
                "token", token
        );
    }

    @Override
    public Map<String, Object> loginUser(LoginRequest request) {
        logger.info("Спроба входу користувача: {}", request.getUsername());
        
        String token = tokenService.generateToken("ID-" + request.getUsername(), request.getUsername());

        return Map.of(
                "status", "success",
                "message", "Вхід успішний",
                "token", token
        );
    }
}