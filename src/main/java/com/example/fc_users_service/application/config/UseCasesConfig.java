package com.example.fc_users_service.application.config;

import com.example.fc_users_service.application.service.security.JwtApplicationService;
import com.example.fc_users_service.domain.api.AuthServicePort;
import com.example.fc_users_service.domain.api.UserServicePort;
import com.example.fc_users_service.domain.spi.AuthPersistencePort;
import com.example.fc_users_service.domain.spi.UserPersistencePort;
import com.example.fc_users_service.domain.usecase.AuthUseCase;
import com.example.fc_users_service.domain.usecase.UserUseCase;
import com.example.fc_users_service.infrastructure.adapters.persistence.adapter.AuthPersistenceAdapter;
import com.example.fc_users_service.infrastructure.adapters.persistence.adapter.UserPersistenceAdapter;
import com.example.fc_users_service.infrastructure.adapters.persistence.mapper.EmployeeRestaurantEntityMapper;
import com.example.fc_users_service.infrastructure.adapters.persistence.mapper.UserEntityMapper;
import com.example.fc_users_service.infrastructure.adapters.persistence.repository.EmployeeRestaurantRepository;
import com.example.fc_users_service.infrastructure.adapters.persistence.repository.RoleRepository;
import com.example.fc_users_service.infrastructure.adapters.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;

@Configuration
@RequiredArgsConstructor
public class UseCasesConfig {
  private final UserEntityMapper userEntityMapper;
  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final AuthenticationManager authenticationManager;
  private final JwtApplicationService jwtService;
  private final EmployeeRestaurantRepository employeeRestaurantRepository;
  private final EmployeeRestaurantEntityMapper employeeRestaurantEntityMapper;

  @Bean
  public UserPersistencePort userPersistencePort() {
    return new UserPersistenceAdapter(
        userEntityMapper,
        userRepository,
        roleRepository,
        employeeRestaurantRepository,
        employeeRestaurantEntityMapper);
  }

  @Bean
  public UserServicePort userServicePort(UserPersistencePort userPersistencePort) {
    return new UserUseCase(userPersistencePort);
  }

  @Bean
  public AuthPersistencePort authPersistencePort() {
    return new AuthPersistenceAdapter(authenticationManager, jwtService, userRepository);
  }

  @Bean
  public AuthServicePort authServicePort(AuthPersistencePort authPersistencePort) {
    return new AuthUseCase(authPersistencePort);
  }
}
