package com.djiby.project_manager_api.dto;

import com.djiby.project_manager_api.model.ProjectStatus;
import java.util.List;

public class ProjectResponse {

    private Long id;
    private String name;
    private String description;
    private ProjectStatus status;
    private List<TaskResponse> tasks;

    public ProjectResponse(Long id, String name, String description, ProjectStatus status, List<TaskResponse> tasks) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
        this.tasks = tasks;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public ProjectStatus getStatus() { return status; }
    public List<TaskResponse> getTasks() { return tasks; }
}