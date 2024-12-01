package com.allocation.manager.service.impl;

import com.allocation.manager.model.Project;
import com.allocation.manager.model.ProjectEmployee;
import com.allocation.manager.repository.ProjectRepository;
import com.allocation.manager.service.IProjectService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProjectServiceImpl implements IProjectService {
    @Autowired
    private ProjectRepository repository;

    @Autowired
    private AllocationServiceImpl allocationService;

    @Override
    public Project createProject(Project project) {
        return repository.save(project);
    }

    @Override
    public Project updateProject(Project project) {
        Optional<Project> updatedProject = repository.findById(project.getProjectId());
        Project existitingProject = updatedProject.get();
        existitingProject.setName(project.getName());
        existitingProject.setProjectHours(project.getProjectHours());
        existitingProject.setProjectCoordinator(project.getProjectCoordinator());
        existitingProject.setFundingSource(project.getFundingSource());
        existitingProject.setTotalProjectValue(project.getTotalProjectValue());
        existitingProject.setInitialDate(project.getInitialDate());
        existitingProject.setDeliveryDate(project.getDeliveryDate());

        return repository.save(existitingProject);
    }

    @Override
    public void deleteProject(UUID projectId) {

        if(!repository.existsById(projectId)){
            throw new EntityNotFoundException("Nenhum projeto foi encontrado com o ID fornecido: " + projectId);
        }

        var allocations = allocationService.findAllEmployeeInProject(null, projectId, null, null);

        for (ProjectEmployee allocation : allocations) {
            allocationService.deleteProjectEmployee(allocation);
        }

        repository.deleteById(projectId);
    }

    @Override
    public List<Project> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Project> findById(UUID id) {
        return repository.findById(id);
    }
}
