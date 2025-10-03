package com.webservice.auth_service.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Autowired
  private JWTFilter jwtFilter;

  @Autowired
  private JwtAuthEntryPoint jwtAuthEntryPoint;

  @Autowired
  private JwtAccessEntryPoint jwtAccessEntryPoint;

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
    return config.getAuthenticationManager();
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
      .csrf(csrf -> csrf.disable())              // simple example
      .authorizeHttpRequests(auth -> auth
        .requestMatchers("/public/**", "/auth/**",
          "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html", "/swagger-resources/**", "/swagger-resources", "/api-docs/**").permitAll()
        .requestMatchers("/products/**").hasRole("ADMIN")
        .requestMatchers("/orders/**").hasAnyRole("USER","ADMIN")
        .anyRequest().authenticated()
      )
      .exceptionHandling(ex -> ex.accessDeniedHandler(jwtAccessEntryPoint))
      .exceptionHandling(ex -> ex.authenticationEntryPoint(jwtAuthEntryPoint))
      .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
      .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }

}
