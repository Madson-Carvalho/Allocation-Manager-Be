package com.allocation.manager.unit;

import com.allocation.manager.model.Employee;
import com.allocation.manager.model.Project;
import com.allocation.manager.model.ProjectEmployee;
import com.allocation.manager.repository.EmployeeRepository;
import com.allocation.manager.repository.ProjectEmployeeRepository;
import com.allocation.manager.repository.ProjectRepository;

import org.mockito.Mock;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

public abstract class BaseUnitTest {

    @Mock
    protected ProjectRepository projectRepository;

    @Mock
    protected EmployeeRepository employeeRepository;

    @Mock
    protected ProjectEmployeeRepository projectEmployeeRepository;

    protected Project createProject() {
        Project project = new Project();
        project.setName("Web Application Development");
        project.setTotalProjectValue(30000);
        project.setProjectCoordinator("John Doe");
        project.setFundingSource("External Grant");
        project.setProjectHours(720);
        project.setInitialDate(Instant.now());
        project.setDeliveryDate(Instant.now().plus(30, ChronoUnit.DAYS));
        return project;
    }

    protected Employee createEmployee() {
        Employee employee = new Employee();
        employee.setName("Alice Johnson");
        employee.setEmail("alice.johnson@example.com");
        employee.setWorkInSeconds(720);
        employee.setAllocatedHours(0);
        return employee;
    }

    protected ProjectEmployee createProjectEmployee() {
        ProjectEmployee projectEmployee = new ProjectEmployee();
        projectEmployee.setEmployee(createEmployee());
        projectEmployee.setProject(createProject());
        projectEmployee.setStartDate(Instant.now());
        projectEmployee.setEndDate(Instant.now().plus(10, ChronoUnit.DAYS));
        return projectEmployee;
    }
}