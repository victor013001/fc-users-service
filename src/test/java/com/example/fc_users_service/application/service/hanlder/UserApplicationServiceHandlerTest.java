package com.example.fc_users_service.application.service.hanlder;

import static com.example.fc_users_service.util.data.UserRequestData.getValidUserRequest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.fc_users_service.application.mapper.UserMapper;
import com.example.fc_users_service.application.mapper.UserMapperImpl;
import com.example.fc_users_service.domain.api.UserServicePort;
import com.example.fc_users_service.domain.enums.Roles;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class UserApplicationServiceHandlerTest {

  @InjectMocks private UserApplicationServiceHandler userApplicationServiceHandler;

  @Spy private UserMapper userMapper = new UserMapperImpl();

  @Mock private PasswordEncoder passwordEncoder;

  @Mock private UserServicePort userService;

  @Mock private Authentication auth;

  @Mock private SecurityContext securityContext;

  @Test
  void createLandlord_ShouldEncodePasswordAndSaveUser() {
    var request = getValidUserRequest();
    String encodedPassword = "encodedPassword";

    when(passwordEncoder.encode(anyString())).thenReturn(encodedPassword);

    userApplicationServiceHandler.createLandlord(request);

    verify(passwordEncoder).encode(request.password());
    verify(userMapper).toModel(request, encodedPassword);
    verify(userService)
        .saveUser(
            argThat(user -> user.password().equals(encodedPassword)),
            eq(Roles.LANDLORD.getValue()),
            any());
  }

  @Test
  void landlordExists() {
    var landlordId = 1L;
    when(userService.userWithRoleExists(anyLong(), anyString())).thenReturn(true);
    userApplicationServiceHandler.landlordExists(landlordId);
    verify(userService).userWithRoleExists(landlordId, Roles.LANDLORD.getValue());
  }

  @Test
  void doesEmailMatchLandlordId_ShouldCallServiceWithCurrentEmailAndLandlordRole() {
    Long landlordId = 1L;
    String email = "test@example.com";

    when(auth.getName()).thenReturn(email);
    when(securityContext.getAuthentication()).thenReturn(auth);
    SecurityContextHolder.setContext(securityContext);

    when(userService.doesEmailMatchRoleId(landlordId, email, Roles.LANDLORD.getValue()))
        .thenReturn(true);

    Boolean result = userApplicationServiceHandler.doesEmailMatchLandlordId(landlordId);

    assertTrue(result);
    verify(userService).doesEmailMatchRoleId(landlordId, email, Roles.LANDLORD.getValue());
  }

  @Test
  void createEmployee_ShouldEncodePasswordAndSaveUser() {
    var request = getValidUserRequest();
    String encodedPassword = "encodedPassword";
    var restaurantId = 1L;

    when(passwordEncoder.encode(anyString())).thenReturn(encodedPassword);

    userApplicationServiceHandler.createEmployee(request, restaurantId);

    verify(passwordEncoder).encode(request.password());
    verify(userMapper).toModel(request, encodedPassword);
    verify(userService)
        .saveUser(
            argThat(user -> user.password().equals(encodedPassword)),
            eq(Roles.EMPLOYEE.getValue()),
            anyLong());
  }

  @Test
  void createClient_ShouldEncodePasswordAndSaveUser() {
    var request = getValidUserRequest();
    String encodedPassword = "encodedPassword";

    when(passwordEncoder.encode(anyString())).thenReturn(encodedPassword);

    userApplicationServiceHandler.createClient(request);

    verify(passwordEncoder).encode(request.password());
    verify(userMapper).toModel(request, encodedPassword);
    verify(userService)
        .saveUser(
            argThat(user -> user.password().equals(encodedPassword)),
            eq(Roles.CLIENT.getValue()),
            any());
  }

  @Test
  void getUserPhone() {
    Long userId = 1L;
    var phone = "+1234567890123";
    when(userService.getUserPhone(anyLong())).thenReturn(phone);
    var phoneResponse = userApplicationServiceHandler.getUserPhone(userId);
    assertEquals(phone, phoneResponse);
    verify(userService).getUserPhone(userId);
  }
}
