INSERT INTO users (username, password, enabled) VALUES
('user1', 'password1', true),
('user2', 'password2', true);

INSERT INTO authorities (username, authority) VALUES
('user1', 'ROLE_USER'),
('user2', 'ROLE_USER');
