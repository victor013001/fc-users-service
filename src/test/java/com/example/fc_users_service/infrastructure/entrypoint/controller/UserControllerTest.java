package com.example.fc_users_service.infrastructure.entrypoint.controller;

import static com.example.fc_users_service.domain.constants.RouterConst.CLIENT_BASE_PATH;
import static com.example.fc_users_service.domain.constants.RouterConst.EMPLOYEE_BASE_PATH;
import static com.example.fc_users_service.domain.constants.RouterConst.EXISTS_PATH;
import static com.example.fc_users_service.domain.constants.RouterConst.LANDLORD_BASE_PATH;
import static com.example.fc_users_service.domain.constants.RouterConst.PHONE_PATH;
import static com.example.fc_users_service.domain.constants.RouterConst.RESTAURANT_BASE_PATH;
import static com.example.fc_users_service.domain.constants.RouterConst.USER_BASE_PATH;
import static com.example.fc_users_service.domain.enums.ServerResponses.USER_CREATED_SUCCESSFULLY;
import static com.example.fc_users_service.util.data.UserRequestData.getInvalidUserRequest;
import static com.example.fc_users_service.util.data.UserRequestData.getValidUserRequest;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
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
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
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

  @Test
  public void landlordExists_ReturnsTrue() throws Exception {
    Long landlordId = 1L;

    when(userApplicationService.landlordExists(landlordId)).thenReturn(true);

    mockMvc
        .perform(
            MockMvcRequestBuilders.get(
                    USER_BASE_PATH + LANDLORD_BASE_PATH + EXISTS_PATH + "/" + landlordId)
                .with(
                    SecurityMockMvcRequestPostProcessors.user("admin")
                        .authorities(new SimpleGrantedAuthority("admin"))))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data").value(true))
        .andExpect(jsonPath("$.error").doesNotExist());
  }

  @Test
  public void landlordExists_ReturnsFalse() throws Exception {
    Long landlordId = 2L;

    when(userApplicationService.landlordExists(landlordId)).thenReturn(false);

    mockMvc
        .perform(
            MockMvcRequestBuilders.get(
                    USER_BASE_PATH + LANDLORD_BASE_PATH + EXISTS_PATH + "/" + landlordId)
                .with(
                    SecurityMockMvcRequestPostProcessors.user("admin")
                        .authorities(new SimpleGrantedAuthority("admin"))))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data").value(false))
        .andExpect(jsonPath("$.error").doesNotExist());
  }

  @Test
  public void doesLandlordBelongToEmail_ReturnsTrue() throws Exception {
    Long landlordId = 1L;

    when(userApplicationService.doesEmailMatchLandlordId(landlordId)).thenReturn(true);

    mockMvc
        .perform(
            MockMvcRequestBuilders.get(
                    USER_BASE_PATH + LANDLORD_BASE_PATH + "/" + landlordId + "/belongs")
                .with(
                    SecurityMockMvcRequestPostProcessors.user("landlordUser")
                        .authorities(new SimpleGrantedAuthority("landlord"))))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data").value(true))
        .andExpect(jsonPath("$.error").doesNotExist());
  }

  @Test
  public void doesLandlordBelongToEmail_ReturnsFalse() throws Exception {
    Long landlordId = 2L;

    when(userApplicationService.doesEmailMatchLandlordId(landlordId)).thenReturn(false);

    mockMvc
        .perform(
            MockMvcRequestBuilders.get(
                    USER_BASE_PATH + LANDLORD_BASE_PATH + "/" + landlordId + "/belongs")
                .with(
                    SecurityMockMvcRequestPostProcessors.user("landlordUser")
                        .authorities(new SimpleGrantedAuthority("landlord"))))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data").value(false))
        .andExpect(jsonPath("$.error").doesNotExist());
  }

  @Test
  public void createEmployee_Success() throws Exception {
    var userRequest = getValidUserRequest();

    String requestJson = objectMapper.writeValueAsString(userRequest);

    Mockito.doNothing()
        .when(userApplicationService)
        .createEmployee(any(UserRequest.class), anyLong());

    mockMvc
        .perform(
            MockMvcRequestBuilders.post(
                    USER_BASE_PATH + EMPLOYEE_BASE_PATH + RESTAURANT_BASE_PATH + "/1")
                .with(
                    SecurityMockMvcRequestPostProcessors.user("landlordUser")
                        .authorities(new SimpleGrantedAuthority("landlord")))
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.data").value(USER_CREATED_SUCCESSFULLY.getMessage()))
        .andExpect(jsonPath("$.error").doesNotExist());
  }

  @Test
  public void createEmployee_BadRequest() throws Exception {
    var userRequest = getInvalidUserRequest();

    String requestJson = objectMapper.writeValueAsString(userRequest);

    mockMvc
        .perform(
            MockMvcRequestBuilders.post(
                    USER_BASE_PATH + EMPLOYEE_BASE_PATH + RESTAURANT_BASE_PATH + "/1")
                .with(
                    SecurityMockMvcRequestPostProcessors.user("landlordUser")
                        .authorities(new SimpleGrantedAuthority("landlord")))
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void createClient_Success() throws Exception {
    var userRequest = getValidUserRequest();

    String requestJson = objectMapper.writeValueAsString(userRequest);

    Mockito.doNothing().when(userApplicationService).createClient(any(UserRequest.class));

    mockMvc
        .perform(
            MockMvcRequestBuilders.post(USER_BASE_PATH + CLIENT_BASE_PATH)
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.data").value(USER_CREATED_SUCCESSFULLY.getMessage()))
        .andExpect(jsonPath("$.error").doesNotExist());
  }

  @Test
  public void createClient_BadRequest() throws Exception {
    var userRequest = getInvalidUserRequest();

    String requestJson = objectMapper.writeValueAsString(userRequest);

    mockMvc
        .perform(
            MockMvcRequestBuilders.post(USER_BASE_PATH + CLIENT_BASE_PATH)
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void getUserPhone_Success() throws Exception {
    Long userId = 1L;
    String phone = "3001234567";

    Mockito.when(userApplicationService.getUserPhone(userId)).thenReturn(phone);

    mockMvc
        .perform(MockMvcRequestBuilders.get(USER_BASE_PATH + "/" + userId + PHONE_PATH))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data").value(phone))
        .andExpect(jsonPath("$.error").doesNotExist());
  }
}
