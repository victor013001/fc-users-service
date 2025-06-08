package com.example.fc_users_service.application.mapper;

import com.example.fc_users_service.domain.model.User;
import com.example.fc_users_service.infrastructure.entrypoint.dto.UserRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
  @Mapping(target = "password", source = "password")
  User toModel(UserRequest userRequest, String password);
}
