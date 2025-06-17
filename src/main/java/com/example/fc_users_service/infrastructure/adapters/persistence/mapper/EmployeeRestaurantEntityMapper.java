package com.example.fc_users_service.infrastructure.adapters.persistence.mapper;

import com.example.fc_users_service.infrastructure.adapters.persistence.entity.EmployeeRestaurantEntity;
import com.example.fc_users_service.infrastructure.adapters.persistence.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface EmployeeRestaurantEntityMapper {
  @Mapping(target = "id", ignore = true)
  EmployeeRestaurantEntity toEntity(UserEntity employee, Long restaurantId);
}
