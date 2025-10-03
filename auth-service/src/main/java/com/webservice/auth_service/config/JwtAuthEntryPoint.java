package com.webservice.auth_service.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.webservice.auth_service.dto.ErrorResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class JwtAuthEntryPoint implements AuthenticationEntryPoint {

  private final ObjectMapper objectMapper;

  public JwtAuthEntryPoint(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  @Override
  public void commence(
    HttpServletRequest request, HttpServletResponse response, AuthenticationException authException
  ) throws IOException, ServletException {

    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    response.setContentType("application/json");

    ErrorResponse errorResponse = new ErrorResponse(
      "Authentication is required to access this resource",
      "Unauthorised request from user",
      HttpStatus.UNAUTHORIZED.value(),
      LocalDateTime.now()
    );

    objectMapper.writeValue(response.getOutputStream(), errorResponse);
  }
}
