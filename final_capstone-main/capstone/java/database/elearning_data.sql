BEGIN TRANSACTION;

INSERT INTO users (username, firstname, lastname, password_hash, role) VALUES ('user', 'Sally', 'User', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_USER');
INSERT INTO users (username, firstname, lastname, password_hash, role) VALUES ('admin', 'Theresa', 'Admin', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_ADMIN');

INSERT INTO users (username, firstname, lastname, password_hash, role) VALUES ('user1@test.com', 'Studious', 'Maxiumus', '$2a$10$0k0K2koW5bDX0sIBLusVVuy/OEf0bPmbSgMs0sIcm5uuCW.oCzd1C','ROLE_USER'); --user1@test.com/user1
INSERT INTO users (username, firstname, lastname, password_hash, role) VALUES ('user2@test.com', 'Ferris', 'Beuller', '$2a$10$0k0K2koW5bDX0sIBLusVVuy/OEf0bPmbSgMs0sIcm5uuCW.oCzd1C','ROLE_USER'); --user1/user1
INSERT INTO users (username, firstname, lastname, password_hash, role) VALUES ('teacher1@test.edu', 'John', 'Kimble', '$2a$10$fvi6xnxafiZQm5y0.OAULOhLRJSUxEUIP0/1VIbmavTm7rQyMilrO','ROLE_ADMIN'); --teacher1@test.edu/teacher1
INSERT INTO users (username, firstname, lastname, password_hash, role) VALUES ('teacher2@nowhere.edu', 'Nicole', 'Teacherly', '$2a$10$ncPxcUqCZHQpCgckZ32ubOKpG/doLvAoCGEKGHIY/WXcVwI4sY7em','ROLE_ADMIN'); -- teacher2/teacher2
INSERT INTO courses (teacher_id, name, description, difficulty) VALUES ((SELECT user_id FROM users WHERE username = 'teacher1@test.edu'), 'Intro to Things', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.', 'Moderate');
insert into courses (teacher_id, name, description, difficulty) values ((SELECT user_id FROM users WHERE username = 'teacher2@nowhere.edu'), 'Debt, The', 'Donec posuere metus vitae ipsum. Aliquam non mauris. Morbi non lectus. Aliquam sit amet diam in magna bibendum imperdiet.', 'Expert');
insert into courses (teacher_id, name, description, difficulty) values ((SELECT user_id FROM users WHERE username = 'teacher1@test.edu'), 'I Love You Too', 'Mauris ullamcorper purus sit amet nulla. Quisque arcu libero, rutrum ac, lobortis vel, dapibus at, diam. Nam tristique tortor eu pede.', 'Hard');
insert into courses (teacher_id, name, description, difficulty) values ((SELECT user_id FROM users WHERE username = 'teacher2@nowhere.edu'), 'Outrage (Autoreiji)', 'Vestibulum ac est lacinia nisi venenatis tristique. Fusce congue, diam id ornare imperdiet, sapien urna pretium nisl, ut volutpat sapien arcu sed augue. Aliquam erat volutpat.', 'Hard');
insert into courses (teacher_id, name, description, difficulty) values ((SELECT user_id FROM users WHERE username = 'teacher2@nowhere.edu'), 'Lie with Me', 'Maecenas tincidunt lacus at velit. Vivamus vel nulla eget eros elementum pellentesque. Quisque porta volutpat erat. Quisque erat eros, viverra eget, congue eget, semper rutrum, nulla.', 'Easy');
insert into courses (teacher_id, name, description, difficulty) values ((SELECT user_id FROM users WHERE username = 'teacher1@test.edu'), '''Til There Was You', 'Aenean sit amet justo. Morbi ut odio. Cras mi pede, malesuada in, imperdiet et, commodo vulputate, justo.', 'Moderate');
insert into courses (teacher_id, name, description, difficulty) values ((SELECT user_id FROM users WHERE username = 'teacher1@test.edu'), 'George Lopez: Tall, Dark & Chicano', 'Suspendisse accumsan tortor quis turpis. Sed ante.', 'Moderate');
insert into courses (teacher_id, name, description, difficulty) values ((SELECT user_id FROM users WHERE username = 'teacher2@nowhere.edu'), 'That''s Life!', 'Quisque porta volutpat erat. Quisque erat eros, viverra eget, congue eget, semper rutrum, nulla. Nunc purus.', 'Expert');
insert into courses (teacher_id, name, description, difficulty) values ((SELECT user_id FROM users WHERE username = 'teacher1@test.edu'), 'The Sea Hawk', 'Vivamus in felis eu sapien cursus vestibulum. Proin eu mi. Nulla ac enim. In tempor, turpis nec euismod scelerisque, quam turpis adipiscing lorem, vitae mattis nibh ligula nec sem.', 'Hard');
insert into courses (teacher_id, name, description, difficulty) values ((SELECT user_id FROM users WHERE username = 'teacher1@test.edu'), 'Nancy Drew: Detective', 'Phasellus in felis. Donec semper sapien a libero. Nam dui.', 'Beginner');
insert into courses (teacher_id, name, description, difficulty) values ((SELECT user_id FROM users WHERE username = 'teacher1@test.edu'), 'Valley of Decision, The', 'Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Proin risus. Praesent lectus.', 'Easy');

INSERT INTO student_courses (student_id, course_id) VALUES ((SELECT user_id FROM users WHERE username = 'user1@test.com'), 4);
INSERT INTO student_courses (student_id, course_id) VALUES ((SELECT user_id FROM users WHERE username = 'user1@test.com'), 1);
INSERT INTO student_courses (student_id, course_id) VALUES ((SELECT user_id FROM users WHERE username = 'user2@test.com'), 1);
-- Listing student courses:
--SELECT * FROM courses c JOIN student_courses sc on sc.course_id = c.course_id JOIN users u ON u.user_id = sc.student_id WHERE user_id = (SELECT user_id FROM users WHERE username = 'user1@test.com');
-- Listing teacher's courses:
-- SELECT * FROM courses WHERE teacher_id = (SELECT user_id FROM users WHERE username='teacher1@test.edu');

INSERT INTO lessons (course_id, title, content, resources, due_date, instructions, has_assignment) VALUES (1, 'Day One Lesson', '<h1>Lesson Heading</h1><p>First paragraph</p><ul><li>list item one</li><li>list item two</li></ul>', '<a href="https://www.wikipedia.org">Wikipedia</a>', '06-05-2023', 'Create an ecommerce app!', true);
INSERT INTO lessons (course_id, title, content, resources, due_date, instructions, has_assignment) VALUES (1, 'Day Two Lesson', '<h1>Second Lesson Heading</h1><p>First paragraph</p><ul><li>list item one</li><li>list item two</li></ul>', '<a href="https://www.wikipedia.org">Wikipedia</a>', '06-06-2023', 'Create an ecommerce app!', true);
INSERT INTO lessons (course_id, title, content, resources, due_date, instructions, has_assignment) VALUES (4, 'There is but one lesson', '<h1>Lesson: Lesson</h1><p>First paragraph</p><ul><li>list item one</li><li>list item two</li></ul>', '<a href="https://www.wikipedia.org">Wikipedia</a>', '06-06-2023', 'Create an ecommerce app!', true);

--INSERT INTO


COMMIT TRANSACTION;
