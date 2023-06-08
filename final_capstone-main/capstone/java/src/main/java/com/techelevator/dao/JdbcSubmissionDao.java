package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.Submission;
import com.techelevator.model.SubmissionDTO;
import com.techelevator.model.SubmissionGradeSummaryDTO;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcSubmissionDao implements SubmissionDao {

    private JdbcTemplate jdbcTemplate;

    public JdbcSubmissionDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Submission postSubmission(Submission submission) {
        Submission createdSubmission = null;
        String sql = "INSERT INTO submissions (content, lesson_id, student_id) " +
                "VALUES (?, ?, ?) returning submission_id";
        System.out.println("inserting to lesson: " + submission.getLessonId() + ", student: " + submission.getStudentId());
        try {
            int newSubmissionId = jdbcTemplate.queryForObject(sql, int.class, submission.getContent(),
                    submission.getLessonId(), submission.getStudentId());
            createdSubmission = getSubmission(newSubmissionId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (BadSqlGrammarException e) {
            throw new DaoException("SQL syntax error", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }

        return createdSubmission;
    }

    public Submission getSubmission(int id) {
        Submission submission = null;
        String sql = "SELECT * FROM submissions WHERE submission_id = ?";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);

            if (results.next()) {
                submission = mapRowToSubmission(results);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (BadSqlGrammarException e) {
            throw new DaoException("SQL syntax error", e);
        }

        return submission;
    }

    @Override
    public List<Submission> getSubmissionsForLesson(int lessonId) {
        List<Submission> submissions = new ArrayList<>();

        String sql = "SELECT * FROM submissions WHERE lesson_id = ?";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, lessonId);

            while (results.next()) {
                submissions.add(mapRowToSubmission(results));
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (BadSqlGrammarException e) {
            throw new DaoException("SQL syntax error", e);
        }

        return submissions;
    }

    @Override
    public List<Submission> getSubmissionsForModule(int moduleId) {
        List<Submission> submissions = new ArrayList<>();

        String sql = "SELECT * FROM submissions s JOIN lessons l ON l.lesson_id = s.lesson_id WHERE l.module_id = ?;";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, moduleId);

            while (results.next()) {
                submissions.add(mapRowToSubmission(results));
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (BadSqlGrammarException e) {
            throw new DaoException("SQL syntax error", e);
        }

        return submissions;
    }

    @Override
    public Submission getSubmissionByLessonAndStudentId(int lessonId, int studentId) {
        Submission submission = null;

        String sql = "SELECT s.submission_id, s.content, s.lesson_id, s.student_id, s.grade, s.submitted_at " +
                "FROM submissions s " +
                "JOIN lessons l ON s.lesson_id = l.lesson_id " +
                "WHERE s.student_id = ? AND l.lesson_id = ?";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, studentId, lessonId);

            if (results.next()) {
                submission = mapRowToSubmission(results);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (BadSqlGrammarException e) {
            throw new DaoException("SQL syntax error", e);
        }

        return submission;
    }

    @Override
    public BigDecimal getGradeAverageForCourse(int studentId, int courseId) {
        BigDecimal gradeAvg = BigDecimal.ZERO;
        String sql = "SELECT AVG(grade) FROM submissions s " +
                "JOIN lessons l ON l.lesson_id = s.lesson_id " +
                "JOIN modules m ON m.module_id = l.module_id " +
                "WHERE s.student_id = ? AND m.course_id = ?";

        try {
            gradeAvg = jdbcTemplate.queryForObject(sql, BigDecimal.class, studentId, courseId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (BadSqlGrammarException e) {
            throw new DaoException("SQL syntax error", e);
        }

        return gradeAvg;
    }

    @Override
    public List<Submission> getSubmissionsForCourse(int courseId) {
        List<Submission> submissions = new ArrayList<>();

        String sql = "SELECT * FROM submissions s JOIN lessons l ON l.lesson_id = s.lesson_id JOIN modules m ON m" +
                ".module_id = l.module_id WHERE m.course_id = ?;";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, courseId);

            while (results.next()) {
                submissions.add(mapRowToSubmission(results));
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (BadSqlGrammarException e) {
            throw new DaoException("SQL syntax error", e);
        }

        return submissions;
    }

    @Override
    public List<SubmissionGradeSummaryDTO> getAverageGradeSummaryForStudentsInCourse(int courseId) {
        List<SubmissionGradeSummaryDTO> summaries = new ArrayList<>();

        String sql = "SELECT CONCAT(u.firstname, ' ', u.lastname) AS full_name, c.name AS course_name, AVG(s.grade) " +
                "AS average_grade " +
                "FROM users u " +
                "JOIN student_courses sc ON u.user_id = sc.student_id " +
                "JOIN courses c ON sc.course_id = c.course_id " +
                "LEFT JOIN submissions s ON u.user_id = s.student_id " +
                "LEFT JOIN lessons l ON s.lesson_id = l.lesson_id " +
                "WHERE c.course_id = 38 " +
                "GROUP BY u.user_id, full_name, course_name;";



        return summaries;
    }
    
    public List<Submission> getSubmissionsForCourseAndStudent(int courseId, int studentId) {
        List<Submission> submissions = new ArrayList<>();
        String sql = "SELECT * FROM submissions s JOIN lessons l ON l.lesson_id = s.lesson_id JOIN modules m ON m" +
                ".module_id = l.module_id WHERE m.course_id = ? AND s.student_id = ?;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, courseId, studentId);
            while (results.next()) {
                submissions.add(mapRowToSubmission(results));
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (BadSqlGrammarException e) {
            throw new DaoException("SQL syntax error", e);
        }
        return submissions;
    }

    @Override
    public void setSubmissionGrade(Submission submission, int id) {
        String sql = "UPDATE submissions SET grade = ? " +
                "WHERE submission_id = ?";

        try {
            int numberOfRows = jdbcTemplate.update(sql, submission.getGrade(), id);

            if (numberOfRows == 0) {
                throw new DaoException("Zero rows affected, expected at least one");
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (BadSqlGrammarException e) {
            throw new DaoException("SQL syntax error", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
    }

    @Override
    public void deleteSubmission(int submissionId) {
        String sql = "DELETE FROM submissions where submission_id = ?";

        try {
            jdbcTemplate.update(sql, submissionId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (BadSqlGrammarException e) {
            throw new DaoException("SQL syntax error", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
    }

    private Submission mapRowToSubmission(SqlRowSet rowSet) {
        Submission submission = new Submission();
        submission.setSubmissionId(rowSet.getInt("submission_id"));
        submission.setContent(rowSet.getString("content"));
        submission.setLessonId(rowSet.getInt("lesson_id"));
        submission.setStudentId(rowSet.getInt("student_id"));
        submission.setGrade(rowSet.getInt("grade"));
        submission.setSubmittedAt(rowSet.getString("submitted_at"));
        return submission;
    }

    public Submission mapSubmissionDtoToSubmission(SubmissionDTO submissionDto, int lessonId,
                                                   int studentId) {
        Submission submission = new Submission();
        submission.setContent(submissionDto.getContent());
        submission.setLessonId(lessonId);
        submission.setStudentId(studentId);
        return submission;
    }

    @Override
    public SubmissionDTO mapSubmissionToSubmissionDto(Submission submission, String fullName, String courseName,
                                                      String title) {
        SubmissionDTO submissionDto = new SubmissionDTO();
        submissionDto.setLessonId(submission.getLessonId());
        submissionDto.setContent(submission.getContent());
        submissionDto.setFullName(fullName);
        submissionDto.setCourseName(courseName);
        submissionDto.setTitle(title);
        submissionDto.setSubmittedAt(submission.getSubmittedAt());
        return submissionDto;
    }


}
