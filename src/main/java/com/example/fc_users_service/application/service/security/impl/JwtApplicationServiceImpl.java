package com.example.fc_users_service.application.service.security.impl;

import static com.example.fc_users_service.domain.constants.JwtConst.EXPIRATION_TIME;

import com.example.fc_users_service.application.service.security.JwtApplicationService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtApplicationServiceImpl implements JwtApplicationService {

  // @Value("${app.secret_key}")
  private String secretKey = "7f4c9a8e2d1b3f9c6e0a4d7b8f2c1e5a9b3d6f7c8e0a1b2d3c4f5a6e7b8d9c0";

  @Override
  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  @Override
  public String extractUserName(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  @Override
  public String generateToken(UserDetails userDetails) {
    return generateToken(new HashMap<>(), userDetails);
  }

  @Override
  public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
    Date issuedAt = new Date(System.currentTimeMillis());
    Date expiration = new Date(issuedAt.getTime() + EXPIRATION_TIME);

    return Jwts.builder()
        .setClaims(extraClaims)
        .setSubject(userDetails.getUsername())
        .setIssuedAt(issuedAt)
        .setExpiration(expiration)
        .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
        .signWith(getSignInKey(), SignatureAlgorithm.HS256)
        .compact();
  }

  @Override
  public boolean isTokenValid(String token, UserDetails userDetails) {
    final String username = extractUserName(token);
    return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
  }

  private boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  private Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  private Claims extractAllClaims(String token) {
    return Jwts.parserBuilder()
        .setSigningKey(getSignInKey())
        .build()
        .parseClaimsJws(token)
        .getBody();
  }

  private Key getSignInKey() {
    byte[] keyBytes = Decoders.BASE64.decode(secretKey);
    return Keys.hmacShaKeyFor(keyBytes);
  }
}
