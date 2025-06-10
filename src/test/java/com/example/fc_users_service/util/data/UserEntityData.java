package com.example.fc_users_service.util.data;

import static com.example.fc_users_service.util.data.RoleEntityData.getLandlordRole;

import com.example.fc_users_service.infrastructure.adapters.persistence.entity.UserEntity;

public class UserEntityData {
  private UserEntityData() {
    throw new UnsupportedOperationException("Utility class");
  }

  public static UserEntity getUserEntity() {
    return UserEntity.builder()
        .email("email@example.com")
        .password("$2a$10$9L6zdfcWYLhxDXB5VA60FuKtEYsq9J9ycBjqE2RUvoByTnJdtGeYu")
        .role(getLandlordRole())
        .build();
  }
}
