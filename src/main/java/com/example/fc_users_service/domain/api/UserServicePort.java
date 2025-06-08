package com.example.fc_users_service.domain.api;

import com.example.fc_users_service.domain.model.User;

public interface UserServicePort {
  void saveUser(User user, String roleName);
}
