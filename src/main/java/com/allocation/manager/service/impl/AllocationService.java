package com.allocation.manager.service.impl;

import com.allocation.manager.exceptions.EmployeeAllocatedException;
import com.allocation.manager.exceptions.InsufficientWorkHoursException;
import com.allocation.manager.model.ProjectEmployee;
import com.allocation.manager.repository.EmployeeRepository;
import com.allocation.manager.repository.ProjectEmployeeRepository;
import com.allocation.manager.repository.ProjectRepository;
import com.allocation.manager.service.IAllocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

import static com.allocation.manager.util.ValidationUtil.checkNotNullOrThrowEntityNotFound;

@Service
public class AllocationService implements IAllocationService {

    @Autowired
    private ProjectEmployeeRepository projectEmployeeRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private EmployeeRepository employeeRepository;


    @Override
    public void allocationEmployeeWithProject(UUID employeeId, UUID projectId, Instant startDate, Instant endDate) throws EmployeeAllocatedException, InsufficientWorkHoursException {

        boolean isAllocated = isEmployeeAllocatedToProject(employeeId, startDate, endDate);
        if (isAllocated)
            throw new EmployeeAllocatedException("O funcionário já está alocado em outro projeto durante este período.");

        String messageErrorProject = "Projeto não encontrado com o ID: " + projectId;
        var project = checkNotNullOrThrowEntityNotFound(projectRepository.findByUuid(projectId), messageErrorProject);

        String messageErrorEmployee = "Colaborador não encontrado com o ID: " + employeeId;
        var employee = checkNotNullOrThrowEntityNotFound(employeeRepository.findByUuid(employeeId), messageErrorEmployee);

        long requestInSeconds = Duration.between(startDate, endDate).getSeconds();
        if (employee.verifyHoursDisponible(requestInSeconds))
            throw new InsufficientWorkHoursException("O colaborador não contém horas disponiveis para a alocação neste projeto");

        ProjectEmployee projectEmployee = new ProjectEmployee(project, employee, startDate, endDate);
        projectEmployeeRepository.save(projectEmployee);

        employee.setWorkInSeconds(employee.getWorkInSeconds() - requestInSeconds);
        employeeRepository.save(employee);
    }

    @Override
    public boolean isEmployeeAllocatedToProject(UUID employeeId, Instant startDate, Instant endDate) {
        try {
            return projectEmployeeRepository.isEmployeeAllocatedDuringPeriod(employeeId, startDate, endDate);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}
