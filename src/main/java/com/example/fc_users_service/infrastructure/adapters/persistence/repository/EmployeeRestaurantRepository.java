package com.example.fc_users_service.infrastructure.adapters.persistence.repository;

import com.example.fc_users_service.infrastructure.adapters.persistence.entity.EmployeeRestaurantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRestaurantRepository
    extends JpaRepository<EmployeeRestaurantEntity, Long> {
  @Query(
      """
          SELECT er.restaurantId
          FROM EmployeeRestaurantEntity er
          WHERE er.employee.email = :email
      """)
  Long findRestaurantIdByEmail(String email);
}
