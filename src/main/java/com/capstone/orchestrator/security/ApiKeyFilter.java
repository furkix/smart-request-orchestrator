package com.capstone.orchestrator.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class ApiKeyFilter extends OncePerRequestFilter {

    @Value("${app.api-key:}")
    private String expectedKey;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String uri = request.getRequestURI();
        return expectedKey == null || expectedKey.isBlank()
                || uri.startsWith("/v3/api-docs") || uri.startsWith("/swagger-ui")
                || uri.startsWith("/h2-console");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String key = request.getHeader("X-API-KEY");
        if (expectedKey != null && !expectedKey.isBlank() && !expectedKey.equals(key)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid API key");
            return;
        }
        filterChain.doFilter(request, response);
    }
}
