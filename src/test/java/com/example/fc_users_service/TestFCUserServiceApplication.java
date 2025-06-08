package com.example.fc_users_service;

import com.example.fc_users_service.config.TestcontainersConfig;
import org.springframework.boot.SpringApplication;

public class TestFCUserServiceApplication {
  public static void main(String[] args) {
    SpringApplication.from(FCUsersServiceApplication::main)
        .with(TestcontainersConfig.class)
        .run(args);
  }
}
