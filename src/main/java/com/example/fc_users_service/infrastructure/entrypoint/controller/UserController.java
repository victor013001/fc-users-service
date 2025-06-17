package com.example.fc_users_service.infrastructure.entrypoint.controller;

import static com.example.fc_users_service.domain.constants.HttpStatusConst.BAD_REQUEST;
import static com.example.fc_users_service.domain.constants.HttpStatusConst.CONFLICT;
import static com.example.fc_users_service.domain.constants.HttpStatusConst.CREATED;
import static com.example.fc_users_service.domain.constants.HttpStatusConst.OK;
import static com.example.fc_users_service.domain.constants.HttpStatusConst.OK_INT;
import static com.example.fc_users_service.domain.constants.HttpStatusConst.SERVER_ERROR;
import static com.example.fc_users_service.domain.constants.MsgConst.BAD_REQUEST_MSG;
import static com.example.fc_users_service.domain.constants.MsgConst.EMPLOYEE_RESTAURANT_MSG;
import static com.example.fc_users_service.domain.constants.MsgConst.LANDLORD_FOUND_MSG;
import static com.example.fc_users_service.domain.constants.MsgConst.SERVER_ERROR_MSG;
import static com.example.fc_users_service.domain.constants.MsgConst.USER_ALREADY_EXISTS_MSG;
import static com.example.fc_users_service.domain.constants.MsgConst.USER_CREATED_SUCCESSFULLY_MSG;
import static com.example.fc_users_service.domain.constants.MsgConst.USER_EMAIL_MSG;
import static com.example.fc_users_service.domain.constants.MsgConst.USER_ID_MSG;
import static com.example.fc_users_service.domain.constants.MsgConst.USER_PHONE_MSG;
import static com.example.fc_users_service.domain.constants.RouterConst.CLIENT_BASE_PATH;
import static com.example.fc_users_service.domain.constants.RouterConst.EMAIL_BASE_PATH;
import static com.example.fc_users_service.domain.constants.RouterConst.EMPLOYEE_BASE_PATH;
import static com.example.fc_users_service.domain.constants.RouterConst.EXISTS_PATH;
import static com.example.fc_users_service.domain.constants.RouterConst.ID_BASE_PATH;
import static com.example.fc_users_service.domain.constants.RouterConst.LANDLORD_BASE_PATH;
import static com.example.fc_users_service.domain.constants.RouterConst.PHONE_PATH;
import static com.example.fc_users_service.domain.constants.RouterConst.RESTAURANT_BASE_PATH;
import static com.example.fc_users_service.domain.constants.RouterConst.USER_BASE_PATH;
import static com.example.fc_users_service.domain.constants.SwaggerConst.CREATE_CLIENT_OPERATION;
import static com.example.fc_users_service.domain.constants.SwaggerConst.CREATE_EMPLOYEE_OPERATION;
import static com.example.fc_users_service.domain.constants.SwaggerConst.CREATE_LANDLORD_OPERATION;
import static com.example.fc_users_service.domain.constants.SwaggerConst.EMAIL_BELONGS_TO_LANDLORD;
import static com.example.fc_users_service.domain.constants.SwaggerConst.GET_EMPLOYEE_RESTAURANT_OPERATION;
import static com.example.fc_users_service.domain.constants.SwaggerConst.GET_USER_EMAIL_OPERATION;
import static com.example.fc_users_service.domain.constants.SwaggerConst.GET_USER_ID_OPERATION;
import static com.example.fc_users_service.domain.constants.SwaggerConst.GET_USER_PHONE_OPERATION;
import static com.example.fc_users_service.domain.enums.ServerResponses.USER_CREATED_SUCCESSFULLY;

import com.example.fc_users_service.application.dto.DefaultServerResponse;
import com.example.fc_users_service.application.dto.UserRequest;
import com.example.fc_users_service.application.service.UserApplicationService;
import com.example.fc_users_service.domain.exceptions.StandardError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
  @PreAuthorize("hasAuthority('admin')")
  public ResponseEntity<DefaultServerResponse<String, StandardError>> createLandlord(
      @Valid @RequestBody final UserRequest userRequest) {
    userApplicationService.createLandlord(userRequest);
    return ResponseEntity.status(USER_CREATED_SUCCESSFULLY.getHttpStatus())
        .body(new DefaultServerResponse<>(USER_CREATED_SUCCESSFULLY.getMessage(), null));
  }

  @Operation(summary = CREATE_LANDLORD_OPERATION)
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = OK, description = LANDLORD_FOUND_MSG),
        @ApiResponse(responseCode = BAD_REQUEST, description = BAD_REQUEST_MSG),
        @ApiResponse(responseCode = SERVER_ERROR, description = SERVER_ERROR_MSG),
      })
  @GetMapping(LANDLORD_BASE_PATH + EXISTS_PATH + "/{landlord_id}")
  @PreAuthorize("hasAuthority('admin')")
  public ResponseEntity<DefaultServerResponse<Boolean, StandardError>> landlordExists(
      @PathVariable(name = "landlord_id") Long landlordId) {
    return ResponseEntity.status(OK_INT)
        .body(new DefaultServerResponse<>(userApplicationService.landlordExists(landlordId), null));
  }

  @Operation(summary = EMAIL_BELONGS_TO_LANDLORD)
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = OK, description = ""),
        @ApiResponse(responseCode = BAD_REQUEST, description = BAD_REQUEST_MSG),
        @ApiResponse(responseCode = SERVER_ERROR, description = SERVER_ERROR_MSG),
      })
  @GetMapping(LANDLORD_BASE_PATH + "/{landlord_id}/belongs")
  @PreAuthorize("hasAuthority('landlord')")
  public ResponseEntity<DefaultServerResponse<Boolean, StandardError>> doesLandlordBelongToEmail(
      @PathVariable("landlord_id") Long landlordId) {
    return ResponseEntity.status(OK_INT)
        .body(
            new DefaultServerResponse<>(
                userApplicationService.doesEmailMatchLandlordId(landlordId), null));
  }

  @Operation(summary = CREATE_EMPLOYEE_OPERATION)
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = CREATED, description = USER_CREATED_SUCCESSFULLY_MSG),
        @ApiResponse(responseCode = CONFLICT, description = USER_ALREADY_EXISTS_MSG),
        @ApiResponse(responseCode = BAD_REQUEST, description = BAD_REQUEST_MSG),
        @ApiResponse(responseCode = SERVER_ERROR, description = SERVER_ERROR_MSG),
      })
  @PostMapping(EMPLOYEE_BASE_PATH + RESTAURANT_BASE_PATH + "/{restaurant_id}")
  @PreAuthorize("hasAuthority('landlord')")
  public ResponseEntity<DefaultServerResponse<String, StandardError>> createEmployee(
      @Valid @RequestBody final UserRequest userRequest,
      @PathVariable(name = "restaurant_id") Long restaurantId) {
    userApplicationService.createEmployee(userRequest, restaurantId);
    return ResponseEntity.status(USER_CREATED_SUCCESSFULLY.getHttpStatus())
        .body(new DefaultServerResponse<>(USER_CREATED_SUCCESSFULLY.getMessage(), null));
  }

  @Operation(summary = CREATE_CLIENT_OPERATION)
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = CREATED, description = USER_CREATED_SUCCESSFULLY_MSG),
        @ApiResponse(responseCode = CONFLICT, description = USER_ALREADY_EXISTS_MSG),
        @ApiResponse(responseCode = BAD_REQUEST, description = BAD_REQUEST_MSG),
        @ApiResponse(responseCode = SERVER_ERROR, description = SERVER_ERROR_MSG),
      })
  @PostMapping(CLIENT_BASE_PATH)
  public ResponseEntity<DefaultServerResponse<String, StandardError>> createClient(
      @Valid @RequestBody final UserRequest userRequest) {
    userApplicationService.createClient(userRequest);
    return ResponseEntity.status(USER_CREATED_SUCCESSFULLY.getHttpStatus())
        .body(new DefaultServerResponse<>(USER_CREATED_SUCCESSFULLY.getMessage(), null));
  }

  @Operation(summary = GET_USER_PHONE_OPERATION)
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = OK, description = USER_PHONE_MSG),
        @ApiResponse(responseCode = BAD_REQUEST, description = BAD_REQUEST_MSG),
        @ApiResponse(responseCode = SERVER_ERROR, description = SERVER_ERROR_MSG),
      })
  @GetMapping("/{user_id}" + PHONE_PATH)
  @PreAuthorize("hasAuthority('employee')")
  public ResponseEntity<DefaultServerResponse<String, StandardError>> getUserPhone(
      @PathVariable(name = "user_id") Long userId) {
    return ResponseEntity.status(OK_INT)
        .body(new DefaultServerResponse<>(userApplicationService.getUserPhone(userId), null));
  }

  @Operation(summary = GET_USER_ID_OPERATION)
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = OK, description = USER_ID_MSG),
        @ApiResponse(responseCode = BAD_REQUEST, description = BAD_REQUEST_MSG),
        @ApiResponse(responseCode = SERVER_ERROR, description = SERVER_ERROR_MSG),
      })
  @GetMapping(ID_BASE_PATH)
  @PreAuthorize("hasAnyAuthority('client', 'employee')")
  public ResponseEntity<DefaultServerResponse<Long, StandardError>> getUserId() {
    return ResponseEntity.status(OK_INT)
        .body(new DefaultServerResponse<>(userApplicationService.getUserId(), null));
  }

  @Operation(summary = GET_EMPLOYEE_RESTAURANT_OPERATION)
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = OK, description = EMPLOYEE_RESTAURANT_MSG),
        @ApiResponse(responseCode = BAD_REQUEST, description = BAD_REQUEST_MSG),
        @ApiResponse(responseCode = SERVER_ERROR, description = SERVER_ERROR_MSG),
      })
  @GetMapping("/employee/restaurant")
  @PreAuthorize("hasAuthority('employee')")
  public ResponseEntity<DefaultServerResponse<Long, StandardError>> getEmployeeRestaurant() {
    return ResponseEntity.status(OK_INT)
        .body(new DefaultServerResponse<>(userApplicationService.getEmployeeRestaurant(), null));
  }

  @Operation(summary = GET_USER_EMAIL_OPERATION)
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = OK, description = USER_EMAIL_MSG),
        @ApiResponse(responseCode = BAD_REQUEST, description = BAD_REQUEST_MSG),
        @ApiResponse(responseCode = SERVER_ERROR, description = SERVER_ERROR_MSG),
      })
  @GetMapping(EMAIL_BASE_PATH + "/{user_id}")
  @PreAuthorize("hasAnyAuthority('employee', 'client)")
  public ResponseEntity<DefaultServerResponse<Long, StandardError>> getUserEmail(
      @PathVariable(name = "user_id") Long userId) {
    return ResponseEntity.status(OK_INT)
        .body(new DefaultServerResponse<>(userApplicationService.getUserEmail(userId), null));
  }
}
