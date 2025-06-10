package com.example.fc_users_service.domain.enums;

import static com.example.fc_users_service.domain.constants.HttpStatusConst.BAD_REQUEST_INT;
import static com.example.fc_users_service.domain.constants.HttpStatusConst.CONFLICT_INT;
import static com.example.fc_users_service.domain.constants.HttpStatusConst.CREATED_INT;
import static com.example.fc_users_service.domain.constants.HttpStatusConst.OK_INT;
import static com.example.fc_users_service.domain.constants.HttpStatusConst.SERVER_ERROR_INT;

import com.example.fc_users_service.domain.constants.MsgConst;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ServerResponses {
  BAD_REQUEST("E000", BAD_REQUEST_INT, MsgConst.BAD_REQUEST_MSG),
  SERVER_ERROR("E001", SERVER_ERROR_INT, MsgConst.SERVER_ERROR_MSG),
  USER_CREATED_SUCCESSFULLY("", CREATED_INT, MsgConst.USER_CREATED_SUCCESSFULLY_MSG),
  USER_ALREADY_EXISTS("E002", CONFLICT_INT, MsgConst.USER_ALREADY_EXISTS_MSG),
  AUTHENTICATED_SUCCESSFULLY("", OK_INT, MsgConst.AUTHENTICATED_SUCCESSFULLY_MSG);

  private final String code;
  private final int httpStatus;
  private final String message;
}
