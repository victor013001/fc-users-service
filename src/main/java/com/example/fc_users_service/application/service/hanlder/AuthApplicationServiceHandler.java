package com.example.fc_users_service.application.service.hanlder;

import com.example.fc_users_service.application.dto.AuthenticationRequest;
import com.example.fc_users_service.application.service.AuthApplicationService;
import com.example.fc_users_service.domain.api.AuthServicePort;
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
