package com.example.fc_users_service.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Roles {
  ADMIN("admin"),
  LANDLORD("landlord"),
  EMPLOYEE("employee"),
  CLIENT("client");

  private final String value;

  public String getValue() {
    return value;
  }
}
