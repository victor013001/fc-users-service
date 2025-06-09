package com.example.fc_users_service.application.service;

import com.example.fc_users_service.infrastructure.entrypoint.dto.AuthenticationRequest;

public interface AuthApplicationService {
  String authenticate(AuthenticationRequest authRequest);
}
