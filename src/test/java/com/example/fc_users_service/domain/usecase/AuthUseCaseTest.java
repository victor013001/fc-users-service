package com.example.fc_users_service.domain.usecase;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.fc_users_service.domain.exceptions.standard_exception.BadRequest;
import com.example.fc_users_service.domain.spi.AuthPersistencePort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AuthUseCaseTest {

  @InjectMocks private AuthUseCase authUseCase;

  @Mock private AuthPersistencePort authPersistencePort;

  @Test
  void login_ShouldReturnToken_WhenCredentialsAreValid() {
    String email = "user@example.com";
    String password = "securePassword";
    String expectedToken = "fake.jwt.token";

    when(authPersistencePort.authenticate(email, password)).thenReturn(expectedToken);

    String result = authUseCase.login(email, password);

    assertEquals(expectedToken, result);
    verify(authPersistencePort).authenticate(email, password);
  }

  @Test
  void login_ShouldThrowBadRequest_WhenAuthenticationFails() {
    String email = "user@example.com";
    String password = "wrongPassword";

    when(authPersistencePort.authenticate(email, password)).thenThrow(new RuntimeException());

    assertThrows(BadRequest.class, () -> authUseCase.login(email, password));

    verify(authPersistencePort).authenticate(email, password);
  }
}
