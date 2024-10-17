package com.allocation.manager.service;

import com.allocation.manager.exceptions.EmployeeAllocatedException;
import com.allocation.manager.exceptions.InsufficientWorkHoursException;
import com.allocation.manager.model.ProjectEmployee;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface IAllocationService {
    void allocationEmployeeWithProject(UUID employeeId, UUID projectId, Instant startDate, Instant endDate) throws EmployeeAllocatedException, InsufficientWorkHoursException;
    boolean isEmployeeAllocatedToProject(UUID employeeId, Instant startDate, Instant endDate);

    List<ProjectEmployee> findAllEmployeeInProject(UUID employeeId, UUID projectId, Instant startDate, Instant endDate);

    void updateEmployeeAllocation(UUID employeeId, UUID projectId, Instant startDate, Instant endDate) throws EmployeeAllocatedException, InsufficientWorkHoursException;
}
