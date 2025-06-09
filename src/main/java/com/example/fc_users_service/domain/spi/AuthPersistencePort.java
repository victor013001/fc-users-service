package com.example.fc_users_service.domain.spi;

public interface AuthPersistencePort {

  String authenticate(String email, String password);
}
