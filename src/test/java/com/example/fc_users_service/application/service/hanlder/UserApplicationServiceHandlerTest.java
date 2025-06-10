package com.example.fc_users_service.application.service.hanlder;

import static com.example.fc_users_service.util.data.UserRequestData.getValidUserRequest;
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
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class UserApplicationServiceHandlerTest {

  @InjectMocks private UserApplicationServiceHandler userApplicationServiceHandler;

  @Spy private UserMapper userMapper = new UserMapperImpl();

  @Mock private PasswordEncoder passwordEncoder;

  @Mock private UserServicePort userService;

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
            eq(Roles.LANDLORD.getValue()));
  }

  @Test
  void landlordExists() {
    var landlordId = 1L;
    when(userService.userWithRoleExists(anyLong(), anyString())).thenReturn(true);
    userApplicationServiceHandler.landlordExists(landlordId);
    verify(userService).userWithRoleExists(landlordId, Roles.LANDLORD.getValue());
  }
}
