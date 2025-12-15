package com.codealchemists.tasklistapplication.model;

public enum TaskStatus {
    TO_DO("This task is to do"),
    IN_PROGRESS("Task in progress"),
    REVIEW("Task in review"),
    DONE("Task completed");

    private final String displayText;

    TaskStatus(String displayText) {
        this.displayText = displayText;
    }

    public String getDisplayText() {
        return displayText;
    }
}