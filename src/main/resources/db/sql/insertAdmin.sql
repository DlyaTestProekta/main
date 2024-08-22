INSERT INTO users (fk_role_id, login, password)
VALUES(1, 'admin', '$2a$10$2wggeB6Xl0tnHnMMOdd4vuANO/xcxd/h2iAZJCev48kgZ/gOeZMk.')
ON CONFLICT (login) DO NOTHING;

WITH user_id_query AS (
  SELECT user_id FROM users WHERE login = 'admin'
)
INSERT INTO refresh_tokens (refresh_token, fk_user_id)
SELECT '', user_id FROM user_id_query
ON CONFLICT (fk_user_id) DO NOTHING;