package com.techelevator.dao;

import com.techelevator.model.Lesson;
import com.techelevator.model.LessonNotFoundException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcLessonDao implements LessonDao{
    private final JdbcTemplate jdbcTemplate;

    public JdbcLessonDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Lesson getLessonById(int lessonId, int courseId) {
        // need to check that user has access to this course?
        // using user_id to check:
        // SELECT * FROM lessons l JOIN courses c ON c.course_id = l.course_id JOIN student_courses sc ON sc.course_id = l.course_id  WHERE (sc.student_id = 4 OR c.teacher_id = 4) AND l.lesson_id = 1;
        String sql = "SELECT * FROM lessons WHERE lesson_id = ? AND course_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, lessonId, courseId);
        if (results.next()) {
            return mapRowToLesson(results);
        } else {
            throw new LessonNotFoundException();
        }
    }

    @Override
    public List<Lesson> getLessonsByCourseId(int courseId) {
        // check that user has access to this course
        // need to add course_id to both WHERE sections below:
        // SELECT lesson_id FROM lessons l JOIN courses c ON c.course_id = l.course_id JOIN student_courses sc ON sc.course_id = l.course_id WHERE sc.student_id = 4 UNION SELECT lesson_id FROM lessons l JOIN courses c ON c.course_id = l.course_id WHERE c.teacher_id = 4;
        List<Lesson> lessons = new ArrayList<>();
        String sql = "SELECT * FROM lessons WHERE course_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, courseId);
        while (results.next()) {
            lessons.add(mapRowToLesson(results));
        }
        return lessons;
    }

    private Lesson mapRowToLesson(SqlRowSet rs) {
        Lesson lesson = new Lesson();
        lesson.setId(rs.getInt("lesson_id"));
        lesson.setCourseId(rs.getInt("course_id"));
        lesson.setTitle(rs.getString("title"));
        lesson.setContent(rs.getString("content"));
        lesson.setResources(rs.getString("resources"));
        lesson.setDue_date(rs.getDate("due_date").toLocalDate());
        lesson.setInstructions(rs.getString("instructions"));
        lesson.setHas_assignment(rs.getBoolean("has_assignment"));
        return lesson;
    }
}
