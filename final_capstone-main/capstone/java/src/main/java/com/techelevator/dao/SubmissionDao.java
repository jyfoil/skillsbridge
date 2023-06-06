package com.techelevator.dao;

import com.techelevator.model.Submission;
import com.techelevator.model.SubmissionDTO;

public interface SubmissionDao {

    Submission postSubmission(Submission submission);

    Submission getSubmission(int id);

    void setSubmissionGrade(Submission submission, int id);

    void deleteSubmission(int id);

    Submission mapSubmissionDtoToSubmission(SubmissionDTO submissionDto, int lessonId,
                                            int studentId);

    SubmissionDTO mapSubmissionToSubmissionDto(Submission submission, String fullName, String courseName, String title);
}
