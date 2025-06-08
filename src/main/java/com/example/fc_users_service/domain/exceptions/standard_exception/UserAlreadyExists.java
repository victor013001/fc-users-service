package com.example.fc_users_service.domain.exceptions.standard_exception;

import com.example.fc_users_service.domain.enums.ServerResponses;
import com.example.fc_users_service.domain.exceptions.StandardException;

public class UserAlreadyExists extends StandardException {
  public UserAlreadyExists() {
    super(ServerResponses.USER_ALREADY_EXISTS);
  }
}
