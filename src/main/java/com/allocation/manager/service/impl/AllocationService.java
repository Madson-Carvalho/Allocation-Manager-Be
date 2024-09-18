package com.allocation.manager.service.impl;

import com.allocation.manager.exceptions.EmployeeAllocatedException;
import com.allocation.manager.model.ProjectEmployee;
import com.allocation.manager.repository.EmployeeRepository;
import com.allocation.manager.repository.ProjectEmployeeRepository;
import com.allocation.manager.repository.ProjectRepository;
import com.allocation.manager.service.IAllocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
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
    public void allocationEmployeeWithProject(UUID employeeId, UUID projectId, Date startDate, Date endDate) {
        try {

            boolean isAllocated = isEmployeeAllocatedToProject(employeeId, startDate, endDate);
            if(isAllocated)
                throw new EmployeeAllocatedException("O funcionário já está alocado em outro projeto durante este período.");

            String messageErrorProject = "Projeto não encontrado com o ID: " + projectId;
            var project = checkNotNullOrThrowEntityNotFound(projectRepository.findByUuid(projectId), messageErrorProject);

            String messageErrorEmployee = "Colaborador não encontrado com o ID: " + employeeId;
            var employee = checkNotNullOrThrowEntityNotFound(employeeRepository.findByUuid(employeeId), messageErrorEmployee);


            //validar se o funcionario ainda contem horas no banco de horas disponiveis para ser alocado em um projeto.

            ProjectEmployee projectEmployee = new ProjectEmployee();
            projectEmployee.setEmployee(employee);
            projectEmployee.setProject(project);
            projectEmployee.setStartDate(startDate);
            projectEmployee.setEndDate(endDate);

            projectEmployeeRepository.save(projectEmployee);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    @Override
    public boolean isEmployeeAllocatedToProject(UUID employeeId, Date startDate, Date endDate) {
        try {
            return projectEmployeeRepository.isEmployeeAllocatedDuringPeriod(employeeId, startDate, endDate);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}
