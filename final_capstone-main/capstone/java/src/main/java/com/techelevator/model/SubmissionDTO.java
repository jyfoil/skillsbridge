package com.techelevator.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SubmissionDTO {

    private int lessonId;
    private String content;
    private String fullName;
    private String courseName;
    private String title;
    private String submittedAt;
    private int grade;

    public SubmissionDTO() {
    }

    public SubmissionDTO(int lessonId, String content, String fullName, String courseName, String title) {
        this.lessonId = lessonId;
        this.content = content;
        this.fullName = fullName;
        this.courseName = courseName;
        this.title = title;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss a");
        this.submittedAt = LocalDateTime.now().format(formatter);
    }

    public int getLessonId() {
        return lessonId;
    }

    public void setLessonId(int lessonId) {
        this.lessonId = lessonId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubmittedAt() {
        return submittedAt;
    }

    public void setSubmittedAt(String submittedAt) {
        this.submittedAt = submittedAt;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "SubmissionDTO{" +
                "lessonId=" + lessonId +
                ", content='" + content + '\'' +
                ", fullName='" + fullName + '\'' +
                ", courseName='" + courseName + '\'' +
                ", title='" + title + '\'' +
                ", submittedAt=" + submittedAt +
                '}';
    }
}
