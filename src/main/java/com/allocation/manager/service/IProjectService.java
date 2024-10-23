package com.allocation.manager.service;

import com.allocation.manager.model.Project;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IProjectService {
    Project createProject(Project project);
    Project updateProject(Project project);
    void deleteProject(UUID id);
    List<Project> findAll();
    Optional<Project> findById(UUID id);
}
