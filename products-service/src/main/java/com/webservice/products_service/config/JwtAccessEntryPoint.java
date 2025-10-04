package com.webservice.products_service.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.webservice.products_service.dto.ErrorResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class JwtAccessEntryPoint implements AccessDeniedHandler {

  private final ObjectMapper objectMapper;

  public JwtAccessEntryPoint(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  @Override
  public void handle(
    HttpServletRequest request, HttpServletResponse response,
                     AccessDeniedException accessDeniedException
  ) throws IOException, ServletException {
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    response.setContentType("application/json");

    ErrorResponse errorResponse = new ErrorResponse(
      "Access Denied: " + accessDeniedException.getMessage(),
      "Forbidden request from user",
      HttpStatus.FORBIDDEN.value(),
      LocalDateTime.now()
    );

    objectMapper.writeValue(response.getOutputStream(), errorResponse);
  }
}
