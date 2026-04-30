package com.djiby.project_manager_api.dto;

import com.djiby.project_manager_api.model.TaskPriority;
import com.djiby.project_manager_api.model.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class TaskRequest {

    @NotBlank
    private String title;

    @NotNull
    private TaskStatus status;

    private TaskPriority priority;

    private LocalDate dueDate;

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public TaskStatus getStatus() { return status; }
    public void setStatus(TaskStatus status) { this.status = status; }

    public TaskPriority getPriority() { return priority; }
    public void setPriority(TaskPriority priority) { this.priority = priority; }

    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }
}