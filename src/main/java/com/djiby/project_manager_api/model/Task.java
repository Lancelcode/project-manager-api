package com.djiby.project_manager_api.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class Task {

    private Long id;

    @NotBlank
    private String title;

    @NotNull
    private TaskStatus status;

    public Task() {}

    public Task(Long id, String title, TaskStatus status) {
        this.id = id;
        this.title = title;
        this.status = status;
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public TaskStatus getStatus() { return status; }

    public void setId(Long id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setStatus(TaskStatus status) { this.status = status; }
}