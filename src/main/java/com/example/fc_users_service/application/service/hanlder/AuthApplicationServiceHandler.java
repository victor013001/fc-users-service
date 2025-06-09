package com.example.fc_users_service.application.service.hanlder;

import com.example.fc_users_service.application.service.AuthApplicationService;
import com.example.fc_users_service.domain.api.AuthServicePort;
import com.example.fc_users_service.infrastructure.entrypoint.dto.AuthenticationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthApplicationServiceHandler implements AuthApplicationService {

  private final AuthServicePort authServicePort;

  @Override
  public String authenticate(AuthenticationRequest authRequest) {
    return authServicePort.login(authRequest.email(), authRequest.password());
  }
}
