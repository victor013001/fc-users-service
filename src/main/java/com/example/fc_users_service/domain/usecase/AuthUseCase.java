package com.example.fc_users_service.domain.usecase;

import com.example.fc_users_service.domain.api.AuthServicePort;
import com.example.fc_users_service.domain.spi.AuthPersistencePort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AuthUseCase implements AuthServicePort {

  private final AuthPersistencePort authPersistencePort;

  @Override
  public String login(String email, String password) {
    return authPersistencePort.authenticate(email, password);
  }
}
