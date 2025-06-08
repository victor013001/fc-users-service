package com.example.fc_users_service.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class BCryptConfig {

  @Bean
  public PasswordEncoder bCryptEncoder() {
    return new BCryptPasswordEncoder();
  }
}
