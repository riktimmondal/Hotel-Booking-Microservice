package com.hotel.user.service.services.impl;

import org.springframework.boot.autoconfigure.jersey.JerseyProperties.Servlet;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class TokenService {

    public String extractToken() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) org.springframework.web.context.request.RequestContextHolder.getRequestAttributes();

        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            String authHeader = request.getHeader("Authorization");

            if(authHeader != null && authHeader.startsWith("Bearer ")) {
                return authHeader.substring(7); // Extract token after "Bearer "
            }
        }
        return null; // Return null if no token is found
    }
    
}
