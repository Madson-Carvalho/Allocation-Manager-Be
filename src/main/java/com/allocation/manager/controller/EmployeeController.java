package com.allocation.manager.controller;

import com.allocation.manager.controller.routes.Routes;
import com.allocation.manager.model.Employee;
import com.allocation.manager.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/employees")
public class EmployeeController {
    @Autowired
    private IEmployeeService service;

    @PostMapping(Routes.CreateEmployee)
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        return ResponseEntity.ok(service.createEmployee(employee));
    }

    @PutMapping(Routes.UpdateEmployee)
    public ResponseEntity<Employee> updateEmployee(@RequestBody Employee employee) {
        return ResponseEntity.ok(service.updateEmployee(employee));
    }

    @DeleteMapping(Routes.DeleteEmployee)
    public void deleteEmployee(@PathVariable UUID id) {
        service.deleteEmployee(id);
    }

    @GetMapping(Routes.FindAllEmployees)
    public List<Employee> findAll() {
        return service.findAll();
    }

    @GetMapping(Routes.FindEmployeeById)
    public Optional<Employee> findById(@PathVariable UUID id) {
        return service.findById(id);
    }
}
