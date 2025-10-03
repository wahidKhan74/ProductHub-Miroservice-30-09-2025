package com.webservice.auth_service.services;


import com.webservice.auth_service.config.JWTUtil;
import com.webservice.auth_service.dto.JWTResponse;
import com.webservice.auth_service.dto.Response;
import com.webservice.auth_service.entity.Users;
import com.webservice.auth_service.exception.NotFound;
import com.webservice.auth_service.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final AuthenticationManager authManager;
  private final JWTUtil jwtUtil;
  private final UsersRepository repo;
  private final PasswordEncoder encoder;

  public ResponseEntity<Response> register(Users user) {
    user.setPassword(encoder.encode(user.getPassword()));
    repo.save(user);
    return new ResponseEntity<>(
      new Response("User registered successfully"),
      HttpStatus.OK
    );
  }

  public ResponseEntity<JWTResponse> login(Users user) {

    Optional<Users> userDetails = repo.findByUsername(user.getUsername());

    if (userDetails.isPresent()) {
      // set au manager
      authManager.authenticate(
        new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

      return new ResponseEntity<JWTResponse>(
        new JWTResponse("User logged in successfully",
          jwtUtil.generateToken(user.getUsername())
        ), HttpStatus.OK);
    } else {
      throw new NotFound("User not found: " + user.getUsername());
    }

  }
}
