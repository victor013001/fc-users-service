package com.example.fc_users_service.infrastructure.entrypoint.controller;

import static com.example.fc_users_service.domain.constants.HttpStatusConst.BAD_REQUEST;
import static com.example.fc_users_service.domain.constants.HttpStatusConst.SERVER_ERROR;
import static com.example.fc_users_service.domain.constants.MsgConst.BAD_REQUEST_MSG;
import static com.example.fc_users_service.domain.constants.MsgConst.SERVER_ERROR_MSG;
import static com.example.fc_users_service.domain.constants.RouterConst.AUTH_BASE_PATH;
import static com.example.fc_users_service.domain.constants.RouterConst.LOGIN_PATH;
import static com.example.fc_users_service.domain.constants.SwaggerConst.AUTHENTICATE_OPERATION;
import static com.example.fc_users_service.domain.enums.ServerResponses.AUTHENTICATED_SUCCESSFULLY;

import com.example.fc_users_service.application.service.AuthApplicationService;
import com.example.fc_users_service.domain.exceptions.StandardError;
import com.example.fc_users_service.infrastructure.entrypoint.dto.AuthenticationRequest;
import com.example.fc_users_service.infrastructure.entrypoint.dto.DefaultServerResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(AUTH_BASE_PATH)
public class AuthController {

  private final AuthApplicationService authService;

  @Operation(summary = AUTHENTICATE_OPERATION)
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = BAD_REQUEST, description = BAD_REQUEST_MSG),
        @ApiResponse(responseCode = SERVER_ERROR, description = SERVER_ERROR_MSG),
      })
  @PostMapping(LOGIN_PATH)
  public ResponseEntity<DefaultServerResponse<String, StandardError>> login(
      @Valid @RequestBody final AuthenticationRequest authRequest) {
    return ResponseEntity.status(AUTHENTICATED_SUCCESSFULLY.getHttpStatus())
        .body(new DefaultServerResponse<>(authService.authenticate(authRequest), null));
  }
}
