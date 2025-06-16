CREATE TABLE employee_restaurant (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  employee_id BIGINT NOT NULL,
  restaurant_id BIGINT NOT NULL,
  FOREIGN KEY (employee_id) REFERENCES user(id)
);

-- REVERT
-- DROP TABLE employee_restaurant;
-- DELETE FROM flyway_schema_history WHERE version = '1.2';
