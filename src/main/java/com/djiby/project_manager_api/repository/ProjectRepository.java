package com.djiby.project_manager_api.repository;

import com.djiby.project_manager_api.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}