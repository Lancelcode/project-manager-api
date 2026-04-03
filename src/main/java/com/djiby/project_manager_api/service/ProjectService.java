package com.djiby.project_manager_api.service;

import com.djiby.project_manager_api.model.Project;
import com.djiby.project_manager_api.model.ProjectStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectService {

    private final List<Project> projects = new ArrayList<>();

    public ProjectService() {
        projects.add(new Project(1L, "Website redesign", "Build new company website", ProjectStatus.PLANNED));
        projects.add(new Project(2L, "Mobile app", "Create mobile application", ProjectStatus.IN_PROGRESS));
        projects.add(new Project(3L, "Bug tracker", "Internal bug tracking system", ProjectStatus.COMPLETED));
    }

    public List<Project> getAllProjects() {
        return projects;
    }
}