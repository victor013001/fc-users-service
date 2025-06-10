package com.example.fc_users_service.domain.usecase;

import static com.example.fc_users_service.util.data.UserData.getUnderageUser;
import static com.example.fc_users_service.util.data.UserData.getValidUser;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.fc_users_service.domain.enums.Roles;
import com.example.fc_users_service.domain.exceptions.standard_exception.BadRequest;
import com.example.fc_users_service.domain.spi.UserPersistencePort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserUseCaseTest {

  private final String ROLE = "USER";
  @InjectMocks UserUseCase userUseCase;
  @Mock UserPersistencePort userPersistencePort;

  @Test
  void saveValidUser() {
    var user = getValidUser();

    when(userPersistencePort.existsByEmail(user.email())).thenReturn(false);
    when(userPersistencePort.existsByDocumentNumber(user.documentNumber())).thenReturn(false);

    userUseCase.saveUser(user, ROLE);

    verify(userPersistencePort).existsByEmail(user.email());
    verify(userPersistencePort).existsByDocumentNumber(user.documentNumber());
    verify(userPersistencePort).saveUser(user, ROLE);
  }

  @Test
  void saveUser_Underage() {
    var user = getUnderageUser();

    assertThrows(BadRequest.class, () -> userUseCase.saveUser(user, ROLE));

    verify(userPersistencePort, never()).existsByEmail(anyString());
    verify(userPersistencePort, never()).existsByDocumentNumber(anyInt());
    verify(userPersistencePort, never()).saveUser(any(), anyString());
  }

  @Test
  void saveUser_EmailExists() {
    var user = getValidUser();

    when(userPersistencePort.existsByDocumentNumber(user.documentNumber())).thenReturn(false);
    when(userPersistencePort.existsByEmail(user.email())).thenReturn(true);

    assertThrows(BadRequest.class, () -> userUseCase.saveUser(user, ROLE));

    verify(userPersistencePort).existsByDocumentNumber(user.documentNumber());
    verify(userPersistencePort).existsByEmail(user.email());
    verify(userPersistencePort, never()).saveUser(any(), anyString());
  }

  @Test
  void saveUser_DocumentExists() {
    var user = getValidUser();

    when(userPersistencePort.existsByDocumentNumber(user.documentNumber())).thenReturn(true);

    assertThrows(BadRequest.class, () -> userUseCase.saveUser(user, ROLE));

    verify(userPersistencePort, never()).existsByEmail(user.email());
    verify(userPersistencePort).existsByDocumentNumber(user.documentNumber());
    verify(userPersistencePort, never()).saveUser(any(), anyString());
  }

  @Test
  void userWithRoleExists_ValidData() {
    Long landlordId = 1L;
    String role = Roles.LANDLORD.getValue();

    when(userPersistencePort.existsByIdAndRoleName(landlordId, role)).thenReturn(true);

    Boolean result = userUseCase.userWithRoleExists(landlordId, role);

    assertTrue(result);
    verify(userPersistencePort).existsByIdAndRoleName(landlordId, role);
  }

  @Test
  void doesEmailMatchLandlordId_Valid() {
    Long landlordId = 1L;
    String email = "landlord@example.com";
    String role = Roles.LANDLORD.getValue();

    when(userPersistencePort.existsByIdAndRoleName(landlordId, role)).thenReturn(true);
    when(userPersistencePort.existsByIdAndEmail(landlordId, email)).thenReturn(true);

    Boolean result = userUseCase.doesEmailMatchRoleId(landlordId, email, role);

    assertTrue(result);
    verify(userPersistencePort).existsByIdAndRoleName(landlordId, role);
    verify(userPersistencePort).existsByIdAndEmail(landlordId, email);
  }
}
