package com.example.fc_users_service.application.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDate;

public record UserRequest(
    @NotBlank(message = "First name is mandatory") String firstName,
    @NotBlank(message = "Last name is mandatory") String lastName,
    @NotNull(message = "Document number is mandatory") Integer documentNumber,
    @NotBlank(message = "Phone number is mandatory")
        @Pattern(regexp = "^\\+?\\d{13}$", message = "Phone number is not valid")
        String phoneNumber,
    @Past(message = "Birth date must be in the past.") LocalDate birthDate,
    @Email String email,
    @NotBlank(message = "Password is mandatory") String password) {}
