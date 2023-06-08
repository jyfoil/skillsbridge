package com.techelevator.model;

import java.math.BigDecimal;

public class SubmissionGradeSummaryDTO {

    private String fullName;
    private String courseName;
    private BigDecimal averageGrade;

    public SubmissionGradeSummaryDTO() {
    }

    public SubmissionGradeSummaryDTO(String fullName, String courseName, BigDecimal averageGrade) {
        this.fullName = fullName;
        this.courseName = courseName;
        this.averageGrade = averageGrade;
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

    public BigDecimal getAverageGrade() {
        return averageGrade;
    }

    public void setAverageGrade(BigDecimal averageGrade) {
        this.averageGrade = averageGrade;
    }
}
