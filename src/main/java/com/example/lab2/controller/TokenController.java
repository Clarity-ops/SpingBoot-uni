package com.example.lab2.controller;

import com.example.lab2.security.RequiresToken;
import com.example.lab2.service.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/token")
public class TokenController {

    private final TokenService tokenService;

    public TokenController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @PostMapping("/refresh")
    @RequiresToken
    public ResponseEntity<?> refreshToken(HttpServletRequest request) {
        String oldToken = request.getHeader("Authorization").substring(7);
        
        try {
            String newToken = tokenService.refreshToken(oldToken);
            return ResponseEntity.ok(Map.of("newToken", newToken));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(401).body("Invalid Token");
        }
    }

    @PostMapping("/invalidate")
    @RequiresToken
    public ResponseEntity<?> invalidateToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);
        tokenService.invalidateToken(token);
        return ResponseEntity.ok(Map.of("message", "Токен успішно інвалідовано (Logout)"));
    }
    
    @GetMapping("/me")
    @RequiresToken
    public ResponseEntity<?> getMyMetadata(HttpServletRequest request) {
        Object metadata = request.getAttribute("userMetadata");
        return ResponseEntity.ok(metadata);
    }
}