package com.allocation.manager.service;

import com.allocation.manager.model.Employee;
import com.allocation.manager.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee updateEmployee(Employee employee) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(employee.getEmployeeId());
        Employee existingEmployee = optionalEmployee.get();
        existingEmployee.setName(employee.getName());
        existingEmployee.setEmail(employee.getEmail());
        existingEmployee.setJobRole(employee.getJobRole());
        existingEmployee.setWage(employee.getWage());
        existingEmployee.setQualification(employee.getQualification());
        existingEmployee.setWorkeHours(employee.getWorkeHours());
        existingEmployee.setSpecializations(employee.getSpecializations());
        return employeeRepository.save(existingEmployee);
    }

    public void deleteEmployee(UUID employeeId) {
        employeeRepository.deleteById(employeeId);
    }

    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }
}
