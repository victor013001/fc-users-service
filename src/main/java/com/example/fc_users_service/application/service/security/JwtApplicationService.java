package com.example.fc_users_service.application.service.security;

import io.jsonwebtoken.Claims;
import java.util.Map;
import java.util.function.Function;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtApplicationService {

  <T> T extractClaim(String token, Function<Claims, T> claimsResolver);

  String extractUserName(String token);

  String generateToken(UserDetails userDetails);

  String generateToken(Map<String, Object> extraClaims, UserDetails userDetails);

  boolean isTokenValid(String token, UserDetails userDetails);
}
