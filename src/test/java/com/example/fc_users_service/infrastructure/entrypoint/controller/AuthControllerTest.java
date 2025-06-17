package com.example.fc_users_service.infrastructure.entrypoint.controller;

import static com.example.fc_users_service.domain.constants.RouterConst.AUTH_BASE_PATH;
import static com.example.fc_users_service.domain.constants.RouterConst.LOGIN_PATH;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.fc_users_service.application.dto.AuthenticationRequest;
import com.example.fc_users_service.application.service.AuthApplicationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

  @InjectMocks private AuthController authController;

  @Mock private AuthApplicationService authService;

  private MockMvc mockMvc;
  private ObjectMapper objectMapper;

  @BeforeEach
  void setUp() {
    mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    objectMapper = new ObjectMapper();
  }

  @Test
  void login_ShouldReturnToken_WhenCredentialsAreValid() throws Exception {
    var request = new AuthenticationRequest("user@example.com", "password123");
    var token = "fake.jwt.token";

    when(authService.authenticate(any(AuthenticationRequest.class))).thenReturn(token);

    mockMvc
        .perform(
            MockMvcRequestBuilders.post(AUTH_BASE_PATH + LOGIN_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data").value(token))
        .andExpect(jsonPath("$.error").doesNotExist());
  }

  @Test
  void login_ShouldReturnBadRequest_WhenRequestIsInvalid() throws Exception {
    var invalidRequest = new AuthenticationRequest("", "");

    mockMvc
        .perform(
            MockMvcRequestBuilders.post(AUTH_BASE_PATH + LOGIN_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRequest)))
        .andExpect(status().isBadRequest());
  }
}
