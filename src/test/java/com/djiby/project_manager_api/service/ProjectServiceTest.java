package com.djiby.project_manager_api.service;

import com.djiby.project_manager_api.dto.ProjectRequest;
import com.djiby.project_manager_api.dto.ProjectResponse;
import com.djiby.project_manager_api.exception.ProjectNotFoundException;
import com.djiby.project_manager_api.model.Project;
import com.djiby.project_manager_api.model.ProjectStatus;
import com.djiby.project_manager_api.repository.ProjectRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private ProjectService projectService;

    private Project buildProject() {
        Project project = new Project();
        project.setId(1L);
        project.setName("Test Project");
        project.setDescription("A description");
        project.setStatus(ProjectStatus.PLANNED);
        return project;
    }

    @Test
    void getAllProjects_returnsAllProjects() {
        when(projectRepository.findAll()).thenReturn(List.of(buildProject()));

        List<ProjectResponse> result = projectService.getAllProjects();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("Test Project");
    }

    @Test
    void getProjectById_returnsProject_whenFound() {
        when(projectRepository.findById(1L)).thenReturn(Optional.of(buildProject()));

        ProjectResponse result = projectService.getProjectById(1L);

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("Test Project");
    }

    @Test
    void getProjectById_throwsException_whenNotFound() {
        when(projectRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> projectService.getProjectById(99L))
                .isInstanceOf(ProjectNotFoundException.class)
                .hasMessageContaining("99");
    }

    @Test
    void createProject_savesAndReturnsProject() {
        ProjectRequest request = new ProjectRequest();
        request.setName("New Project");
        request.setDescription("Description");
        request.setStatus(ProjectStatus.PLANNED);

        Project saved = new Project();
        saved.setId(1L);
        saved.setName("New Project");
        saved.setDescription("Description");
        saved.setStatus(ProjectStatus.PLANNED);

        when(projectRepository.save(any(Project.class))).thenReturn(saved);

        ProjectResponse result = projectService.createProject(request);

        assertThat(result.getName()).isEqualTo("New Project");
        verify(projectRepository).save(any(Project.class));
    }

    @Test
    void deleteProject_deletesProject_whenFound() {
        when(projectRepository.findById(1L)).thenReturn(Optional.of(buildProject()));

        projectService.deleteProject(1L);

        verify(projectRepository).delete(any(Project.class));
    }

    @Test
    void updateProject_updatesAndReturnsProject() {
        Project existing = buildProject();
        when(projectRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(projectRepository.save(any(Project.class))).thenReturn(existing);

        ProjectRequest request = new ProjectRequest();
        request.setName("Updated");
        request.setDescription("Updated desc");
        request.setStatus(ProjectStatus.IN_PROGRESS);

        ProjectResponse result = projectService.updateProject(1L, request);

        assertThat(result.getName()).isEqualTo("Updated");
        assertThat(result.getStatus()).isEqualTo(ProjectStatus.IN_PROGRESS);
    }
}