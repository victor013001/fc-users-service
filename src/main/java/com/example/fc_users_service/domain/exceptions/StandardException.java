package com.example.fc_users_service.domain.exceptions;

import com.example.fc_users_service.domain.enums.ServerResponses;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class StandardException extends RuntimeException {
  private final int httpStatus;
  private final StandardError standardError;

  public StandardException(ServerResponses serverResponses) {
    super(serverResponses.getMessage());
    this.httpStatus = serverResponses.getHttpStatus();
    this.standardError =
        StandardError.builder()
            .code(serverResponses.getCode())
            .timestamp(LocalDateTime.now())
            .description(serverResponses.getMessage())
            .build();
  }
}
