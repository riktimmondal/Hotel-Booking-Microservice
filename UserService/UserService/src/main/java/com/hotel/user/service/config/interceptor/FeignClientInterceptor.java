package com.hotel.user.service.config.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.hotel.user.service.services.impl.TokenService;

import feign.RequestInterceptor;

@Configuration
@Component
public class FeignClientInterceptor implements RequestInterceptor {
    
    private final TokenService tokenService;

    public FeignClientInterceptor(TokenService tokenService) {
        this.tokenService = tokenService;
    }
    @Override
    public void apply(feign.RequestTemplate requestTemplate) {
        String token = tokenService.extractToken();
        if (token != null) {
            // Add the token to the Authorization header
            requestTemplate.header("Authorization", "Bearer " + token);
        }
    }
}
