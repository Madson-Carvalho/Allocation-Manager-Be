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
    private EmployeeRepository employeeRepository;

    @Override
    public void allocateEmployeeInProject(ProjectEmployee projectEmployee) {

        if (projectEmployee.getStartDate().isAfter(projectEmployee.getEndDate()) || projectEmployee.getStartDate().equals(projectEmployee.getEndDate())) {
            throw new IllegalArgumentException("As datas fornecidas são inválidas. Verifique os valores e tente novamente.");
        }

        UUID employeeId = projectEmployee.getEmployee().getEmployeeId();
        Instant startDate = projectEmployee.getStartDate();
        Instant endDate = projectEmployee.getEndDate();
        long requestedHoursPerDay = projectEmployee.getAllocatedHours();

        List<ProjectEmployee> overlappingAllocations = projectEmployeeRepository.findOverlappingAllocations(employeeId, startDate, endDate);

        long dailyWorkHours = projectEmployee.getEmployee().getWorkInSeconds(); // Agora é horas, não segundos
        validateDailyHoursAvailability(overlappingAllocations, startDate, endDate, requestedHoursPerDay, dailyWorkHours);

        projectEmployeeRepository.save(projectEmployee);
    }

    @Override
    public void updateAllocationsEmployeesInProjects(List<ProjectEmployee> projectsEmployees) {
        var newEmployees = new ArrayList<Employee>();

        for (ProjectEmployee pe : projectsEmployees) {
            var project = pe.getProject();
            var employee = pe.getEmployee();
            var startDate = pe.getStartDate();
            var endDate = pe.getEndDate();

            if (startDate.isAfter(endDate) || startDate.equals(endDate)) {
                throw new IllegalArgumentException("As datas fornecidas são inválidas. Verifique os valores e tente novamente.");
            }

            var oldProjectEmployee = checkNotNullOrThrowEntityNotFound(projectEmployeeRepository.findByEmployeeIdAndProjectId(
                    employee.getEmployeeId(), project.getProjectId()), "Não há colaborador ID" + employee.getEmployeeId() + "Alocado no projeto ID:" + project.getProjectId()
            );

            long newRequestInSeconds = between(startDate, endDate).getSeconds();

            verifyIsEmployeeAllocatedToProject(pe);

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

    private void verifyIsEmployeeAllocatedToProject(ProjectEmployee projectEmployee) {
        var allocation = projectEmployeeRepository.findAllEmployeeInProject(projectEmployee.getEmployee().getEmployeeId(), null,
                        projectEmployee.getStartDate(), projectEmployee.getEndDate())
                .stream()
                .findFirst()
                .orElse(null);

        if (allocation != null && !allocation.getProject().getProjectId().equals(projectEmployee.getProject().getProjectId()))
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

    private void validateDailyHoursAvailability(List<ProjectEmployee> allocations, Instant startDate, Instant endDate, long requestedHoursPerDay, long dailyWorkHours) {
        Instant current = startDate;
        while (!current.isAfter(endDate)) {
            final Instant finalCurrent = current;

            long allocatedHoursOnDay = allocations.stream()
                    .filter(a -> !a.getStartDate().isAfter(finalCurrent) && !a.getEndDate().isBefore(finalCurrent))
                    .mapToLong(ProjectEmployee::getAllocatedHours)
                    .sum();

            if (allocatedHoursOnDay + requestedHoursPerDay > dailyWorkHours) {
                throw new InsufficientWorkHoursException(
                        "Horas insuficientes para o dia: " + finalCurrent +
                                ". Alocado: " + allocatedHoursOnDay + " horas, Solicitado: " + requestedHoursPerDay +
                                ", Disponível: " + (dailyWorkHours - allocatedHoursOnDay) + " horas."
                );
            }

            current = current.plusSeconds(86400);
        }
    }
}
