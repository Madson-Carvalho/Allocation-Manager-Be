package com.allocation.manager.service.impl;

import com.allocation.manager.exceptions.EmployeeAllocatedException;
import com.allocation.manager.model.ProjectEmployee;
import com.allocation.manager.repository.ProjectEmployeeRepository;
import com.allocation.manager.service.IAllocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AllocationService implements IAllocationService {

    @Autowired
    private ProjectEmployeeRepository repository;

    @Override
    public void allocationEmployeeWithProject(long employeeId, long projectId, Date startDate, Date endDate) {
        try {

            boolean isAllocated = isEmployeeAllocatedToProject(employeeId, startDate, endDate);

            if(isAllocated) throw new EmployeeAllocatedException("O funcionário já está alocado em outro projeto durante este período.");

            ProjectEmployee projectEmployee = new ProjectEmployee();
            projectEmployee.setEmployeeId(employeeId);
            projectEmployee.setProjectId(projectId);
            projectEmployee.setStartDate(startDate);
            projectEmployee.setEndDate(endDate);

            repository.save(projectEmployee);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public boolean isEmployeeAllocatedToProject(long employeeId, Date startDate, Date endDate) {
        try {
            return repository.isEmployeeAllocatedDuringPeriod(employeeId, startDate, endDate);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}
