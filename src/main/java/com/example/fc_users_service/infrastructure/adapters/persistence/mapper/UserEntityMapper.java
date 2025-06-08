package com.example.fc_users_service.infrastructure.adapters.persistence.mapper;

import com.example.fc_users_service.domain.model.User;
import com.example.fc_users_service.infrastructure.adapters.persistence.entity.RoleEntity;
import com.example.fc_users_service.infrastructure.adapters.persistence.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserEntityMapper {
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "role", source = "role")
  UserEntity toEntity(User user, RoleEntity role);
}
