BEGIN TRANSACTION;

DROP TABLE IF EXISTS courses;
DROP TABLE IF EXISTS student_courses;
DROP TABLE IF EXISTS lessons;
DROP TABLE IF EXISTS submissions;
DROP TABLE IF EXISTS notifications;

CREATE TABLE courses (
	course_id SERIAL,
    teacher_id INT NOT NULL,
    name VARCHAR(50),
    description TEXT,
    difficulty VARCHAR(20) DEFAULT '',
    cost NUMERIC(7,2) DEFAULT 0.0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	CONSTRAINT PK_course PRIMARY KEY(course_id),
	CONSTRAINT FK_teacher FOREIGN KEY(teacher_id) REFERENCES users(user_id)
);

CREATE TABLE student_courses (
    student_id int NOT NULL,
    course_id int NOT NULL,
    CONSTRAINT PK_student_course PRIMARY KEY(student_id, course_id),
    CONSTRAINT FK_student_course_student FOREIGN KEY(student_id) REFERENCES users(user_id),
    CONSTRAINT FK_student_course_course FOREIGN KEY(course_id) REFERENCES courses(course_id)
);

CREATE TABLE modules (
    module_id SERIAL,
    course_id INT NOT NULL,
    name VARCHAR(20),
    description TEXT,
    CONSTRAINT PK_module PRIMARY KEY(module_id),
    CONSTRAINT FK_module_course FOREIGN KEY(course_id) REFERENCES courses(course_id)
);

CREATE TABLE lessons (
    lesson_id SERIAL,
    module_id INT NOT NULL,
    title VARCHAR(60) NOT NULL,
    content TEXT NOT NULL,
    resources TEXT,
    due_date TIMESTAMP,
    instructions TEXT,
--    add assignment value? (out of x points?)
    has_assignment BOOLEAN DEFAULT false,
    CONSTRAINT PK_lesson PRIMARY KEY(lesson_id),
    CONSTRAINT FK_lesson_module FOREIGN KEY(module_id) REFERENCES modules(module_id)
);

CREATE TABLE submissions (
    submission_id SERIAL,
    content TEXT NOT NULL,
    lesson_id INT NOT NULL,
    student_id INT NOT NULL,
    grade INT,
    submitted_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT PK_submission PRIMARY KEY(submission_id),
    CONSTRAINT FK_submission_lesson FOREIGN KEY(lesson_id) REFERENCES lessons(lesson_id),
    CONSTRAINT FK_submission_student FOREIGN KEY(student_id) REFERENCES users(user_id)
);

CREATE TABLE notifications (
    notification_id SERIAL,
    user_id INT NOT NULL,
    content TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    viewed BOOLEAN DEFAULT false
);


COMMIT TRANSACTION;