INSERT INTO users (id, email, password, first_name, last_name) VALUES (
    DEFAULT,
    'testuser@gmail.com',
    'password',
    'Test',
    'User'
), 
(
    DEFAULT,
    'q@q',
    '111111',
    'Test',
    'User'
);

INSERT INTO posts (id, text, image_url, author_id) VALUES(
    DEFAULT,
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.',
    '',
    1
), (
    DEFAULT,
    'The classic',
    'https://i.imgur.com/fhgzVEt.jpeg',
    1
);
