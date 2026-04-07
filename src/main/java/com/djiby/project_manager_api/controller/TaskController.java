package com.djiby.project_manager_api.controller;

import com.djiby.project_manager_api.model.Task;
import com.djiby.project_manager_api.service.ProjectService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects/{projectId}/tasks")
public class TaskController {

    private final ProjectService projectService;

    public TaskController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping
    public List<Task> getTasks(@PathVariable Long projectId) {
        return projectService.getTasksByProjectId(projectId);
    }

    @PostMapping
    public ResponseEntity<Task> addTask(
            @PathVariable Long projectId,
            @Valid @RequestBody Task task) {

        Task created = projectService.addTaskToProject(projectId, task);
        return ResponseEntity.status(201).body(created);
    }

    @GetMapping("/{taskId}")
    public Task getTask(
            @PathVariable Long projectId,
            @PathVariable Long taskId) {

        return projectService.getTaskById(projectId, taskId);
    }

    @PutMapping("/{taskId}")
    public Task updateTask(
            @PathVariable Long projectId,
            @PathVariable Long taskId,
            @Valid @RequestBody Task task) {

        return projectService.updateTask(projectId, taskId, task);
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(
            @PathVariable Long projectId,
            @PathVariable Long taskId) {

        projectService.deleteTask(projectId, taskId);
        return ResponseEntity.noContent().build();
    }
}