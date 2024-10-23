package com.allocation.manager.service.impl;

import com.allocation.manager.model.Employee;
import com.allocation.manager.model.Project;
import com.allocation.manager.repository.ProjectRepository;
import com.allocation.manager.service.IProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProjectService implements IProjectService {
    @Autowired
    private ProjectRepository repository;

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
    public void deleteProject(UUID id) {
        repository.deleteById(id);
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
