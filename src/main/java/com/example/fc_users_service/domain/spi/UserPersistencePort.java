package com.example.fc_users_service.domain.spi;

import com.example.fc_users_service.domain.model.User;

public interface UserPersistencePort {
  void saveUser(User user, String roleName);

  boolean existsByEmail(String email);

  boolean existsByDocumentNumber(Integer documentNumber);
}
