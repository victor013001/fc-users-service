package com.example.fc_users_service.application.service.hanlder;

import com.example.fc_users_service.application.dto.UserRequest;
import com.example.fc_users_service.application.mapper.UserMapper;
import com.example.fc_users_service.application.service.UserApplicationService;
import com.example.fc_users_service.domain.api.UserServicePort;
import com.example.fc_users_service.domain.enums.Roles;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserApplicationServiceHandler implements UserApplicationService {

  private final UserMapper userMapper;
  private final PasswordEncoder passwordEncoder;
  private final UserServicePort userService;

  @Override
  public void createLandlord(UserRequest userRequest) {
    String encodedPassword = passwordEncoder.encode(userRequest.password());
    userService.saveUser(
        userMapper.toModel(userRequest, encodedPassword), Roles.LANDLORD.getValue(), null);
  }

  @Override
  public Boolean landlordExists(Long landlordId) {
    return userService.userWithRoleExists(landlordId, Roles.LANDLORD.getValue());
  }

  @Override
  public Boolean doesEmailMatchLandlordId(Long landlordId) {
    return userService.doesEmailMatchRoleId(
        landlordId, getCurrentUserEmail(), Roles.LANDLORD.getValue());
  }

  @Override
  public void createEmployee(UserRequest userRequest, Long restaurantId) {
    String encodedPassword = passwordEncoder.encode(userRequest.password());
    userService.saveUser(
        userMapper.toModel(userRequest, encodedPassword), Roles.EMPLOYEE.getValue(), restaurantId);
  }

  @Override
  public void createClient(UserRequest userRequest) {
    String encodedPassword = passwordEncoder.encode(userRequest.password());
    userService.saveUser(
        userMapper.toModel(userRequest, encodedPassword), Roles.CLIENT.getValue(), null);
  }

  @Override
  public String getUserPhone(Long userId) {
    return userService.getUserPhone(userId);
  }

  @Override
  public Long getUserId() {
    return userService.getUserId(getCurrentUserEmail());
  }

  @Override
  public Long getEmployeeRestaurant() {
    return userService.getEmployeeRestaurant(getCurrentUserEmail());
  }

  @Override
  public Long getUserEmail(Long userId) {
    return userService.getUserEmail(userId);
  }

  private String getCurrentUserEmail() {
    return SecurityContextHolder.getContext().getAuthentication().getName();
  }
}
