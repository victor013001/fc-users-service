package com.example.fc_users_service.domain.exceptions.standard_exception;

import com.example.fc_users_service.domain.enums.ServerResponses;
import com.example.fc_users_service.domain.exceptions.StandardException;

public class BadRequest extends StandardException {
  public BadRequest() {
    super(ServerResponses.BAD_REQUEST);
  }
}
