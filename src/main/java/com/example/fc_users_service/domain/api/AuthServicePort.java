package com.example.fc_users_service.domain.api;

public interface AuthServicePort {
  String login(String email, String password);
}
