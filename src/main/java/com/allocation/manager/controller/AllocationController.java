package com.allocation.manager.controller;

import com.allocation.manager.controller.routes.Routes;
import com.allocation.manager.model.ProjectEmployee;
import com.allocation.manager.service.IAllocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("api/v1/allocation")
public class AllocationController {

    @Autowired
    private IAllocationService service;

    @PostMapping(Routes.AllocationEmployeeInProject)
    public ResponseEntity<Void> allocatedEmployeeInProject(@PathVariable UUID employeeId, @PathVariable UUID projectId, @PathVariable Instant startDate, @PathVariable Instant endDate) {
        try {
            service.allocationEmployeeWithProject(employeeId, projectId, startDate, endDate);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping(Routes.FindAllProjectsEmployees)
    public ResponseEntity<List<ProjectEmployee>> findAllEmployeeInProject(@RequestParam(required = false) UUID employeeId, @RequestParam(required = false) UUID projectId, @RequestParam(required = false) Instant startDate, @RequestParam(required = false) Instant endDate) {
        try{
            return ResponseEntity.ok(service.findAllEmployeeInProject(employeeId, projectId, startDate, endDate));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
