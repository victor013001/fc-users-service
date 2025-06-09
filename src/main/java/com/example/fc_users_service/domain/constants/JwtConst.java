package com.example.fc_users_service.domain.constants;

import lombok.experimental.UtilityClass;

@UtilityClass
public class JwtConst {
  public static final Long EXPIRATION_TIME = 1000L * 60 * 24;
  public static final String ROLE_CLAIM = "role";
}
