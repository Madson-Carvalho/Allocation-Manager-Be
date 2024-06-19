package com.allocation.manager.controller;

import com.allocation.manager.model.Employee;
import com.allocation.manager.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/employees")
public class EmployeeController {
    @Autowired
    private EmployeeService service;

    @PostMapping("/create-employee")
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        return ResponseEntity.ok(service.createEmployee(employee));
    }

    @PutMapping("/update-employee")
    public ResponseEntity<Employee> updateEmployee(@RequestBody Employee employee) {
        return ResponseEntity.ok(service.updateEmployee(employee));
    }

    @DeleteMapping("/delete-employee/{id}")
    public void deleteEmployee(@PathVariable UUID id) {
        service.deleteEmployee(id);
    }

    @GetMapping("/find-all")
    public List<Employee> findAll() {
        return service.findAll();
    }
}
