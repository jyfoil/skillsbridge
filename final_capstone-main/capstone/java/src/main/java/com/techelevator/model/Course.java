package com.techelevator.model;

import java.math.BigDecimal;

public class Course {

    private int courseId;
    private int teacherId;
    private String name;
    private String description;
    private String difficultyLevel;
    private BigDecimal cost;

    public Course() {
    }

    public Course(String name, String description, String difficultyLevel, BigDecimal cost) {
        this.name = name;
        this.description = description;
        this.difficultyLevel = difficultyLevel;
        this.cost = cost;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
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

    public String getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(String difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "Course{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", difficultyLevel='" + difficultyLevel + '\'' +
                ", cost=" + cost +
                '}';
    }
}
