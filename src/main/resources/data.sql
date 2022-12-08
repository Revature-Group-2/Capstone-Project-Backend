INSERT INTO users (id, email, password, first_name, last_name, avatar_image_url) VALUES (
    DEFAULT,
    'testuser@gmail.com',
    'password',
    'Test',
    'User',
    'https://www.nicepng.com/png/full/128-1280406_view-user-icon-png-user-circle-icon-png.png'
), 
(
    DEFAULT,
    'q@q',
    '111111',
    'Test',
    'User',
    'https://www.nicepng.com/png/full/128-1280406_view-user-icon-png-user-circle-icon-png.png'
);

INSERT INTO profiles (id, owner_id) VALUES (DEFAULT,1 ), (DEFAULT, 2 );

INSERT INTO posts (id, text, image_url, vote_count, author_id) VALUES
(
    DEFAULT,
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.',
    '',
    0,
    1
), (
    DEFAULT,
    'The classic',
    'https://i.imgur.com/fhgzVEt.jpeg',
    0,
    1
);
