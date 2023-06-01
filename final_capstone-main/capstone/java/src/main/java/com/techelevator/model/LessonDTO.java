package com.techelevator.model;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

public class LessonDTO {
    private int id = 0;
    private int courseId;
    @NotEmpty
    private String title;
    @NotEmpty
    private String content;
    @NotEmpty
    private String resources;
    private LocalDate due_date;
    private String instructions;
    private boolean has_assignment = false;

    public LessonDTO() {

    }

    public LessonDTO(String title, String content, String resources, LocalDate due_date, String instructions) {
        this.title = title;
        this.content = content;
        this.resources = resources;
        this.due_date = due_date;
        this.instructions = instructions;
        if (instructions != "" && instructions != null) {
            this.has_assignment = true;
        }
    }

    public LessonDTO(int courseId, String title, String content, String resources, LocalDate due_date, String instructions) {
        this.courseId = courseId;
        this.title = title;
        this.content = content;
        this.resources = resources;
        this.due_date = due_date;
        this.instructions = instructions;
        if (instructions != "" && instructions != null) {
            this.has_assignment = true;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getResources() {
        return resources;
    }

    public void setResources(String resources) {
        this.resources = resources;
    }

    public LocalDate getDue_date() {
        return due_date;
    }

    public void setDue_date(LocalDate due_date) {
        this.due_date = due_date;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
        if (instructions != "" && instructions != null) {
            this.has_assignment = true;
        } else {
            this.has_assignment = false;
        }
    }

    public boolean isHas_assignment() {
        return has_assignment;
    }

    public void setHas_assignment(boolean has_assignment) {
        this.has_assignment = has_assignment;
    }

    @Override
    public String toString() {
        return "Lesson{" +
                "id=" + id +
                ", courseId=" + courseId +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", resources='" + resources + '\'' +
                ", due_date=" + due_date +
                ", instructions='" + instructions + '\'' +
                ", has_assignment=" + has_assignment +
                '}';
    }
}
