package com.allocation.manager.controller;

import com.allocation.manager.model.Project;
import com.allocation.manager.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/projects")
public class ProjectController {
    @Autowired
    private ProjectService service;

    @PostMapping("/create-project")
    public ResponseEntity<Project> createProject(@RequestBody Project project) {
        return ResponseEntity.ok(service.createProject(project));
    }

    @PutMapping("/update-project")
    public ResponseEntity<Project> updatePrject(@RequestBody Project project) {
        return ResponseEntity.ok(service.updateProject(project));
    }

    @DeleteMapping("/delete-project/{id}")
    public void deleteProject(@PathVariable UUID id) {
        service.deleteProject(id);
    }

    @GetMapping("/find-all")
    public List<Project> findAll() {
        return service.findAll();
    }
}
