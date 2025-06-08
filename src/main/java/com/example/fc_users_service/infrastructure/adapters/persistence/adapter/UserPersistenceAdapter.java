package com.example.fc_users_service.infrastructure.adapters.persistence.adapter;

import com.example.fc_users_service.domain.model.User;
import com.example.fc_users_service.domain.spi.UserPersistencePort;
import com.example.fc_users_service.infrastructure.adapters.persistence.mapper.UserEntityMapper;
import com.example.fc_users_service.infrastructure.adapters.persistence.repository.RoleRepository;
import com.example.fc_users_service.infrastructure.adapters.persistence.repository.UserRepository;
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

  @Override
  @Transactional
  public void saveUser(User user, String roleName) {
    roleRepository
        .findByName(roleName)
        .map(
            role -> {
              log.info(
                  "{} Saving user with document: {} and email: {}.",
                  LOG_PREFIX,
                  user.documentNumber(),
                  user.email());
              return userRepository.save(userEntityMapper.toEntity(user, role));
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
}
