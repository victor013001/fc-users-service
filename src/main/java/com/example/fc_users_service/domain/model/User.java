package com.example.fc_users_service.domain.model;

import java.time.LocalDate;

public record User(
    String firstName,
    String lastName,
    Integer documentNumber,
    String phoneNumber,
    LocalDate birthDate,
    String email,
    String password) {}
