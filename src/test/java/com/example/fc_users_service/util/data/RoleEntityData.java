package com.example.fc_users_service.util.data;

import com.example.fc_users_service.infrastructure.adapters.persistence.entity.RoleEntity;

public class RoleEntityData {
  private RoleEntityData() {
    throw new UnsupportedOperationException("Utility class");
  }

  public static RoleEntity getLandlordRole() {
    return RoleEntity.builder()
        .id(1L)
        .name("landlord")
        .description("landlord data util role")
        .build();
  }
}
