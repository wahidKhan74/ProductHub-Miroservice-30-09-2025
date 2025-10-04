package com.webservice.products_service.config;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class IntrospectionClient {

  private final RestTemplate restTemplate = new RestTemplate();

  public Map<String, Object> introspectToken(String token) {
    String url = "http://localhost:5000/auth-service/auth/introspect"; // Auth Service URL

    Map<String, String> request = Map.of("token", token);
    return restTemplate.postForObject(url, request, Map.class);
  }
}
