package com.webservice.auth_service.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JWTUtil {

  private static final String SECRET = "wahidsecretykeywahidsecretykeywahidsecretykey123"; // 32+ chars
  private static final long EXPIRATION_TIME = 1000 * 60 * 60; // 1 hour
  private final Key key = Keys.hmacShaKeyFor(SECRET.getBytes());

  // Generate token
  public String generateToken(String username) {
    return Jwts.builder()
      .setSubject(username)
      .setIssuedAt(new Date())
      .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
      .signWith(key, SignatureAlgorithm.HS256)
      .compact();
  }

  // Extract payload
  public String extractUsername(String token) {
    return Jwts.parserBuilder()
      .setSigningKey(key)
      .build()
      .parseClaimsJws(token)
      .getBody()
      .getSubject();
  }

  // Validate token
  public boolean validateToken(String token) {
    try {
      Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
      return true;
    } catch (JwtException e) {
      return false;
    }
  }

  public Claims getClaimFromToken(String token) {
    return Jwts.parser()
      .setSigningKey(key)
      .parseClaimsJws(token)
      .getBody();
  }
}
