package com.allocation.manager.service;

import com.allocation.manager.model.Employee;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IEmployeeService {
    Employee createEmployee(Employee employee);

    Employee updateEmployee(Employee employee);

    void deleteEmployee(UUID employeeId);

    List<Employee> findAll();

    Optional<Employee> findById(UUID employeeId);
}
