package com.example.fc_users_service.application.dto;

public record DefaultServerResponse<T, E>(T data, E error) {}
