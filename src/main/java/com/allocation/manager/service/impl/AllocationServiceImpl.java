package com.allocation.manager.service.impl;

import com.allocation.manager.exceptions.EmployeeAllocatedException;
import com.allocation.manager.model.Employee;
import com.allocation.manager.model.ProjectEmployee;
import com.allocation.manager.repository.EmployeeRepository;
import com.allocation.manager.repository.ProjectEmployeeRepository;
import com.allocation.manager.repository.ProjectRepository;
import com.allocation.manager.service.IAllocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.allocation.manager.util.FormatDataUtil.formatInstantDateTime;
import static com.allocation.manager.util.ValidationUtil.checkNotNullOrThrowEntityNotFound;
import static java.time.Duration.between;

@Service
public class AllocationServiceImpl implements IAllocationService {

    @Autowired
    private ProjectEmployeeRepository projectEmployeeRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public void allocateEmployeeInProject(ProjectEmployee projectEmployee) {

        var projectId = projectEmployee.getProject().getProjectId();
        var employeeId = projectEmployee.getEmployee().getEmployeeId();
        var startDate = projectEmployee.getStartDate();
        var endDate = projectEmployee.getEndDate();

        if (startDate.isAfter(endDate) || startDate.equals(endDate)) {
            throw new IllegalArgumentException("As datas fornecidas são inválidas. Verifique os valores e tente novamente.");
        }

        verifyIsEmployeeAllocatedToProject(projectId, employeeId, startDate, endDate);

        var project = checkNotNullOrThrowEntityNotFound(projectRepository.findByUuid(projectId), "Projeto não encontrado com o ID: " + projectId);
        var employee = checkNotNullOrThrowEntityNotFound(employeeRepository.findByUuid(employeeId), "Colaborador não encontrado com o ID: " + employeeId);

        long requestInSeconds = between(startDate, endDate).getSeconds();

        employee.verifyHoursDisponible(requestInSeconds);
        project.verifyProjectValidity(requestInSeconds);

        projectEmployeeRepository.save(projectEmployee);

        employee.setWorkInSeconds(employee.getWorkInSeconds() - requestInSeconds);
        employeeRepository.save(employee);
    }

    @Override
    public void updateAllocationsEmployeesInProjects(List<ProjectEmployee> projectsEmployees) {
        var newEmployees = new ArrayList<Employee>();

        for (ProjectEmployee pe : projectsEmployees) {
            var project = pe.getProject();
            var employee = pe.getEmployee();
            var startDate = pe.getStartDate();
            var endDate = pe.getEndDate();

            if (startDate.isAfter(endDate)) {
                throw new IllegalArgumentException("A data de início não pode ser posterior à data de término.");
            }

            var oldProjectEmployee = checkNotNullOrThrowEntityNotFound(projectEmployeeRepository.findByEmployeeIdAndProjectId(
                    employee.getEmployeeId(), project.getProjectId()), "Não há colaborador ID" + employee.getEmployeeId() + "Alocado no projeto ID:" + project.getProjectId()
            );

            long newRequestInSeconds = between(startDate, endDate).getSeconds();

            verifyIsEmployeeAllocatedToProject(project.getProjectId(), employee.getEmployeeId(), startDate, endDate);

            long currentAllocationInSeconds = between(oldProjectEmployee.getStartDate(), oldProjectEmployee.getEndDate()).getSeconds();
            employee.setWorkInSeconds(employee.getWorkInSeconds() + currentAllocationInSeconds);

            employee.verifyHoursDisponible(newRequestInSeconds);

            newEmployees.add(employee);
        }

        projectEmployeeRepository.saveAll(projectsEmployees);
        employeeRepository.saveAll(newEmployees);
    }

    @Override
    public List<ProjectEmployee> findAllEmployeeInProject(UUID employeeId, UUID projectId, Instant startDate, Instant endDate) {
        return projectEmployeeRepository.findAllEmployeeInProject(employeeId, projectId, startDate, endDate);
    }

    @Override
    public void deleteProjectEmployee(ProjectEmployee projectEmployee) {
        resetEmployeeWorkingHours(projectEmployee);
        projectEmployeeRepository.delete(projectEmployee);
    }

    private void verifyIsEmployeeAllocatedToProject(UUID projectId, UUID employeeId, Instant startDate, Instant endDate) {
        var allocation = projectEmployeeRepository.findAllEmployeeInProject(employeeId, null, startDate, endDate)
                .stream()
                .findFirst()
                .orElse(null);

        if (allocation != null && !allocation.getProject().getProjectId().equals(projectId))
            throw new EmployeeAllocatedException("O funcionário já está alocado no projeto "
                    + allocation.getProject().getName() + " durante o período de: "
                    + formatInstantDateTime(allocation.getStartDate()) + " à " + formatInstantDateTime(allocation.getEndDate()));
    }

    private void resetEmployeeWorkingHours(ProjectEmployee projectEmployee) {
        var startDate = projectEmployee.getStartDate();
        var endDate = projectEmployee.getEndDate();

        long requestInSeconds = between(startDate, endDate).getSeconds();
        var employee = projectEmployee.getEmployee();

        employee.setWorkInSeconds(employee.getWorkInSeconds() + requestInSeconds);
        employeeRepository.save(employee);
    }
}
