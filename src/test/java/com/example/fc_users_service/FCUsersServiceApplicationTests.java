package com.example.fc_users_service;

import com.example.fc_users_service.config.TestcontainersConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
@Import(TestcontainersConfig.class)
class FCUsersServiceApplicationTests {

  @Autowired PasswordEncoder passwordEncoder;

  @Test
  void contextLoads() {
    // This method is intentionally left empty to verify that the Spring application context loads
    System.out.println(passwordEncoder.encode("dummy"));
  }
}
