package com.djiby.project_manager_api.controller;

import com.djiby.project_manager_api.model.Project;
import com.djiby.project_manager_api.service.ProjectService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@RestController
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/projects")
    public List<Project> getAllProjects() {
        return projectService.getAllProjects();
    }

    @GetMapping("/projects/{id}")
    public Project getProjectById(@PathVariable Long id) {
        return projectService.getProjectById(id);
    }
}