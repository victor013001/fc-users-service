package com.example.fc_users_service.util.data;

import com.example.fc_users_service.infrastructure.entrypoint.dto.UserRequest;
import java.time.LocalDate;

public class UserRequestData {
  private UserRequestData() {
    throw new UnsupportedOperationException("Utility class");
  }

  public static UserRequest getValidUserRequest() {
    return new UserRequest(
        "Joe",
        "Doe",
        12345678,
        "1234567890123",
        LocalDate.now().minusYears(25),
        "Joe.Doe@example.com",
        "SecurePassword123");
  }

  public static UserRequest getInvalidUserRequest() {
    return new UserRequest(
        "Jane",
        "Smith",
        87654321,
        "1234567",
        LocalDate.now().minusYears(20),
        "jane.smith@example.com",
        "SecurePassword456");
  }
}
