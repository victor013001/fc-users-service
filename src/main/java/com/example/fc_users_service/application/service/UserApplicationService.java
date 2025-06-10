package com.example.fc_users_service.application.service;

import com.example.fc_users_service.infrastructure.entrypoint.dto.UserRequest;

public interface UserApplicationService {
  void createLandlord(UserRequest userRequest);

  Boolean landlordExists(Long landlordId);
}
