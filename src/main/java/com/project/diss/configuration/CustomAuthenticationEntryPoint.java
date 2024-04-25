package com.project.diss.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.diss.dto.ErrorResponse;
import com.project.diss.exception.ServiceErrorCodes;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        ErrorResponse errorResponse = new ErrorResponse(
                ServiceErrorCodes.UNAUTHORIZED_ERROR.errorCode(),
                ServiceErrorCodes.UNAUTHORIZED_ERROR.errorName(),
                ServiceErrorCodes.UNAUTHORIZED_ERROR.errorMessage()
        );
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }

}
