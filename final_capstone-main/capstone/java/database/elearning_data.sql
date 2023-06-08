BEGIN TRANSACTION;

--INSERT INTO users (username, firstname, lastname, password_hash, role) VALUES ('user', 'Sally', 'User', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_USER');
--INSERT INTO users (username, firstname, lastname, password_hash, role) VALUES ('admin', 'Theresa', 'Admin', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_ADMIN');

INSERT INTO users (username, firstname, lastname, password_hash, role) VALUES ('mstudious@gmail.com', 'Studious', 'Maximus', '$2a$10$0k0K2koW5bDX0sIBLusVVuy/OEf0bPmbSgMs0sIcm5uuCW.oCzd1C','ROLE_USER'); --user1@test.com/user1
INSERT INTO users (username, firstname, lastname, password_hash, role) VALUES ('fbeuller@gmail.com', 'Ferris', 'Beuller', '$2a$10$0k0K2koW5bDX0sIBLusVVuy/OEf0bPmbSgMs0sIcm5uuCW.oCzd1C','ROLE_USER'); --user1/user1
INSERT INTO users (username, firstname, lastname, password_hash, role) VALUES ('jkimble@g-email.com', 'John', 'Kimble', '$2a$10$fvi6xnxafiZQm5y0.OAULOhLRJSUxEUIP0/1VIbmavTm7rQyMilrO','ROLE_ADMIN'); --teacher1@test.edu/teacher1
INSERT INTO users (username, firstname, lastname, password_hash, role) VALUES ('teacher2@nowhere.edu', 'Nicole', 'Teacherly', '$2a$10$ncPxcUqCZHQpCgckZ32ubOKpG/doLvAoCGEKGHIY/WXcVwI4sY7em','ROLE_ADMIN'); -- teacher2/teacher2

INSERT INTO users (username, firstname, lastname, password_hash, role) VALUES ('dochterlony0@skyrock.com', 'Drud', 'Ochterlony', '$2a$10$5G87IPrht36eEREueKh2tOQkVHc/2ZUY/Pk0xqt840KE.Hkqo18W.', 'ROLE_USER'); -- passwords are first part of email (before @)
INSERT INTO users (username, firstname, lastname, password_hash, role) VALUES ('djerams1@t.co', 'Danita', 'Jerams', '$2a$10$.qovoI3Ws/eMAikOl.H3Zu0EoTvqNNrxeETrO93sz/rcItkTn9Xsy', 'ROLE_USER');
INSERT INTO users (username, firstname, lastname, password_hash, role) VALUES ('dcommins@gmail.com', 'Daniel', 'Commins', '$2a$10$eT5WR5hypYJ/SgrXAV3qpuuvWbftGrUDP7kfmoYqYfGLLARadv9m2', 'ROLE_USER');
INSERT INTO users (username, firstname, lastname, password_hash, role) VALUES ('sbreen@gmail.com', 'Sarah', 'Breen', '$2a$10$1vWEp7WuJ30kmEH0O8VkQOF1tTCg8bXo0kMARA0lE/PUdEbkBX1E2', 'ROLE_USER');
INSERT INTO users (username, firstname, lastname, password_hash, role) VALUES ('cquintana@gmail.com', 'Claribel', 'Quintana', '$2a$10$jaTf8D0aIzGpn8qbvVppeeY4iII11Tl1.7heD0ndJR.WcIpbXPz8m', 'ROLE_USER');
INSERT INTO users (username, firstname, lastname, password_hash, role) VALUES ('mgreen@gmail.com', 'Margaret', 'Green', '$2a$10$7PNvFEoT0EFxLA1v/acu2eTDn6JFtNS68S./l5YkkdUIpus3BNJLK', 'ROLE_USER');


INSERT INTO courses (teacher_id, name, description, difficulty) VALUES ((SELECT user_id FROM users WHERE username = 'jkimble@g-email.com'), 'Development Tools', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.', 'Moderate');
insert into courses (teacher_id, name, description, difficulty) values ((SELECT user_id FROM users WHERE username = 'jkimble@g-email.com'), 'Front End Development for Dummies', 'Donec posuere metus vitae ipsum. Aliquam non mauris. Morbi non lectus. Aliquam sit amet diam in magna bibendum imperdiet.', 'Expert');
insert into courses (teacher_id, name, description, difficulty) values ((SELECT user_id FROM users WHERE username = 'jkimble@g-email.com'), 'Java Masterclass', 'Mauris ullamcorper purus sit amet nulla. Quisque arcu libero, rutrum ac, lobortis vel, dapibus at, diam. Nam tristique tortor eu pede.', 'Hard');

INSERT INTO student_courses (student_id, course_id) VALUES ((SELECT user_id FROM users WHERE username = 'mstudious@gmail.com'), 1);
INSERT INTO student_courses (student_id, course_id) VALUES ((SELECT user_id FROM users WHERE username = 'mstudious@gmail.com'), 2);
INSERT INTO student_courses (student_id, course_id) VALUES ((SELECT user_id FROM users WHERE username = 'mstudious@gmail.com'), 3);
INSERT INTO student_courses (student_id, course_id) VALUES ((SELECT user_id FROM users WHERE username = 'fbeuller@gmail.com'), 1);
INSERT INTO student_courses (student_id, course_id) VALUES ((SELECT user_id FROM users WHERE username = 'fbeuller@gmail.com'), 2);
INSERT INTO student_courses (student_id, course_id) VALUES ((SELECT user_id FROM users WHERE username = 'sbreen@gmail.com'), 1);
INSERT INTO student_courses (student_id, course_id) VALUES ((SELECT user_id FROM users WHERE username = 'dcommins@gmail.com'), 1);

-- Listing student courses:
--SELECT * FROM courses c JOIN student_courses sc on sc.course_id = c.course_id JOIN users u ON u.user_id = sc.student_id WHERE user_id = (SELECT user_id FROM users WHERE username = 'user1@test.com');
-- Listing teacher's courses:
-- SELECT * FROM courses WHERE teacher_id = (SELECT user_id FROM users WHERE username='teacher1@test.edu');

INSERT INTO modules (course_id, name, description) VALUES (1, 'The Development Environment', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt.');
INSERT INTO modules (course_id, name, description) VALUES (1, 'Chrome Developer Tools', 'Et malesuada fames ac turpis egestas sed tempus. Aliquam id diam maecenas ultricies mi eget. ');
INSERT INTO modules (course_id, name, description) VALUES (1, 'intelliJ IDE', 'Bibendum neque egestas congue quisque egestas. Integer enim neque volutpat ac tincidunt vitae semper quis.');
INSERT INTO modules (course_id, name, description) VALUES (2, 'Intro to HTML', 'Ullamcorper eget nulla facilisi etiam dignissim. Lacus suspendisse faucibus interdum posuere.');
INSERT INTO modules (course_id, name, description) VALUES (2, 'Intro to CSS', 'Commodo elit at imperdiet dui accumsan sit amet nulla facilisi. Adipiscing at in tellus intege.');

INSERT INTO lessons (module_id, title, content, resources, due_date, instructions, has_assignment) VALUES (1, 'intelliJ: it does half the work for you', '<h2>Lesson Heading</h2><p>First paragraph</p><ul><li>list item one</li><li>list item two</li></ul>', 'Wikipedia.org', '06-05-2023', 'Do this, this, and this.', true);
INSERT INTO lessons (module_id, title, content, resources, due_date, instructions, has_assignment) VALUES (1, 'Day Two Lesson', '<h2>Second Lesson Heading</h2><p>First paragraph</p><ul><li>list item one</li><li>list item two</li></ul>', 'geeksforgeeks.com', '06-06-2023', 'Create an ecommerce app!', true);
INSERT INTO lessons (module_id, title, content, resources, has_assignment) VALUES (1, 'Day Three Lesson', '<h2>Lesson: Lesson</h2><p>First paragraph</p><ul><li>list item one</li><li>list item two</li></ul>', 'stackoverflow.com', false);
INSERT INTO lessons (module_id, title, content, resources, due_date, instructions, has_assignment) VALUES (1, 'Day Four Lesson', '<h2>Lesson Heading</h2><p>First paragraph</p><ul><li>list item one</li><li>list item two</li></ul>', '', '06-10-2023', 'Create an ecommerce app!', true);

INSERT INTO lessons (module_id, title, content, resources, due_date, instructions, has_assignment) VALUES (2, 'First Lesson', '<h2>Lesson Heading</h2><p>First paragraph</p><ul><li>list item one</li><li>list item two</li></ul>', 'Wikipedia.org', '06-05-2023', 'Do this, this, and this.', true);
INSERT INTO lessons (module_id, title, content, resources, due_date, instructions, has_assignment) VALUES (2, 'Second Lesson', '<h2>Second Lesson Heading</h2><p>First paragraph</p><ul><li>list item one</li><li>list item two</li></ul>', 'geeksforgeeks.com', '06-06-2023', 'Create an ecommerce app!', true);
INSERT INTO lessons (module_id, title, content, resources, has_assignment) VALUES (2, 'Third Lesson', '<h1>Lesson: Lesson</h1><p>First paragraph</p><ul><li>list item one</li><li>list item two</li></ul>', 'stackoverflow.com', false);

INSERT INTO lessons (module_id, title, content, resources, due_date, instructions, has_assignment) VALUES (3, 'intelliJ: The Basics', '<h2>Lesson Heading</h2><p>First paragraph</p><ul><li>list item one</li><li>list item two</li></ul>', 'Wikipedia.org', '06-05-2023', 'Do this, this, and this.', true);
INSERT INTO lessons (module_id, title, content, resources, due_date, instructions, has_assignment) VALUES (4, 'HTML: Use divs for everything', '<h2>Second Lesson Heading</h2><p>First paragraph</p><ul><li>list item one</li><li>list item two</li></ul>', 'geeksforgeeks.com', '06-06-2023', 'Create an ecommerce app!', true);
INSERT INTO lessons (module_id, title, content, resources, has_assignment) VALUES (5, 'CSS: The bane of your existence', '<h1>Lesson: Lesson</h1><p>First paragraph</p><ul><li>list item one</li><li>list item two</li></ul>', 'stackoverflow.com', false);

INSERT INTO submissions (content, lesson_id, grade, student_id, submitted_at) VALUES ('INSERT amazing work WHERE username = studiousMaximus', 1, 8, (SELECT user_id FROM users WHERE username = 'mstudious@gmail.com'), '2023-06-06 16:55:19.012961');
INSERT INTO submissions (content, lesson_id, student_id, submitted_at) VALUES ('My dog ate my laptop.', 2, (SELECT user_id FROM users WHERE username = 'mstudious@gmail.com'), '2023-06-07 16:55:19.012961');
INSERT INTO submissions (content, lesson_id, grade, student_id, submitted_at) VALUES ('Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt.', 1, 7, (SELECT user_id FROM users WHERE username = 'fbeuller@gmail.com'), '2023-06-07 16:55:19.012961');
INSERT INTO submissions (content, lesson_id, grade, student_id, submitted_at) VALUES ('Commodo elit at imperdiet dui accumsan sit amet nulla facilisi. Adipiscing at in tellus intege.', 2, 9, (SELECT user_id FROM users WHERE username = 'fbeuller@gmail.com'), '2023-06-08 16:55:19.012961');

COMMIT TRANSACTION;
