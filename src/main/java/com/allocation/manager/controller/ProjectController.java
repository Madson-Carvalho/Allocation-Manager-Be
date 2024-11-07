package com.allocation.manager.controller;

import com.allocation.manager.controller.routes.Routes;
import com.allocation.manager.model.Project;
import com.allocation.manager.service.IProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/projects")
public class ProjectController {
    @Autowired
    private IProjectService service;

    @PostMapping(Routes.CreateProject)
    public ResponseEntity<Project> createProject(@RequestBody Project project) {
        return ResponseEntity.ok(service.createProject(project));
    }

    @PutMapping(Routes.UpdateProject)
    public ResponseEntity<Project> updatePrject(@RequestBody Project project) {
        return ResponseEntity.ok(service.updateProject(project));
    }

    @DeleteMapping(Routes.DeleteProject)
    public void deleteProject(@PathVariable UUID id) {
        service.deleteProject(id);
    }

    @GetMapping(Routes.FindAllProjects)
    public List<Project> findAll() {
        return service.findAll();
    }

    @GetMapping(Routes.FindProjectById)
    public Optional<Project> findById(@PathVariable UUID id) {
            return service.findById(id);
    }
}
