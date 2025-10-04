package com.webservice.products_service.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.core.HttpHeaders;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

  private final IntrospectionClient introspectionClient;

  @Override
  protected void doFilterInternal(HttpServletRequest request,
                                  HttpServletResponse response,
                                  FilterChain chain) throws IOException, ServletException {

    String header = request.getHeader(HttpHeaders.AUTHORIZATION);

    if (header != null && header.startsWith("Bearer ")) {
      String token = header.substring(7);

      Map<String, Object> result = introspectionClient.introspectToken(token);

      if (Boolean.TRUE.equals(result.get("enabled"))) {
        String username = (String) result.get("username");
        Object rolesObj = result.get("roles");
        List<String> roles = new ArrayList<>();

        if (rolesObj instanceof List<?>) {
          ((List<?>) rolesObj).forEach(r -> roles.add(String.valueOf(r)));
        }

        UsernamePasswordAuthenticationToken authentication =
          new UsernamePasswordAuthenticationToken(
            username, null,
            roles.stream().map(SimpleGrantedAuthority::new).toList()
          );

        SecurityContextHolder.getContext().setAuthentication(authentication);
      } else {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid access token");
        return;
      }
    }

    chain.doFilter(request, response);
  }
}

