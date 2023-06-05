package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.Lesson;
import com.techelevator.model.LessonDTO;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcLessonDao implements LessonDao {
    private final JdbcTemplate jdbcTemplate;

    public JdbcLessonDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

//    @Override
//    public Lesson getLessonByIdAndCourseId(int lessonId, int courseId) {
//        // need to check that user has access to this course?
//        // using user_id to check:
//        // SELECT * FROM lessons l JOIN courses c ON c.course_id = l.course_id JOIN student_courses sc ON sc
//        // .course_id = l.course_id  WHERE (sc.student_id = 4 OR c.teacher_id = 4) AND l.lesson_id = 1;
//        // TODO thinking that maybe it should be lesson id and module id?
//
//        Lesson lesson = null;
//        String sql = "SELECT * FROM lessons l JOIN modules m ON m.module_id = l.module_id WHERE l.lesson_id = ? AND
//        m" +
//                ".course_id = ?;";
//
//        try {
//            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, lessonId, courseId);
//
//            if (results.next()) {
//                lesson = mapRowToLesson(results);
//            }
//        } catch (CannotGetJdbcConnectionException e) {
//            throw new DaoException("Unable to connect to server or database", e);
//        } catch (BadSqlGrammarException e) {
//            throw new DaoException("SQL syntax error", e);
//        }
//
//        return lesson;
//    }

    @Override
    public Lesson getLessonById(int lessonId) {
        Lesson lesson = null;
        String sql = "SELECT * FROM lessons WHERE lesson_id = ?;";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, lessonId);

            if (results.next()) {
                lesson = mapRowToLesson(results);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (BadSqlGrammarException e) {
            throw new DaoException("SQL syntax error", e);
        }

        return lesson;
    }

//    @Override
//    public List<Lesson> getLessonsByCourseId(int courseId) {
//        // check that user has access to this course
//        // need to add course_id to both WHERE sections below:
//        // SELECT lesson_id FROM lessons l JOIN courses c ON c.course_id = l.course_id JOIN student_courses sc ON sc
//        // .course_id = l.course_id WHERE sc.student_id = 4 UNION SELECT lesson_id FROM lessons l JOIN courses c ON c
//        // .course_id = l.course_id WHERE c.teacher_id = 4;
//        List<Lesson> lessons = new ArrayList<>();
//        String sql = "SELECT * FROM lessons l JOIN modules m ON l.module_id = m.module_id WHERE m.course_id = ?;";
//        try {
//            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, courseId);
//            while (results.next()) {
//                lessons.add(mapRowToLesson(results));
//            }
//        } catch (CannotGetJdbcConnectionException e) {
//            throw new DaoException("Unable to connect to server or database", e);
//        } catch (BadSqlGrammarException e) {
//            throw new DaoException("SQL syntax error", e);
//        } catch (DataIntegrityViolationException e) {
//            throw new DaoException("Data integrity violation", e);
//        }
//        return lessons;
//    }


    @Override
    public List<Lesson> getLessonsByCourseAndModule(int moduleId, int courseId) {
        List<Lesson> lessons = new ArrayList<>();
        String sql = "SELECT * FROM lessons l JOIN modules m ON m.module_id = l.module_id WHERE l.module_id = ? AND m" +
                ".course_id = ?;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, moduleId, courseId);
            while (results.next()) {
                lessons.add(mapRowToLesson(results));
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (BadSqlGrammarException e) {
            throw new DaoException("SQL syntax error", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }

        return lessons;
    }

    @Override
    public Lesson updateLesson(Lesson lesson) {
        Lesson updatedLesson = null;
        String sql = "UPDATE lessons SET title = ?, content = ?, resources = ?, due_date = ?, " +
                "instructions = ? " +
                "WHERE lesson_id = ?";

        try {
            int numberOfRows = jdbcTemplate.update(sql, lesson.getTitle(), lesson.getContent(),
                    lesson.getResources(), lesson.getDueDate(), lesson.getInstructions(), lesson.getId());

            if (numberOfRows == 0) {
                throw new DaoException("Zero rows affected, expected at least one");
            } else {
                updatedLesson = getLessonById(lesson.getId());
            }

        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (BadSqlGrammarException e) {
            throw new DaoException("SQL syntax error", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }

        return updatedLesson;
    }


    @Override
    public void deleteLesson(int lessonId) {
        String sql = "DELETE FROM lessons WHERE lesson_id = ?";

        try {
            jdbcTemplate.update(sql, lessonId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (BadSqlGrammarException e) {
            throw new DaoException("SQL syntax error", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
    }

    @Override
    public Lesson createLesson(LessonDTO lesson) {
        Lesson newLesson = null;
        String sql = "INSERT INTO lessons (module_id, title, content, resources, due_date, instructions, " +
                "has_assignment) VALUES (?, ?, ?, ?, ?, ?, ?) RETURNING lesson_id;";
        try {
            int lessonId = jdbcTemplate.queryForObject(sql, int.class, lesson.getModuleId(), lesson.getTitle(),
                    lesson.getContent(), lesson.getResources(), lesson.getDueDate(), lesson.getInstructions(),
                    lesson.isHas_assignment());
            newLesson = getLessonById(lessonId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (BadSqlGrammarException e) {
            throw new DaoException("SQL syntax error", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return newLesson;
    }

    private Lesson mapRowToLesson(SqlRowSet rs) {
        Lesson lesson = new Lesson();
        lesson.setId(rs.getInt("lesson_id"));
        lesson.setModuleId(rs.getInt("module_id"));
        lesson.setTitle(rs.getString("title"));
        lesson.setContent(rs.getString("content"));
        lesson.setResources(rs.getString("resources"));
        lesson.setHas_assignment(rs.getBoolean("has_assignment"));
        if (rs.getDate("due_date") != null) {
            lesson.setDueDate(rs.getDate("due_date").toLocalDate());
        }
        lesson.setInstructions(rs.getString("instructions"));

        return lesson;
    }


}
