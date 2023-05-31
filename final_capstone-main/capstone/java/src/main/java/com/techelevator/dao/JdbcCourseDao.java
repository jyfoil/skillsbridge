package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.Course;
import com.techelevator.model.CourseDTO;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

@Component
public class JdbcCourseDao implements CourseDao {

    private JdbcTemplate jdbcTemplate;

    public JdbcCourseDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Course createCourse(Course course) {
        Course newCourse = null;

        String sql = "INSERT INTO courses (teacher_id, description, difficulty, cost) " +
                "VALUES (?, ?, ?, ?) RETURNING course_id";

        try {
            int newCourseId = jdbcTemplate.queryForObject(sql, int.class, course.getTeacherId(),
                    course.getDescription(), course.getDifficultyLevel(), course.getCost());
            newCourse = getCourse(newCourseId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (BadSqlGrammarException e) {
            throw new DaoException("SQL syntax error", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }

        return newCourse;
    }

    @Override
    public Course getCourse(int id) {
        Course course = null;
        String sql = "SELECT course_id, teacher_id, description, difficulty, cost " +
                "FROM course " +
                "WHERE course_id = ?";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);

            if (results.next()) {
                course = mapRowToCourse(results);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (BadSqlGrammarException e) {
            throw new DaoException("SQL syntax error", e);
        }

        return course;
    }

    public Course mapRowToCourse(SqlRowSet rowSet) {
        Course course = null;
        course.setCourseId(rowSet.getInt("course_id"));
        course.setTeacherId(rowSet.getInt("teacher_id"));
        course.setDescription(rowSet.getString("description"));
        course.setDifficultyLevel(rowSet.getString("difficulty"));
        course.setCost(rowSet.getBigDecimal("cost"));
        return course;
    }

    public Course mapCourseDtoToCourse(CourseDTO courseDTO, int id) {
        Course course = new Course();
        course.setTeacherId(id);
        course.setName(courseDTO.getName());
        course.setDescription(courseDTO.getDescription());
        course.setDifficultyLevel(courseDTO.getDifficulty());
        course.setCost(courseDTO.getCost());
        return course;
    }
}
