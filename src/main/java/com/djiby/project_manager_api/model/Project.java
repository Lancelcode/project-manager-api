package com.djiby.project_manager_api.model;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import jakarta.validation.Valid;

public class Project {

    private Long id;
    
    @NotBlank
    private String name;
    
    @NotBlank
    private String description;

    @NotNull
    private ProjectStatus status;

    @Valid
    private List<Task> tasks = new ArrayList<>();
    
    public Project() {
    }

    public Project(Long id, String name, String description, ProjectStatus status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public ProjectStatus getStatus() {
        return status;
    }

    public void setStatus(ProjectStatus status) {
        this.status = status;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}