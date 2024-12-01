package com.allocation.manager.unit;

import com.allocation.manager.model.Employee;
import com.allocation.manager.model.Project;
import com.allocation.manager.service.impl.AllocationServiceImpl;
import com.allocation.manager.service.impl.ProjectServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProjectServiceImplTest extends BaseUnitTest {

    @InjectMocks
    private ProjectServiceImpl projectService;

    @Mock
    private AllocationServiceImpl allocationService;

    private Project project;
    private UUID projectId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        project = createProject();
        projectId = project.getProjectId();
    }

    @Test
    void testCreateProject() {
        when(projectRepository.save(project)).thenReturn(project);

        Project createdProject = projectService.createProject(project);

        assertNotNull(createdProject);
        assertEquals(project.getProjectId(), createdProject.getProjectId());
        assertEquals(project.getName(), createdProject.getName());
        verify(projectRepository, times(1)).save(project);
    }

    @Test
    void testUpdateProject() {
        Project updatedProject = createProject();
        updatedProject.setName("Updated Project");
        updatedProject.setProjectHours(800);

        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));
        when(projectRepository.save(any(Project.class))).thenReturn(updatedProject);

        Project result = projectService.updateProject(updatedProject);

        assertNotNull(result);
        assertEquals("Updated Project", result.getName());
        assertEquals(800, result.getProjectHours());
        verify(projectRepository, times(1)).findById(projectId);
        verify(projectRepository, times(1)).save(any(Project.class));
    }

    @Test
    void testDeleteProject_NotFound() {
        when(projectRepository.existsById(projectId)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> projectService.deleteProject(projectId));

        verify(projectRepository, times(1)).existsById(projectId);
    }

    @Test
    void testDeleteProject() {
        when(projectRepository.existsById(projectId)).thenReturn(true);
        projectService.deleteProject(projectId);

        verify(allocationService, times(1)).findAllEmployeeInProject(null, projectId, null, null);
        verify(projectRepository, times(1)).deleteById(projectId);
    }

    @Test
    void testFindAll() {
        when(projectRepository.findAll()).thenReturn(Collections.singletonList(createProject()));

        List<Project> projects = projectService.findAll();

        assertNotNull(projects);
        assertFalse(projects.isEmpty());
        verify(projectRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        when(projectRepository.findById(projectId)).thenReturn(Optional.of(createProject()));

        Optional<Project> foundProject = projectService.findById(projectId);

        assertTrue(foundProject.isPresent());
        assertEquals(projectId, foundProject.get().getProjectId());
        verify(projectRepository, times(1)).findById(projectId);
    }
}
