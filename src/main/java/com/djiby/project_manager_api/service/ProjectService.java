package com.djiby.project_manager_api.service;

import com.djiby.project_manager_api.dto.ProjectRequest;
import com.djiby.project_manager_api.dto.ProjectResponse;
import com.djiby.project_manager_api.dto.TaskRequest;
import com.djiby.project_manager_api.dto.TaskResponse;
import com.djiby.project_manager_api.exception.ProjectNotFoundException;
import com.djiby.project_manager_api.exception.TaskNotFoundException;
import com.djiby.project_manager_api.model.Project;
import com.djiby.project_manager_api.model.Task;
import com.djiby.project_manager_api.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    // ── mapping helpers ──────────────────────────────────────────────

    private TaskResponse toTaskResponse(Task task) {
        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getStatus(),
                task.getPriority(),
                task.getDueDate()
        );
    }

    private ProjectResponse toProjectResponse(Project project) {
        List<TaskResponse> taskResponses = project.getTasks()
                .stream()
                .map(this::toTaskResponse)
                .toList();
        return new ProjectResponse(
                project.getId(),
                project.getName(),
                project.getDescription(),
                project.getStatus(),
                taskResponses
        );
    }

    // ── projects ─────────────────────────────────────────────────────

    public List<ProjectResponse> getAllProjects() {
        return projectRepository.findAll()
                .stream()
                .map(this::toProjectResponse)
                .toList();
    }

    public ProjectResponse getProjectById(Long id) {
        return toProjectResponse(findProjectById(id));
    }

    public ProjectResponse createProject(ProjectRequest request) {
        Project project = new Project();
        project.setName(request.getName());
        project.setDescription(request.getDescription());
        project.setStatus(request.getStatus());
        return toProjectResponse(projectRepository.save(project));
    }

    public ProjectResponse updateProject(Long id, ProjectRequest request) {
        Project project = findProjectById(id);
        project.setName(request.getName());
        project.setDescription(request.getDescription());
        project.setStatus(request.getStatus());
        return toProjectResponse(projectRepository.save(project));
    }

    public void deleteProject(Long id) {
        Project project = findProjectById(id);
        projectRepository.delete(project);
    }

    // ── tasks ─────────────────────────────────────────────────────────

    public List<TaskResponse> getTasksByProjectId(Long projectId) {
        return findProjectById(projectId).getTasks()
                .stream()
                .map(this::toTaskResponse)
                .toList();
    }

    public TaskResponse addTaskToProject(Long projectId, TaskRequest request) {
        Project project = findProjectById(projectId);
        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setStatus(request.getStatus());
        task.setPriority(request.getPriority());
        task.setDueDate(request.getDueDate());
        task.setProject(project);
        project.getTasks().add(task);
        projectRepository.save(project);
        return toTaskResponse(task);
    }

    public TaskResponse getTaskById(Long projectId, Long taskId) {
        return toTaskResponse(findTaskById(projectId, taskId));
    }

    public TaskResponse updateTask(Long projectId, Long taskId, TaskRequest request) {
        Task task = findTaskById(projectId, taskId);
        task.setTitle(request.getTitle());
        task.setStatus(request.getStatus());
        task.setPriority(request.getPriority());
        task.setDueDate(request.getDueDate());
        projectRepository.save(task.getProject());
        return toTaskResponse(task);
    }

    public void deleteTask(Long projectId, Long taskId) {
        Project project = findProjectById(projectId);
        project.getTasks().removeIf(t -> t.getId().equals(taskId));
        projectRepository.save(project);
    }

    // ── private helpers ───────────────────────────────────────────────

    private Project findProjectById(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new ProjectNotFoundException(id));
    }

    private Task findTaskById(Long projectId, Long taskId) {
        return findProjectById(projectId).getTasks().stream()
                .filter(t -> t.getId().equals(taskId))
                .findFirst()
                .orElseThrow(() -> new TaskNotFoundException(taskId));
    }
}