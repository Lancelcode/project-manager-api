package com.djiby.project_manager_api.service;

import com.djiby.project_manager_api.model.Project;
import com.djiby.project_manager_api.model.ProjectStatus;
import com.djiby.project_manager_api.model.Task;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectService {

    private final List<Project> projects = new ArrayList<>();

    private Long taskIdCounter = 1L;

    public ProjectService() {
        projects.add(new Project(1L, "Website redesign", "Build new company website", ProjectStatus.PLANNED));
        projects.add(new Project(2L, "Mobile app", "Create mobile application", ProjectStatus.IN_PROGRESS));
        projects.add(new Project(3L, "Bug tracker", "Internal bug tracking system", ProjectStatus.COMPLETED));
    }

    public List<Project> getAllProjects() {
        return projects;
    }

    public Project getProjectById(Long id) {
        for (Project project : projects) {
            if (project.getId().equals(id)) {
                return project;
            }
        }
        throw new RuntimeException("Project not found");
    }

    public Project createProject(Project project) {
        project.setId((long) (projects.size() + 1));
        projects.add(project);
        return project;
    }

    public Project updateProject(Long id, Project updatedProject) {
        for (Project project : projects) {
            if (project.getId().equals(id)) {
                project.setName(updatedProject.getName());
                project.setDescription(updatedProject.getDescription());
                project.setStatus(updatedProject.getStatus());
                return project;
            }
        }
        throw new RuntimeException("Project not found");
    }

    public Project deleteProject(Long id) {
        for (int i = 0; i < projects.size(); i++) {
            Project project = projects.get(i);
            if (project.getId().equals(id)) {
                projects.remove(i);
                return project;
            }
        }
        throw new RuntimeException("Project not found");
    }

    public List<Task> getTasksByProjectId(Long projectId) {
        Project project = getProjectById(projectId);
        return project.getTasks();
    }

    public Task addTaskToProject(Long projectId, Task task) {
        Project project = getProjectById(projectId);

        task.setId(taskIdCounter++);
        project.getTasks().add(task);

        return task;
    }

    public Task getTaskById(Long projectId, Long taskId) {
        Project project = getProjectById(projectId);

        return project.getTasks().stream()
                .filter(t -> t.getId().equals(taskId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Task not found"));
    }

    public Task updateTask(Long projectId, Long taskId, Task updatedTask) {
        Task task = getTaskById(projectId, taskId);

        task.setTitle(updatedTask.getTitle());
        task.setStatus(updatedTask.getStatus());

        return task;
    }

    public void deleteTask(Long projectId, Long taskId) {
        Project project = getProjectById(projectId);

        project.getTasks().removeIf(t -> t.getId().equals(taskId));
    }
}