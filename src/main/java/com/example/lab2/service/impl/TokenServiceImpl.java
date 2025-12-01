package com.example.lab2.service.impl;

import com.example.lab2.service.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TokenServiceImpl implements TokenService {

    private static final Logger logger = LoggerFactory.getLogger(TokenServiceImpl.class);
    // Зберігалище токенів: TokenString -> Metadata Map
    private final Map<String, Map<String, Object>> tokenStore = new ConcurrentHashMap<>();
    
    private static final long EXPIRATION_MINUTES = 30;
    private static final String APP_NAME = "Lab3App";

    @Override
    public String generateToken(String userId, String username) {
        String token = UUID.randomUUID().toString();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiresAt = now.plusMinutes(EXPIRATION_MINUTES);

        Map<String, Object> metadata = Map.of(
                "userId", userId,
                "username", username,
                "appName", APP_NAME,
                "createdAt", now.toString(),
                "expiresAt", expiresAt.toString()
        );

        tokenStore.put(token, metadata);
        logger.info("Згенеровано токен для користувача: {}", username);
        return token;
    }

    @Override
    public boolean validateToken(String token) {
        if (!tokenStore.containsKey(token)) {
            logger.warn("Токен не знайдено або він недійсний: {}", token);
            return false;
        }

        Map<String, Object> metadata = tokenStore.get(token);
        LocalDateTime expiresAt = LocalDateTime.parse((String) metadata.get("expiresAt"));

        if (LocalDateTime.now().isAfter(expiresAt)) {
            logger.warn("Термін дії токену вичерпано: {}", token);
            tokenStore.remove(token); // Видаляємо протермінований
            return false;
        }

        return true;
    }

    @Override
    public void invalidateToken(String token) {
        tokenStore.remove(token);
        logger.info("Токен інвалідовано (видалено): {}", token);
    }
    
    @Override
    public Map<String, Object> getTokenData(String token) {
        return tokenStore.get(token);
    }
    
    @Override
    public String refreshToken(String oldToken) {
        if (validateToken(oldToken)) {
             Map<String, Object> data = tokenStore.get(oldToken);
             invalidateToken(oldToken);
             return generateToken((String) data.get("userId"), (String) data.get("username"));
        }
        throw new IllegalArgumentException("Неможливо оновити недійсний токен");
    }
}