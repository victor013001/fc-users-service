package com.example.fc_users_service.infrastructure.adapters.persistence.adapter;

import static com.example.fc_users_service.util.data.RoleEntityData.getLandlordRole;
import static com.example.fc_users_service.util.data.UserData.getValidUser;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.fc_users_service.infrastructure.adapters.persistence.entity.UserEntity;
import com.example.fc_users_service.infrastructure.adapters.persistence.mapper.UserEntityMapper;
import com.example.fc_users_service.infrastructure.adapters.persistence.mapper.UserEntityMapperImpl;
import com.example.fc_users_service.infrastructure.adapters.persistence.repository.RoleRepository;
import com.example.fc_users_service.infrastructure.adapters.persistence.repository.UserRepository;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserPersistenceAdapterTest {

  @InjectMocks private UserPersistenceAdapter userPersistenceAdapter;

  @Spy private UserEntityMapper userEntityMapper = new UserEntityMapperImpl();

  @Mock private UserRepository userRepository;

  @Mock private RoleRepository roleRepository;

  @Test
  void saveUser_ShouldSaveWithCorrectRole() {
    String roleName = "LANDLORD";

    var role = getLandlordRole();
    var user = getValidUser();

    when(roleRepository.findByName(roleName)).thenReturn(Optional.of(role));
    when(userRepository.save(any(UserEntity.class))).thenReturn(UserEntity.builder().build());

    userPersistenceAdapter.saveUser(user, roleName);

    verify(roleRepository).findByName(roleName);
    verify(userEntityMapper).toEntity(user, role);
    verify(userRepository).save(any(UserEntity.class));
  }

  @Test
  void saveUser_ShouldThrow_WhenRoleNotFound() {
    var user = getValidUser();

    when(roleRepository.findByName(anyString())).thenReturn(Optional.empty());

    assertThrows(
        NoSuchElementException.class, () -> userPersistenceAdapter.saveUser(user, "NO_ROLE"));
  }

  @Test
  void existsByEmail_ShouldReturnTrue() {
    String email = "test@example.com";
    when(userRepository.existsByEmail(email)).thenReturn(true);

    assertTrue(userPersistenceAdapter.existsByEmail(email));
    verify(userRepository).existsByEmail(email);
  }

  @Test
  void existsByDocumentNumber_ShouldReturnFalse() {
    Integer document = 12345678;
    when(userRepository.existsByDocumentNumber(document.toString())).thenReturn(false);

    assertFalse(userPersistenceAdapter.existsByDocumentNumber(document));
    verify(userRepository).existsByDocumentNumber(document.toString());
  }

  @Test
  void existsByIdAndRoleName_ShouldReturnTrue() {
    Long userId = 1L;
    String roleName = "LANDLORD";

    when(userRepository.existsByIdAndRole_Name(userId, roleName)).thenReturn(true);

    Boolean result = userPersistenceAdapter.existsByIdAndRoleName(userId, roleName);

    assertTrue(result);
    verify(userRepository).existsByIdAndRole_Name(userId, roleName);
  }
}
