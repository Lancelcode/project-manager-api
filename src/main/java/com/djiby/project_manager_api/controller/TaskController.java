package com.djiby.project_manager_api.controller;

import com.djiby.project_manager_api.dto.TaskRequest;
import com.djiby.project_manager_api.dto.TaskResponse;
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
    public List<TaskResponse> getTasks(@PathVariable Long projectId) {
        return projectService.getTasksByProjectId(projectId);
    }

    @PostMapping
    public ResponseEntity<TaskResponse> addTask(@PathVariable Long projectId, @Valid @RequestBody TaskRequest request) {
        return ResponseEntity.status(201).body(projectService.addTaskToProject(projectId, request));
    }

    @GetMapping("/{taskId}")
    public TaskResponse getTask(@PathVariable Long projectId, @PathVariable Long taskId) {
        return projectService.getTaskById(projectId, taskId);
    }

    @PutMapping("/{taskId}")
    public TaskResponse updateTask(@PathVariable Long projectId, @PathVariable Long taskId, @Valid @RequestBody TaskRequest request) {
        return projectService.updateTask(projectId, taskId, request);
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long projectId, @PathVariable Long taskId) {
        projectService.deleteTask(projectId, taskId);
        return ResponseEntity.noContent().build();
    }
}