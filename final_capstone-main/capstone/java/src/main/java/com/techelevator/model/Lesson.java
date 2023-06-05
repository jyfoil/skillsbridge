package com.techelevator.model;

import java.time.LocalDate;

public class Lesson {
    private int id;
    private int moduleId;
    private String title;
    private String content;
    private String resources;
    private LocalDate dueDate;
    private String instructions;
    private boolean has_assignment;

    public Lesson() {

    }

    public Lesson(int id, int moduleId, String title, String content, String resources, LocalDate dueDate, String instructions, boolean has_assignment) {
        this.id = id;
        this.moduleId = moduleId;
        this.title = title;
        this.content = content;
        this.resources = resources;
        this.dueDate = dueDate;
        this.instructions = instructions;
        this.has_assignment = has_assignment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getModuleId() {
        return moduleId;
    }

    public void setModuleId(int moduleId) {
        this.moduleId = moduleId;
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

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
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
                ", moduleId=" + moduleId +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", resources='" + resources + '\'' +
                ", due_date=" + dueDate +
                ", instructions='" + instructions + '\'' +
                ", has_assignment=" + has_assignment +
                '}';
    }
}
