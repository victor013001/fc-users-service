package com.example.fc_users_service.application.service;

import com.example.fc_users_service.infrastructure.entrypoint.dto.UserRequest;

public interface UserApplicationService {
  void createLandlord(UserRequest userRequest);

  Boolean landlordExists(Long landlordId);

  Boolean doesEmailMatchLandlordId(Long landlordId);

  void createEmployee(UserRequest userRequest);

  void createClient(UserRequest userRequest);

  String getUserPhone(Long userId);
}
