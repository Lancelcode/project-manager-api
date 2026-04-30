package com.djiby.project_manager_api.dto;

import com.djiby.project_manager_api.model.TaskPriority;
import com.djiby.project_manager_api.model.TaskStatus;
import java.time.LocalDate;

public class TaskResponse {

    private Long id;
    private String title;
    private TaskStatus status;
    private TaskPriority priority;
    private LocalDate dueDate;

    public TaskResponse(Long id, String title, TaskStatus status, TaskPriority priority, LocalDate dueDate) {
        this.id = id;
        this.title = title;
        this.status = status;
        this.priority = priority;
        this.dueDate = dueDate;
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public TaskStatus getStatus() { return status; }
    public TaskPriority getPriority() { return priority; }
    public LocalDate getDueDate() { return dueDate; }
}