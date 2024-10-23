package com.allocation.manager.controller;

import com.allocation.manager.controller.routes.Routes;
import com.allocation.manager.model.ProjectEmployee;
import com.allocation.manager.service.IAllocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("api/v1/allocation")
public class AllocationController {
    @Autowired
    private IAllocationService service;

    @PostMapping(Routes.AllocationEmployeeInProject)
    public ResponseEntity<Void> allocatedEmployeeInProject(@PathVariable UUID employeeId, @PathVariable UUID projectId, @PathVariable Instant startDate, @PathVariable Instant endDate) {
        service.allocationEmployeeWithProject(employeeId, projectId, startDate, endDate);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping(Routes.FindAllProjectsEmployees)
    public ResponseEntity<List<ProjectEmployee>> findAllEmployeeInProject(@RequestParam(required = false) UUID employeeId, @RequestParam(required = false) UUID projectId, @RequestParam(required = false) Instant startDate, @RequestParam(required = false) Instant endDate) {
        return ResponseEntity.ok(service.findAllEmployeeInProject(employeeId, projectId, startDate, endDate));
    }

    @PutMapping(Routes.UpdateAllocationsEmployeesWithProjects)
    public ResponseEntity<Void> updateAllocationsEmployeesWithProjects(@RequestBody List<ProjectEmployee> projectsEmployees) {
        service.updateAllocationsEmployeesWithProjects(projectsEmployees);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @PutMapping(Routes.UpdateAllocationEmployeeWithProject)
    public ResponseEntity<Void> updateAllocationEmployeeWithProject(@RequestBody ProjectEmployee projectsEmployees) {
        service.updateAllocationsEmployeesWithProjects(new ArrayList<>(new ArrayList<>(){{add(projectsEmployees);}}));
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @DeleteMapping(Routes.DeallocateEmployeeTheProject)
    public ResponseEntity<Void> DeallocateEmployeeTheProject(@RequestBody ProjectEmployee projectEmployee) {
        service.deleteProjectEmployee(projectEmployee);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

}
