package com.example.fc_users_service.domain.usecase;

import com.example.fc_users_service.domain.api.UserServicePort;
import com.example.fc_users_service.domain.exceptions.standard_exception.BadRequest;
import com.example.fc_users_service.domain.exceptions.standard_exception.UserAlreadyExists;
import com.example.fc_users_service.domain.model.User;
import com.example.fc_users_service.domain.spi.UserPersistencePort;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserUseCase implements UserServicePort {

  private final UserPersistencePort userPersistencePort;

  private final int ADULT_AGE = 18;

  @Override
  public void saveUser(User user, String roleName) {
    validAdult(user.birthDate());
    validDocumentNumber(user.documentNumber());
    validEmail(user.email());
    userPersistencePort.saveUser(user, roleName);
  }

  @Override
  public Boolean userWithRoleExists(Long userId, String role) {
    return userPersistencePort.existsByIdAndRoleName(userId, role);
  }

  @Override
  public Boolean doesEmailMatchRoleId(Long userId, String currentUserEmail, String role) {
    return userPersistencePort.existsByIdAndRoleName(userId, role)
        && userPersistencePort.existsByIdAndEmail(userId, currentUserEmail);
  }

  private void validEmail(String email) {
    if (userPersistencePort.existsByEmail(email)) {
      throw new UserAlreadyExists();
    }
  }

  private void validDocumentNumber(Integer documentNumber) {
    if (userPersistencePort.existsByDocumentNumber(documentNumber)) {
      throw new UserAlreadyExists();
    }
  }

  private void validAdult(LocalDate birthDate) {
    if (!birthDate.isBefore(LocalDate.now().minusYears(ADULT_AGE))) {
      throw new BadRequest();
    }
  }
}
