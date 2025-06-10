package com.example.fc_users_service.domain.api;

import com.example.fc_users_service.domain.model.User;

public interface UserServicePort {
  void saveUser(User user, String roleName);

  Boolean userWithRoleExists(Long userId, String role);

  Boolean doesEmailMatchRoleId(Long userId, String currentUserEmail, String role);
}
