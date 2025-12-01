package com.example.lab2.security;

import com.example.lab2.service.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    private final TokenService tokenService;
    private static final Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);

    public AuthInterceptor(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod method = (HandlerMethod) handler;

            if (method.hasMethodAnnotation(RequiresToken.class)) {
                String authHeader = request.getHeader("Authorization");

                if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                    logger.error("Відсутній або некоректний заголовок Authorization");
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing Token");
                    return false;
                }

                String token = authHeader.substring(7);

                if (tokenService.validateToken(token)) {
                    Map<String, Object> meta = tokenService.getTokenData(token);
                    request.setAttribute("userMetadata", meta);
                    logger.info("Доступ дозволено для користувача: {}", meta.get("username"));
                    return true;
                } else {
                    logger.error("Токен не пройшов валідацію");
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Token");
                    return false;
                }
            }
        }
        return true;
    }
}