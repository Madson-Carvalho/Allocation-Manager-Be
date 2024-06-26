package com.allocation.manager.service;

import com.allocation.manager.model.Project;
import com.allocation.manager.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepository repository;

    public Project createProject(Project project) {
        return repository.save(project);
    }

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
        existitingProject.getEmployees().addAll(project.getEmployees());
        return repository.save(existitingProject);
    }

    public void deleteProject(UUID id) {
        repository.deleteById(id);
    }

    public List<Project> findAll() {
        return repository.findAll();
    }
}
