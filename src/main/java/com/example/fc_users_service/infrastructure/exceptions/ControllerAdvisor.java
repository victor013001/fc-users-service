package com.example.fc_users_service.infrastructure.exceptions;

import static com.example.fc_users_service.domain.enums.ServerResponses.BAD_REQUEST;
import static com.example.fc_users_service.domain.enums.ServerResponses.SERVER_ERROR;

import com.example.fc_users_service.domain.exceptions.StandardError;
import com.example.fc_users_service.domain.exceptions.StandardException;
import com.example.fc_users_service.infrastructure.entrypoint.dto.DefaultServerResponse;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ControllerAdvisor {
  private static final String LOG_PREFIX = "[CONTROLLER_ADVISOR] >>> ";

  @ExceptionHandler(StandardException.class)
  protected ResponseEntity<DefaultServerResponse<Object, StandardError>> handlerStandardException(
      StandardException standardException) {
    log.error(
        "{} Standard exception with code caught: {}",
        LOG_PREFIX,
        standardException.getStandardError().getCode());
    return ResponseEntity.status(standardException.getHttpStatus())
        .body(new DefaultServerResponse<>(null, standardException.getStandardError()));
  }

  @ExceptionHandler(Exception.class)
  protected ResponseEntity<DefaultServerResponse<Object, StandardError>> handleGenericException(
      Exception ex) {
    return ResponseEntity.internalServerError()
        .body(new DefaultServerResponse<>(null, buildServerStandardError(ex.getMessage())));
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  protected ResponseEntity<DefaultServerResponse<Object, StandardError>>
      handleMethodArgumentNotValidException(
          MethodArgumentNotValidException argumentNotValidException) {
    return ResponseEntity.badRequest()
        .body(
            new DefaultServerResponse<>(
                null, buildServerStandardError(argumentNotValidException.getMessage())));
  }

  private StandardError buildServerStandardError(String message) {
    log.error("{} Server exception caught: {}", LOG_PREFIX, message);
    return StandardError.builder()
        .code(SERVER_ERROR.getCode())
        .description(SERVER_ERROR.getMessage())
        .timestamp(LocalDateTime.now())
        .build();
  }

  private StandardError buildBadRequestStandardError(String message) {
    log.error("{} Bad Request exception caught: {}", LOG_PREFIX, message);
    return StandardError.builder()
        .code(BAD_REQUEST.getCode())
        .description(BAD_REQUEST.getMessage())
        .timestamp(LocalDateTime.now())
        .build();
  }
}
