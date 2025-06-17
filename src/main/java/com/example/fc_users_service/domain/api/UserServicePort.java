package com.example.fc_users_service.domain.api;

import com.example.fc_users_service.domain.model.User;

public interface UserServicePort {
  void saveUser(User user, String roleName, Long restaurantId);

  Boolean userWithRoleExists(Long userId, String role);

  Boolean doesEmailMatchRoleId(Long userId, String currentUserEmail, String role);

  String getUserPhone(Long userId);

  Long getUserId(String currentUserEmail);

  Long getEmployeeRestaurant(String currentUserEmail);

  Long getUserEmail(Long userId);
}
