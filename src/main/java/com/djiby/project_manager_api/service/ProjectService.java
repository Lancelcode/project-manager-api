package com.djiby.project_manager_api.service;

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

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public Project getProjectById(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));
    }

    public Project createProject(Project project) {
        return projectRepository.save(project);
    }

    public Project updateProject(Long id, Project updatedProject) {
        Project project = getProjectById(id);
        project.setName(updatedProject.getName());
        project.setDescription(updatedProject.getDescription());
        project.setStatus(updatedProject.getStatus());
        return projectRepository.save(project);
    }

    public Project deleteProject(Long id) {
        Project project = getProjectById(id);
        projectRepository.delete(project);
        return project;
    }

    public List<Task> getTasksByProjectId(Long projectId) {
        return getProjectById(projectId).getTasks();
    }

    public Task addTaskToProject(Long projectId, Task task) {
        Project project = getProjectById(projectId);
        task.setProject(project);
        project.getTasks().add(task);
        projectRepository.save(project);
        return task;
    }

    public Task getTaskById(Long projectId, Long taskId) {
        return getProjectById(projectId).getTasks().stream()
                .filter(t -> t.getId().equals(taskId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Task not found"));
    }

    public Task updateTask(Long projectId, Long taskId, Task updatedTask) {
        Task task = getTaskById(projectId, taskId);
        task.setTitle(updatedTask.getTitle());
        task.setStatus(updatedTask.getStatus());
        projectRepository.save(task.getProject());
        return task;
    }

    public void deleteTask(Long projectId, Long taskId) {
        Project project = getProjectById(projectId);
        project.getTasks().removeIf(t -> t.getId().equals(taskId));
        projectRepository.save(project);
    }
}