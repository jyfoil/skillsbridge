package com.techelevator.dao;

import com.techelevator.model.Submission;
import com.techelevator.model.SubmissionDTO;
import com.techelevator.model.SubmissionGradeSummaryDTO;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;
import java.util.List;

public interface SubmissionDao {

    Submission postSubmission(Submission submission);

    Submission getSubmission(int id);

    List<Submission> getSubmissionsForLesson(int lessonId);

    List<Submission> getSubmissionsForModule(int moduleId);

    Submission getSubmissionByLessonAndStudentId(int lessonId, int studentId);

    BigDecimal getGradeAverageForCourse(int studentId, int courseId);

    List<Submission> getSubmissionsForCourse(int courseId);

    List<SubmissionGradeSummaryDTO> getAverageGradeSummaryForStudentsInCourse(@PathVariable int courseId);

    void setSubmissionGrade(Submission submission, int id);

    void deleteSubmission(int id);

    Submission mapSubmissionDtoToSubmission(SubmissionDTO submissionDto, int lessonId,
                                            int studentId);

    SubmissionDTO mapSubmissionToSubmissionDto(Submission submission, String fullName, String courseName, String title);
}
