package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.Submission;
import com.techelevator.model.SubmissionDTO;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.RowSet;
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
        String sql = "INSERT INTO submissions (content, lesson_id, student_id, grade) " +
                "VALUES (?, ?, ?, ?) returning submission_id";

        try {
            int newSubmissionId = jdbcTemplate.queryForObject(sql, int.class, submission.getContent(),
                    submission.getLessonId(), submission.getStudentId(), submission.getGrade());
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
    public List<Submission> getSubmissionsForCourse(int courseId) {
        List<Submission> submissions = new ArrayList<>();

        String sql = "SELECT * FROM submissions s JOIN lessons l ON l.lesson_id = s.lesson_id JOIN modules m ON m.module_id = l.module_id WHERE m.course_id = ?;";

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
    public void setSubmissionGrade(Submission submission, int id) {
        Submission updatedSubmission = null;
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
