package com.techelevator.model;

public class SubmissionsByModuleDTO {
    private int moduleId;
    private int count;

    public SubmissionsByModuleDTO() {
    }

    public SubmissionsByModuleDTO(int moduleId, int count) {
        this.moduleId = moduleId;
        this.count = count;
    }

    public int getModuleId() {
        return moduleId;
    }

    public void setModuleId(int moduleId) {
        this.moduleId = moduleId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
