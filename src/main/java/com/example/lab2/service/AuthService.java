package com.example.lab2.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.example.lab2.dto.LoginRequest;
import com.example.lab2.dto.RegistrationRequest;
import java.util.Map;

@Service
public class AuthService {

    // Створюємо логгер для цього класу
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    public Map<String, Object> registerUser(RegistrationRequest request) {
        // Логуємо дані, які прийшли (пароль логувати не варто в реальних системах)
        logger.info("Attempting to register new user with username: {}", request.getUsername());
        logger.info("User data: email={}, birthday={}, phone={}",
                request.getEmail(), request.getBirthday(), request.getPhoneNumber());

        // Валідація вже відбулася в контролері. Якщо ми тут, значить дані коректні.
        // Повертаємо mock-відповідь.
        logger.info("User {} successfully validated for registration.", request.getUsername());
        return Map.of(
                "status", "success",
                "message", "User registered successfully",
                "userId", 12345 // Mock ID
        );
    }

    public Map<String, String> loginUser(LoginRequest request) {
        logger.info("Attempting to log in user with username: {}", request.getUsername());

        // Валідація пройдена, повертаємо mock-токен.
        logger.info("User {} successfully validated for login.", request.getUsername());
        return Map.of(
                "status", "success",
                "message", "Login successful",
                "token", "mock-jwt-token-for-" + request.getUsername()
        );
    }
}