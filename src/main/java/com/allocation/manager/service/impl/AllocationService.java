package com.allocation.manager.service.impl;

import com.allocation.manager.exceptions.EmployeeAllocatedException;
import com.allocation.manager.exceptions.InsufficientWorkHoursException;
import com.allocation.manager.model.Employee;
import com.allocation.manager.model.ProjectEmployee;
import com.allocation.manager.repository.EmployeeRepository;
import com.allocation.manager.repository.ProjectEmployeeRepository;
import com.allocation.manager.repository.ProjectRepository;
import com.allocation.manager.service.IAllocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
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
    public void allocationEmployeeWithProject(UUID employeeId, UUID projectId, Instant startDate, Instant endDate) {

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
    public void updateAllocationsEmployeesWithProjects(List<ProjectEmployee> projectsEmployees) {
        var newEmployees = new ArrayList<Employee>();

        for (ProjectEmployee pe : projectsEmployees) {

            var project = pe.getProject();
            var employee = pe.getEmployee();
            var startDate = pe.getStartDate();
            var endDate = pe.getEndDate();

            var oldProjectEmployee = checkNotNullOrThrowEntityNotFound(projectEmployeeRepository.findByEmployeeIdAndProjectId(
                    employee.getEmployeeId(), project.getProjectId()), "Não há colaborador ID" + employee.getEmployeeId() + "Alocado no projeto ID:" + project.getProjectId()
            );

            long newRequestInSeconds = Duration.between(startDate, endDate).getSeconds();

            verifyIsEmployeeAllocatedToProject(employee.getEmployeeId(), startDate, endDate);

            long currentAllocationInSeconds = Duration.between(oldProjectEmployee.getStartDate(), oldProjectEmployee.getEndDate()).getSeconds();
            employee.setWorkInSeconds(employee.getWorkInSeconds() + currentAllocationInSeconds);

            if (employee.verifyHoursDisponible(newRequestInSeconds))
                throw new InsufficientWorkHoursException("O colaborador não contém horas disponíveis suficientes para a nova alocação.");

            newEmployees.add(employee);
        }

        projectEmployeeRepository.saveAll(projectsEmployees);
        employeeRepository.saveAll(newEmployees);
    }

    @Override
    public List<ProjectEmployee> findAllEmployeeInProject(UUID employeeId, UUID projectId, Instant startDate, Instant endDate) {
        return projectEmployeeRepository.findAllEmployeeInProject(employeeId, projectId, startDate, endDate);
    }

    private boolean isEmployeeAllocatedToProject(UUID employeeId, Instant startDate, Instant endDate) {
        return projectEmployeeRepository.isEmployeeAllocatedDuringPeriod(employeeId, startDate, endDate);
    }

    private void verifyIsEmployeeAllocatedToProject(UUID employeeId, Instant startDate, Instant endDate) {
        boolean isAllocated = isEmployeeAllocatedToProject(employeeId, startDate, endDate);
        if (isAllocated)
            throw new EmployeeAllocatedException("O funcionário já está alocado em outro projeto durante este período.");
    }
}
