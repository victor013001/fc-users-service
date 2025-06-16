package com.example.fc_users_service.application.service.hanlder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.fc_users_service.application.dto.AuthenticationRequest;
import com.example.fc_users_service.domain.api.AuthServicePort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AuthApplicationServiceHandlerTest {

  @InjectMocks private AuthApplicationServiceHandler authApplicationServiceHandler;

  @Mock private AuthServicePort authServicePort;

  @Test
  void authenticate_ShouldCallServiceWithEmailAndPassword() {
    var authRequest = new AuthenticationRequest("user@example.com", "password123");
    var expectedToken = "mocked.jwt.token";

    when(authServicePort.login(authRequest.email(), authRequest.password()))
        .thenReturn(expectedToken);

    String result = authApplicationServiceHandler.authenticate(authRequest);

    assertEquals(expectedToken, result);
    verify(authServicePort).login(authRequest.email(), authRequest.password());
  }
}
