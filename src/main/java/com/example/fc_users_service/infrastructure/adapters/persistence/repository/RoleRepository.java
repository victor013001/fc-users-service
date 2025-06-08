package com.example.fc_users_service.infrastructure.adapters.persistence.repository;

import com.example.fc_users_service.infrastructure.adapters.persistence.entity.RoleEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
  Optional<RoleEntity> findByName(String name);
}
