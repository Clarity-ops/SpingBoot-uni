package com.example.lab2.service;

import com.example.lab2.dto.LoginRequest;
import com.example.lab2.dto.RegistrationRequest;
import java.util.Map;

public interface AuthService {
    Map<String, Object> registerUser(RegistrationRequest request);
    Map<String, Object> loginUser(LoginRequest request);
}