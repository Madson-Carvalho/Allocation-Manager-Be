package com.allocation.manager.service.impl;

import com.allocation.manager.model.Employee;
import com.allocation.manager.repository.EmployeeRepository;
import com.allocation.manager.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EmployeeServiceImpl implements IEmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public Employee updateEmployee(Employee employee) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(employee.getEmployeeId());
        Employee existingEmployee = optionalEmployee.get();
        existingEmployee.setName(employee.getName());
        existingEmployee.setEmail(employee.getEmail());
        existingEmployee.setJobRole(employee.getJobRole());
        existingEmployee.setWage(employee.getWage());
        existingEmployee.setQualification(employee.getQualification());
        existingEmployee.setWorkInSeconds(employee.getWorkInSeconds());
        existingEmployee.setSpecializations(employee.getSpecializations());

        return employeeRepository.save(existingEmployee);
    }

    @Override
    public void deleteEmployee(UUID employeeId) {
        employeeRepository.deleteById(employeeId);
    }

    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    @Override
    public Optional<Employee> findById(UUID employeeId) {
        return employeeRepository.findById(employeeId);
    }
}