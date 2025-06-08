package com.example.fc_users_service.application.config;

import com.example.fc_users_service.domain.api.UserServicePort;
import com.example.fc_users_service.domain.spi.UserPersistencePort;
import com.example.fc_users_service.domain.usecase.UserUseCase;
import com.example.fc_users_service.infrastructure.adapters.persistence.adapter.UserPersistenceAdapter;
import com.example.fc_users_service.infrastructure.adapters.persistence.mapper.UserEntityMapper;
import com.example.fc_users_service.infrastructure.adapters.persistence.repository.RoleRepository;
import com.example.fc_users_service.infrastructure.adapters.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class UseCasesConfig {
  private final UserEntityMapper userEntityMapper;
  private final UserRepository userRepository;
  private final RoleRepository roleRepository;

  @Bean
  public UserPersistencePort userPersistencePort() {
    return new UserPersistenceAdapter(userEntityMapper, userRepository, roleRepository);
  }

  @Bean
  public UserServicePort userServicePort(UserPersistencePort userPersistencePort) {
    return new UserUseCase(userPersistencePort);
  }
}
