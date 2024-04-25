package com.project.diss.configuration;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.diss.dto.ErrorResponse;
import com.project.diss.exception.JwtTokenException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.FilterChain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.lang.NonNull;

import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenService jwtTokenService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws IOException, ServletException {
        try {
            Authentication auth = jwtTokenService.getAuthentication(request);
            SecurityContextHolder.getContext().setAuthentication(auth);
            filterChain.doFilter(request, response);

        } catch (JwtTokenException ex) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json;charset=UTF-8");
            ErrorResponse errorResponse = new ErrorResponse(
                    ex.getErrorCode(), ex.getErrorName(), ex.getErrorMessage()
            );
            response.getWriter().write(new ObjectMapper().writeValueAsString(errorResponse));
        }
    }

}