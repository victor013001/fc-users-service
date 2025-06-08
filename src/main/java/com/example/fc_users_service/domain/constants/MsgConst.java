package com.example.fc_users_service.domain.constants;

import lombok.experimental.UtilityClass;

@UtilityClass
public class MsgConst {
  public static final String USER_CREATED_SUCCESSFULLY_MSG = "The user was created successfully.";
  public static final String USER_ALREADY_EXISTS_MSG =
      "The user could not be created due to a data conflict.";
  public static final String BAD_REQUEST_MSG =
      "The request could not be processed due to invalid or incomplete data.";
  public static final String SERVER_ERROR_MSG =
      "An unexpected server error occurred. Please try again later.";
}
