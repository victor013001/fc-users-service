package com.example.fc_users_service.infrastructure.entrypoint.dto;

public record DefaultServerResponse<T, E>(T data, E error) {}
