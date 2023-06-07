package com.techelevator.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Submission {
    private int submissionId;
    private String content;
    private int lessonId;
    private int studentId;
    private int grade;
    private String submittedAt;

    public Submission() {
    }

    public Submission(int submissionId, String content, int lessonId, int studentId, int grade) {
        this.submissionId = submissionId;
        this.content = content;
        this.lessonId = lessonId;
        this.studentId = studentId;
        this.grade = grade;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss a");
        this.submittedAt = LocalDateTime.now().format(formatter);
    }

    public int getSubmissionId() {
        return submissionId;
    }

    public void setSubmissionId(int submissionId) {
        this.submissionId = submissionId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getLessonId() {
        return lessonId;
    }

    public void setLessonId(int lessonId) {
        this.lessonId = lessonId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getSubmittedAt() {
        return submittedAt;
    }

    public void setSubmittedAt(String submittedAt) {
        this.submittedAt = submittedAt;
    }

    @Override
    public String toString() {
        return "Submission{" +
                "submissionId=" + submissionId +
                ", content='" + content + '\'' +
                ", lessonId=" + lessonId +
                ", studentId=" + studentId +
                ", grade=" + grade +
                ", submittedAt=" + submittedAt +
                '}';
    }
}
