INSERT INTO user (first_name, last_name, document_number, phone_number, birth_date, email, password, role_id) VALUES
('Admin', 'User', '00000001', '1234567890123', '1990-01-01', 'admin@example.com', '$2a$10$9L6zdfcWYLhxDXB5VA60FuKtEYsq9J9ycBjqE2RUvoByTnJdtGeYu', (SELECT id FROM role WHERE name = 'admin')),
('Landlord', 'User', '00000002', '1234567890123', '1990-01-01', 'landlord@example.com', '$2a$10$9L6zdfcWYLhxDXB5VA60FuKtEYsq9J9ycBjqE2RUvoByTnJdtGeYu', (SELECT id FROM role WHERE name = 'landlord')),
('Employee', 'User', '00000003', '1234567890123', '1990-01-01', 'employee@example.com', '$2a$10$9L6zdfcWYLhxDXB5VA60FuKtEYsq9J9ycBjqE2RUvoByTnJdtGeYu', (SELECT id FROM role WHERE name = 'employee')),
('Client', 'User', '00000004', '1234567890123', '1990-01-01', 'client@example.com', '$2a$10$9L6zdfcWYLhxDXB5VA60FuKtEYsq9J9ycBjqE2RUvoByTnJdtGeYu', (SELECT id FROM role WHERE name = 'client'));

-- REVERT
-- DELETE FROM user WHERE email IN ('admin@example.com', 'landlord@example.com', 'employee@example.com', 'client@example.com');
-- DELETE FROM flyway_schema_history WHERE version = '1.0';
