package com.example.fc_users_service.infrastructure.entrypoint.controller;

import static com.example.fc_users_service.domain.constants.RouterConst.LANDLORD_BASE_PATH;
import static com.example.fc_users_service.domain.constants.RouterConst.USER_BASE_PATH;
import static com.example.fc_users_service.domain.enums.ServerResponses.USER_CREATED_SUCCESSFULLY;
import static com.example.fc_users_service.util.data.UserRequestData.getInvalidUserRequest;
import static com.example.fc_users_service.util.data.UserRequestData.getValidUserRequest;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.fc_users_service.application.service.UserApplicationService;
import com.example.fc_users_service.infrastructure.entrypoint.dto.UserRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

  @InjectMocks private UserController userController;

  @Mock private UserApplicationService userApplicationService;

  private MockMvc mockMvc;
  private ObjectMapper objectMapper;

  @BeforeEach
  public void setUp() {
    mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
  }

  @Test
  public void createLandlord_Success() throws Exception {
    var userRequest = getValidUserRequest();

    String requestJson = objectMapper.writeValueAsString(userRequest);

    Mockito.doNothing().when(userApplicationService).createLandlord(any(UserRequest.class));

    mockMvc
        .perform(
            MockMvcRequestBuilders.post(USER_BASE_PATH + LANDLORD_BASE_PATH)
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.data").value(USER_CREATED_SUCCESSFULLY.getMessage()))
        .andExpect(jsonPath("$.error").doesNotExist());
  }

  @Test
  public void createLandlord_BadRequest() throws Exception {
    var userRequest = getInvalidUserRequest();

    String requestJson = objectMapper.writeValueAsString(userRequest);

    mockMvc
        .perform(
            MockMvcRequestBuilders.post(USER_BASE_PATH + LANDLORD_BASE_PATH)
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
  }
}
