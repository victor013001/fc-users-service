package com.example.fc_users_service.infrastructure.adapters.persistence.adapter;

import static com.example.fc_users_service.util.data.UserEntityData.getUserEntity;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.fc_users_service.application.service.security.JwtApplicationService;
import com.example.fc_users_service.infrastructure.adapters.persistence.repository.UserRepository;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@ExtendWith(MockitoExtension.class)
class AuthPersistenceAdapterTest {

  @InjectMocks private AuthPersistenceAdapter authPersistenceAdapter;

  @Mock private AuthenticationManager authenticationManager;
  @Mock private JwtApplicationService jwtService;
  @Mock private UserRepository userRepository;

  @Test
  void authenticate_ShouldReturnToken() {
    String email = "test@example.com";
    String password = "password";
    String expectedToken = "mocked-jwt";
    var userEntity = getUserEntity();

    when(userRepository.findByEmail(email)).thenReturn(Optional.of(userEntity));
    when(jwtService.generateToken(anyMap(), eq(userEntity))).thenReturn(expectedToken);

    String result = authPersistenceAdapter.authenticate(email, password);

    verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
    verify(userRepository).findByEmail(email);
    verify(jwtService).generateToken(anyMap(), eq(userEntity));

    assertEquals(expectedToken, result);
  }

  @Test
  void authenticate_ShouldThrow_WhenUserNotFound() {
    String email = "nonexistent@example.com";
    String password = "password";

    when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

    assertThrows(
        NoSuchElementException.class,
        () -> {
          authPersistenceAdapter.authenticate(email, password);
        });

    verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
    verify(userRepository).findByEmail(email);
    verify(jwtService, never()).generateToken(anyMap(), any());
  }
}
