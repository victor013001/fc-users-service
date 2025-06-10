package com.example.fc_users_service.infrastructure.adapters.persistence.repository;

import com.example.fc_users_service.infrastructure.adapters.persistence.entity.UserEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
  boolean existsByEmail(String email);

  boolean existsByDocumentNumber(String documentNumber);

  Optional<UserEntity> findByEmail(String email);

  Boolean existsByIdAndRole_Name(Long id, String roleName);

  boolean existsByIdAndEmail(Long userId, String userEmail);
}
