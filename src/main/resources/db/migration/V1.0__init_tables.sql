CREATE TABLE role (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(64) NOT NULL UNIQUE,
    description VARCHAR(256)
);

CREATE TABLE user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(64) NOT NULL,
    last_name VARCHAR(64) NOT NULL,
    document_number VARCHAR(32) NOT NULL UNIQUE,
    phone_number VARCHAR(14),
    birth_date DATE NOT NULL,
    email VARCHAR(128) NOT NULL UNIQUE,
    password VARCHAR(256) NOT NULL,
    role_id BIGINT NOT NULL,
    FOREIGN KEY (role_id) REFERENCES role(id)
);

INSERT INTO role (name, description) VALUES
('admin', 'System administrator with full access'),
('landlord', 'Property owner or manager'),
('employee', 'Internal system user or staff'),
('client', 'External user or customer');

-- REVERT
-- DROP TABLE IF EXISTS role;
-- DROP TABLE IF EXISTS user;
-- DELETE FROM role WHERE name IN ('admin', 'landlord', 'employee', 'client');
-- DELETE FROM flyway_schema_history WHERE version = '1.0';
