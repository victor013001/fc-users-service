package com.example.fc_users_service.util.data;

import com.example.fc_users_service.domain.model.User;
import java.time.LocalDate;

public class UserData {
  private UserData() {
    throw new UnsupportedOperationException("Utility class");
  }

  public static User getValidUser() {
    return new User(
        "Joe",
        "Doe",
        12345678,
        "1234567890123",
        LocalDate.now().minusYears(25),
        "Joe.Doe@example.com",
        "SecurePassword123");
  }

  public static User getUnderageUser() {
    return new User(
        "Jane",
        "Smith",
        87654321,
        "1234567890123",
        LocalDate.now().minusYears(16),
        "jane.smith@example.com",
        "SecurePassword456");
  }
}
