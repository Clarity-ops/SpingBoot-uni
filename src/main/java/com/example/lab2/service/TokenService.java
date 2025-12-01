package com.example.lab2.service;

import java.util.Map;

public interface TokenService {
    String generateToken(String userId, String username);
    boolean validateToken(String token);
    void invalidateToken(String token);
    Map<String, Object> getTokenData(String token);
    String refreshToken(String oldToken);
}