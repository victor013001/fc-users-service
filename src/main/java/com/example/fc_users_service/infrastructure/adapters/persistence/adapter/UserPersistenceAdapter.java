package com.example.fc_users_service.infrastructure.adapters.persistence.adapter;

import com.example.fc_users_service.domain.model.User;
import com.example.fc_users_service.domain.spi.UserPersistencePort;
import com.example.fc_users_service.infrastructure.adapters.persistence.entity.UserEntity;
import com.example.fc_users_service.infrastructure.adapters.persistence.mapper.EmployeeRestaurantEntityMapper;
import com.example.fc_users_service.infrastructure.adapters.persistence.mapper.UserEntityMapper;
import com.example.fc_users_service.infrastructure.adapters.persistence.repository.EmployeeRestaurantRepository;
import com.example.fc_users_service.infrastructure.adapters.persistence.repository.RoleRepository;
import com.example.fc_users_service.infrastructure.adapters.persistence.repository.UserRepository;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserPersistenceAdapter implements UserPersistencePort {
  private static final String LOG_PREFIX = "[USER_PERSISTENCE_ADAPTER] >>> ";

  private final UserEntityMapper userEntityMapper;
  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final EmployeeRestaurantRepository employeeRestaurantRepository;
  private final EmployeeRestaurantEntityMapper employeeRestaurantEntityMapper;

  @Override
  @Transactional
  public void saveUser(User user, String roleName, Long restaurantId) {
    roleRepository
        .findByName(roleName)
        .map(
            role -> {
              log.info(
                  "{} Saving user with document: {} and email: {}.",
                  LOG_PREFIX,
                  user.documentNumber(),
                  user.email());
              UserEntity userEntity = userEntityMapper.toEntity(user, role);
              userRepository.save(userEntity);
              if (Objects.nonNull(restaurantId))
                employeeRestaurantRepository.save(
                    employeeRestaurantEntityMapper.toEntity(userEntity, restaurantId));
              return userEntity;
            })
        .orElseThrow();
  }

  @Override
  @Transactional(readOnly = true)
  public boolean existsByEmail(String email) {
    log.info("{} Checking if email: {} exists.", LOG_PREFIX, email);
    return userRepository.existsByEmail(email);
  }

  @Override
  @Transactional(readOnly = true)
  public boolean existsByDocumentNumber(Integer documentNumber) {
    log.info("{} Checking if document number: {} exists.", LOG_PREFIX, documentNumber);
    return userRepository.existsByDocumentNumber(String.valueOf(documentNumber));
  }

  @Override
  public Boolean existsByIdAndRoleName(Long userId, String roleName) {
    log.info("{} Checking if user: {} exists and has role: {}.", LOG_PREFIX, userId, roleName);
    return userRepository.existsByIdAndRole_Name(userId, roleName);
  }

  @Override
  public boolean existsByIdAndEmail(Long userId, String userEmail) {
    log.info("{} Checking if user: {} belongs to email: {}.", LOG_PREFIX, userId, userEmail);
    return userRepository.existsByIdAndEmail(userId, userEmail);
  }

  @Override
  public String getUserPhone(Long userId) {
    return userRepository.getPhoneNumberById(userId);
  }

  @Override
  public Long getUserId(String currentUserEmail) {
    return userRepository.findIdByEmail(currentUserEmail);
  }

  @Override
  public Long getUserRestaurant(String currentUserEmail) {
    return employeeRestaurantRepository.findRestaurantIdByEmail(currentUserEmail);
  }

  @Override
  public Long getUserEmail(Long userId) {
    return userRepository.findEmailById(userId);
  }
}
