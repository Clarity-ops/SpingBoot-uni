package com.example.lab2;

import com.example.lab2.service.impl.TokenServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

class TokenServiceTest {

    private TokenServiceImpl tokenService;

    @BeforeEach
    void setUp() {
        tokenService = new TokenServiceImpl();
    }

    @Test
    void testGenerateAndValidateToken() {
        String username = "testUser";
        String userId = "123";

        String token = tokenService.generateToken(userId, username);
        Assertions.assertNotNull(token);

        Assertions.assertTrue(tokenService.validateToken(token));

        Map<String, Object> data = tokenService.getTokenData(token);
        Assertions.assertEquals(username, data.get("username"));
        Assertions.assertEquals("Lab3App", data.get("appName"));
    }

    @Test
    void testInvalidateToken() {
        String token = tokenService.generateToken("1", "user");
        Assertions.assertTrue(tokenService.validateToken(token));

        tokenService.invalidateToken(token);
        Assertions.assertFalse(tokenService.validateToken(token));
    }
}