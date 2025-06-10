package com.example.fc_users_service.infrastructure.entrypoint.dto;

import jakarta.validation.constraints.NotBlank;

public record AuthenticationRequest(
    @NotBlank(message = "Email is mandatory") String email,
    @NotBlank(message = "Password is mandatory") String password) {}
