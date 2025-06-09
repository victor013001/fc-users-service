package com.example.fc_users_service.infrastructure.adapters.persistence.adapter;

import static com.example.fc_users_service.domain.constants.JwtConst.ROLE_CLAIM;

import com.example.fc_users_service.application.service.security.JwtApplicationService;
import com.example.fc_users_service.domain.spi.AuthPersistencePort;
import com.example.fc_users_service.infrastructure.adapters.persistence.repository.UserRepository;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthPersistenceAdapter implements AuthPersistencePort {

  private final AuthenticationManager authenticationManager;
  private final JwtApplicationService jwtService;
  private final UserRepository userRepository;

  @Override
  public String authenticate(String email, String password) {
    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
    UserDetails userDetails = getUserDetails(email);
    return jwtService.generateToken(roleClaims(userDetails.getAuthorities()), userDetails);
  }

  private UserDetails getUserDetails(String userEmail) {
    return userRepository.findByEmail(userEmail).orElseThrow();
  }

  private Map<String, Object> roleClaims(Collection<? extends GrantedAuthority> authorities) {
    Map<String, Object> claims = new HashMap<>();
    authorities.forEach(
        grantedAuthority -> claims.put(ROLE_CLAIM, grantedAuthority.getAuthority()));
    return claims;
  }
}
