INSERT INTO Role (roleType) VALUES ('ADMIN');
INSERT INTO Role (roleType) VALUES ('USER');

INSERT INTO User (username, password) VALUES ('test', 'password');
INSERT INTO User (username, password) VALUES ('johndoe', 'secret');

INSERT INTO user_roles (user_id, role_id) VALUES (1, 1); -- Assign ADMIN to 'test'
INSERT INTO user_roles (user_id, role_id) VALUES (1, 2); -- Assign USER to 'test'
INSERT INTO user_roles (user_id, role_id) VALUES (2, 2); -- Assign USER to 'johndoe';
