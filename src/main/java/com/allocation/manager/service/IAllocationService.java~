package com.allocation.manager.service;

import com.allocation.manager.model.ProjectEmployee;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface IAllocationService {
    void allocateEmployeeInProject(UUID employeeId, UUID projectId, Instant startDate, Instant endDate);

    List<ProjectEmployee> findAllEmployeeInProject(UUID employeeId, UUID projectId, Instant startDate, Instant endDate);

    void updateAllocationsEmployeesInProjects(List<ProjectEmployee> projectsEmployees);

    void deleteProjectEmployee(ProjectEmployee projectEmployee);
}
