package com.techelevator.model;

import java.math.BigDecimal;

public class CourseDTO {

    private int courseId;
    private String name;
    private String description;
    private String difficulty;
    private BigDecimal cost;

    public CourseDTO() {
    }

    public CourseDTO(String name, String description, String difficulty, BigDecimal cost) {
        this.name = name;
        this.description = description;
        this.difficulty = difficulty;
        this.cost = cost;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "CourseDTO{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", difficulty='" + difficulty + '\'' +
                ", cost=" + cost +
                '}';
    }
}
