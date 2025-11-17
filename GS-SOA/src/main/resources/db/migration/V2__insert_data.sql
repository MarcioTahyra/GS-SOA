INSERT INTO users (full_name, email, password)
VALUES ('Admin Demo', 'admin@example.com', 'REPLACE_WITH_BCRYPT_HASH');

SET @admin_id = (SELECT id FROM users WHERE email = 'admin@example.com' LIMIT 1);

INSERT INTO user_roles (user_id, role)
VALUES (@admin_id, 'ROLE_ADMIN'),
       (@admin_id, 'ROLE_USER');

INSERT INTO wellbeing_entries (user_id, created_at, mood, sleep_hours, stress_level, notes)
VALUES
  (@admin_id, NOW(), 'GOOD', 7.0, 3, 'Primeira entrada de exemplo'),
  (@admin_id, NOW() - INTERVAL 1 DAY, 'NEUTRAL', 6.5, 5, 'Dia difícil, mas segurei firme'),
  (@admin_id, NOW() - INTERVAL 2 DAY, 'VERY_GOOD', 8.0, 1, 'Ótimo dia, sensação positiva');