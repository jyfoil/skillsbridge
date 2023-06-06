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

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcCourseDao implements CourseDao {

    private JdbcTemplate jdbcTemplate;

    public JdbcCourseDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Course createCourse(Course course) {
        Course newCourse = null;

        String sql = "INSERT INTO courses (teacher_id, name, description, difficulty, cost) " +
                "VALUES (?, ?, ?, ?, ?) RETURNING course_id";

        try {
            int newCourseId = jdbcTemplate.queryForObject(sql, int.class, course.getTeacherId(), course.getName(),
                    course.getDescription(), course.getDifficultyLevel(), course.getCost());
            newCourse = getCourseByCourseId(newCourseId);
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
    public void deleteCourseByCourseId(int id) {
        // This will delete a specified course with the courseId
        String sql = "DELETE FROM courses WHERE course_id = ?";

        try {
            jdbcTemplate.update(sql, id);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (BadSqlGrammarException e) {
            throw new DaoException("SQL syntax error", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }

    }

    @Override
    public Course getCourseByCourseId(int courseId) {
        Course course = null;
        String sql = "SELECT course_id, teacher_id, name, description, difficulty, cost " +
                "FROM courses " +
                "WHERE course_id = ?";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, courseId);

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

    @Override
    public List<Course> getTeacherCoursesByTeacherId(int teacherId) {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT course_id, teacher_id, name, description, difficulty, cost " +
                "FROM courses " +
                "WHERE teacher_id = ?";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, teacherId);

            while (results.next()) {
                courses.add(mapRowToCourse(results));
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (BadSqlGrammarException e) {
            throw new DaoException("SQL syntax error", e);
        }

        return courses;
    }

    @Override
    public List<Course> getStudentCoursesByStudentId(int id) {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT courses.course_id, courses.teacher_id, courses.name, courses.description, courses.difficulty, courses.cost " +
                "FROM student_courses " +
                "JOIN courses ON student_courses.course_id = courses.course_id " +
                "WHERE student_courses.student_id = ?";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);

            while (results.next()) {
                courses.add(mapRowToCourse(results));
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (BadSqlGrammarException e) {
            throw new DaoException("SQL syntax error", e);
        }

        return courses;
    }

    @Override
    public String getCourseNameFromLessonId(int id) {
        String courseName = null;
        String sql = "SELECT c.name FROM lessons l " +
                "JOIN modules m ON l.module_id = m.module_id " +
                "JOIN courses c on m.course_id = c.course_id " +
                "WHERE l.lesson_id = ?";

        try {
            courseName = jdbcTemplate.queryForObject(sql, String.class, id);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (BadSqlGrammarException e) {
            throw new DaoException("SQL syntax error", e);
        }

        return courseName;
    }

    @Override
    public void addStudentToCourse(int studentId, int courseId) {
        String sql = "INSERT INTO student_courses VALUES (?, ?)";

        try {
            jdbcTemplate.update(sql, studentId, courseId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (BadSqlGrammarException e) {
            throw new DaoException("SQL syntax error", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
    }

    @Override
    public void deleteStudentFromCourse(int studentId, int courseId) {
        String sql = "DELETE FROM student_courses WHERE student_id = ? AND course_id = ?";

        try {
            jdbcTemplate.update(sql, studentId, courseId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (BadSqlGrammarException e) {
            throw new DaoException("SQL syntax error", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
    }

    @Override
    public void deleteStudentsFromCourse(int courseId) {
        String sql = "DELETE FROM student_courses WHERE course_id = ?";

        try {
            jdbcTemplate.update(sql, courseId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (BadSqlGrammarException e) {
            throw new DaoException("SQL syntax error", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
    }

    private Course mapRowToCourse(SqlRowSet rowSet) {
        Course course = new Course();
        course.setCourseId(rowSet.getInt("course_id"));
        course.setTeacherId(rowSet.getInt("teacher_id"));
        course.setName(rowSet.getString("name"));
        course.setDescription(rowSet.getString("description"));
        course.setDifficultyLevel(rowSet.getString("difficulty"));
        course.setCost(rowSet.getBigDecimal("cost"));
        return course;
    }

    @Override
    public CourseDTO mapCourseToCourseDTO(Course course) {
        CourseDTO courseDto = new CourseDTO();
        courseDto.setCourseId(course.getCourseId());
        courseDto.setName(course.getName());
        courseDto.setDescription(course.getDescription());
        courseDto.setDifficulty(course.getDifficultyLevel());
        courseDto.setCost(course.getCost());
        return courseDto;
    }

    @Override
    public Course mapCourseDtoToCourse(CourseDTO courseDTO, int id) {
        Course course = new Course();
        // Course id does not need to be mapped
        // It is generated in the createCourse method
        course.setTeacherId(id);
        course.setName(courseDTO.getName());
        course.setDescription(courseDTO.getDescription());
        course.setDifficultyLevel(courseDTO.getDifficulty());
        course.setCost(courseDTO.getCost());
        return course;
    }
}
