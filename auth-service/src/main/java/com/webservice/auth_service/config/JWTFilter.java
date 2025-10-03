package com.webservice.auth_service.config;

import com.webservice.auth_service.services.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTFilter extends OncePerRequestFilter {

  @Autowired
  private JWTUtil jwtUtil;
  @Autowired
  private CustomUserDetailsService customUserDetailsService;

  // Validating request for a token
  @Override
  protected void doFilterInternal(
    HttpServletRequest request, HttpServletResponse response, FilterChain filterChain
  ) throws ServletException, IOException {

    String header = request.getHeader("Authorization");
    String username = null;
    String token = null;

    // Get username from token
    if (header != null && header.startsWith("Bearer ")) {
      token = header.substring(7).trim();
      System.out.println(token);
      username = jwtUtil.extractUsername(token);
    }
    // Validate username with token and database
    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
      var userDetails = customUserDetailsService.loadUserByUsername(username);

      if (jwtUtil.validateToken(token)) {
        UsernamePasswordAuthenticationToken auth =
          new UsernamePasswordAuthenticationToken(
            userDetails, null, userDetails.getAuthorities());

        auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(auth);
      }
    }
    // Chain  request if authentication success
    filterChain.doFilter(request, response);
  }
}
