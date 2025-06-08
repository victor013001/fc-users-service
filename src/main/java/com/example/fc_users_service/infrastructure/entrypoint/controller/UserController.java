package com.example.fc_users_service.infrastructure.entrypoint.controller;

import static com.example.fc_users_service.domain.constants.HttpStatusConst.BAD_REQUEST;
import static com.example.fc_users_service.domain.constants.HttpStatusConst.CONFLICT;
import static com.example.fc_users_service.domain.constants.HttpStatusConst.CREATED;
import static com.example.fc_users_service.domain.constants.HttpStatusConst.SERVER_ERROR;
import static com.example.fc_users_service.domain.constants.MsgConst.BAD_REQUEST_MSG;
import static com.example.fc_users_service.domain.constants.MsgConst.SERVER_ERROR_MSG;
import static com.example.fc_users_service.domain.constants.MsgConst.USER_ALREADY_EXISTS_MSG;
import static com.example.fc_users_service.domain.constants.MsgConst.USER_CREATED_SUCCESSFULLY_MSG;
import static com.example.fc_users_service.domain.constants.RouterConst.LANDLORD_BASE_PATH;
import static com.example.fc_users_service.domain.constants.RouterConst.USER_BASE_PATH;
import static com.example.fc_users_service.domain.constants.SwaggerConst.CREATE_LANDLORD_OPERATION;
import static com.example.fc_users_service.domain.enums.ServerResponses.USER_CREATED_SUCCESSFULLY;

import com.example.fc_users_service.application.service.UserApplicationService;
import com.example.fc_users_service.domain.exceptions.StandardError;
import com.example.fc_users_service.infrastructure.entrypoint.dto.DefaultServerResponse;
import com.example.fc_users_service.infrastructure.entrypoint.dto.UserRequest;
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
@RequestMapping(USER_BASE_PATH)
public class UserController {

  private final UserApplicationService userApplicationService;

  @Operation(summary = CREATE_LANDLORD_OPERATION)
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = CREATED, description = USER_CREATED_SUCCESSFULLY_MSG),
        @ApiResponse(responseCode = CONFLICT, description = USER_ALREADY_EXISTS_MSG),
        @ApiResponse(responseCode = BAD_REQUEST, description = BAD_REQUEST_MSG),
        @ApiResponse(responseCode = SERVER_ERROR, description = SERVER_ERROR_MSG),
      })
  @PostMapping(LANDLORD_BASE_PATH)
  public ResponseEntity<DefaultServerResponse<String, StandardError>> createLandlord(
      @Valid @RequestBody final UserRequest userRequest) {
    userApplicationService.createLandlord(userRequest);
    return ResponseEntity.status(USER_CREATED_SUCCESSFULLY.getHttpStatus())
        .body(new DefaultServerResponse<>(USER_CREATED_SUCCESSFULLY.getMessage(), null));
  }
}
