package com.example.lab2.controller;

import com.example.lab2.dto.LoginRequest;
import com.example.lab2.dto.RegistrationRequest;
import com.example.lab2.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth") // Базовий URL для всіх методів цього контролера
public class AuthController {

    private final AuthService authService;

    // Впровадження залежності AuthService через конструктор
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegistrationRequest request) {
        // @Valid - вмикає валідацію для об'єкта request
        // @RequestBody - говорить Spring взяти тіло запиту і перетворити його в об'єкт RegistrationRequest
        var response = authService.registerUser(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        var response = authService.loginUser(request);
        return ResponseEntity.ok(response);
    }
}