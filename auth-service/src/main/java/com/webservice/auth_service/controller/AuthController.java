package com.webservice.auth_service.controller;


import com.webservice.auth_service.dto.JWTResponse;
import com.webservice.auth_service.dto.Response;
import com.webservice.auth_service.entity.Users;
import com.webservice.auth_service.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

  @Autowired
  private AuthService authService;


  @PostMapping("/register")
  public ResponseEntity<Response> register(@RequestBody Users user) {
    return authService.register(user);
  }

  @PostMapping("/login")
  public ResponseEntity<JWTResponse> login(@RequestBody Users user) {
    return authService.login(user);
  }
}
