package com.allocation.manager.unit;

import com.allocation.manager.exceptions.InsufficientWorkHoursException;
import com.allocation.manager.model.Employee;
import com.allocation.manager.model.Project;
import com.allocation.manager.model.ProjectEmployee;
import com.allocation.manager.service.impl.AllocationServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class AllocationServiceImplTest extends BaseUnitTest{

    @InjectMocks
    private AllocationServiceImpl allocationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAllocateEmployeeInProject_Success() {
        var projectEmployee = createProjectEmployee();
        projectEmployee.getEmployee().setWorkInSeconds(3600);
        projectEmployee.setStartDate(Instant.now());
        projectEmployee.setEndDate(Instant.now().plusSeconds(3600));
        projectEmployee.setAllocatedHours(4);

        when(projectEmployeeRepository.findAllEmployeeInProject(any(), any(), any(), any()))
                .thenReturn(Collections.emptyList());

        allocationService.allocateEmployeeInProject(projectEmployee);

        verify(projectEmployeeRepository, times(1)).save(projectEmployee);
    }

    @Test
    public void testUpdateAllocationsEmployeesInProjects_HappyPath() {
        Employee employeeMock = mock(Employee.class);
        Project projectMock = mock(Project.class);
        ProjectEmployee projectEmployee = new ProjectEmployee();

        projectEmployee.setEmployee(employeeMock);
        projectEmployee.setProject(projectMock);
        projectEmployee.setStartDate(Instant.now().plus(1, ChronoUnit.DAYS));
        projectEmployee.setEndDate(Instant.now().plus(10, ChronoUnit.DAYS));
        List<ProjectEmployee> projectsEmployees = List.of(projectEmployee);

        ProjectEmployee oldProjectEmployee = new ProjectEmployee();
        oldProjectEmployee.setEmployee(employeeMock);
        oldProjectEmployee.setProject(projectMock);
        oldProjectEmployee.setStartDate(Instant.now().minus(1, ChronoUnit.DAYS));
        oldProjectEmployee.setEndDate(Instant.now().plus(5, ChronoUnit.DAYS));

        when(projectEmployeeRepository.findByEmployeeIdAndProjectId(
                employeeMock.getEmployeeId(), projectMock.getProjectId())
        ).thenReturn(oldProjectEmployee);

        doNothing().when(employeeMock).verifyHoursDisponible(anyLong());

        allocationService.updateAllocationsEmployeesInProjects(projectsEmployees);

        verify(projectEmployeeRepository, times(1)).saveAll(projectsEmployees);
        verify(employeeRepository, times(1)).saveAll(anyList());
        verify(employeeMock, times(1)).setWorkInSeconds(anyLong());
    }


    @Test
    public void testUpdateAllocationsEmployeesInProjects_InvalidStartDate() {
        ProjectEmployee projectEmployee = createProjectEmployee();
        projectEmployee.setStartDate(Instant.now().plus(10, ChronoUnit.DAYS));
        projectEmployee.setEndDate(Instant.now().plus(5, ChronoUnit.DAYS));
        List<ProjectEmployee> projectsEmployees = List.of(projectEmployee);

        try {
            allocationService.updateAllocationsEmployeesInProjects(projectsEmployees);
        } catch (IllegalArgumentException e) {
            assert(e.getMessage().contains("As datas fornecidas são inválidas. Verifique os valores e tente novamente."));
        }

        verify(projectEmployeeRepository, times(0)).saveAll(projectsEmployees);
        verify(employeeRepository, times(0)).saveAll(anyList());
    }

    @Test
    public void testUpdateAllocationsEmployeesInProjects_EmployeeNotFound() {
        ProjectEmployee projectEmployee = createProjectEmployee();
        projectEmployee.setStartDate(Instant.now().plus(1, ChronoUnit.DAYS));
        projectEmployee.setEndDate(Instant.now().plus(10, ChronoUnit.DAYS));
        List<ProjectEmployee> projectsEmployees = List.of(projectEmployee);

        when(projectEmployeeRepository.findByEmployeeIdAndProjectId(projectEmployee.getEmployee().getEmployeeId(),
                projectEmployee.getProject().getProjectId())).thenReturn(null);

        try {
            allocationService.updateAllocationsEmployeesInProjects(projectsEmployees);
        } catch (EntityNotFoundException e) {
            assert(e.getMessage().contains("Não há colaborador ID"));
        }

        verify(projectEmployeeRepository, times(0)).saveAll(projectsEmployees);
        verify(employeeRepository, times(0)).saveAll(anyList());
    }

    @Test
    public void testUpdateAllocationsEmployeesInProjects_InsufficientHours() {
        Employee mockedEmployee = mock(Employee.class);

        ProjectEmployee projectEmployee = createProjectEmployee();
        projectEmployee.setEmployee(mockedEmployee);
        projectEmployee.setStartDate(Instant.now().plus(1, ChronoUnit.DAYS));
        projectEmployee.setEndDate(Instant.now().plus(10, ChronoUnit.DAYS));
        List<ProjectEmployee> projectsEmployees = List.of(projectEmployee);

        ProjectEmployee oldProjectEmployee = new ProjectEmployee();
        oldProjectEmployee.setStartDate(Instant.now().minus(1, ChronoUnit.DAYS));
        oldProjectEmployee.setEndDate(Instant.now().plus(5, ChronoUnit.DAYS));
        oldProjectEmployee.setEmployee(mockedEmployee);
        oldProjectEmployee.setProject(projectEmployee.getProject());

        when(projectEmployeeRepository.findByEmployeeIdAndProjectId(
                mockedEmployee.getEmployeeId(),
                projectEmployee.getProject().getProjectId()
        )).thenReturn(oldProjectEmployee);

        doThrow(new InsufficientWorkHoursException("O colaborador não contém horas disponíveis suficientes para a nova alocação."))
                .when(mockedEmployee).verifyHoursDisponible(anyLong());

        Exception exception = assertThrows(InsufficientWorkHoursException.class, () -> {
            allocationService.updateAllocationsEmployeesInProjects(projectsEmployees);
        });

        assertEquals("O colaborador não contém horas disponíveis suficientes para a nova alocação.", exception.getMessage());

        verify(projectEmployeeRepository, times(0)).saveAll(projectsEmployees);
        verify(employeeRepository, times(0)).saveAll(anyList());
    }

    @Test
    void testAllocateEmployeeInProject_InvalidDate() {
        ProjectEmployee projectEmployee = new ProjectEmployee();
        projectEmployee.setStartDate(Instant.now());
        projectEmployee.setEndDate(Instant.now());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            allocationService.allocateEmployeeInProject(projectEmployee);
        });

        assertEquals("As datas fornecidas são inválidas. Verifique os valores e tente novamente.", exception.getMessage());
        verify(projectEmployeeRepository, times(0)).save(any());
    }

    @Test
    public void testAllocateEmployeeInProject_EmployeeExceedsHours() {
        ProjectEmployee projectEmployee = createProjectEmployee();
        projectEmployee.setAllocatedHours(1000);

        try {
            allocationService.allocateEmployeeInProject(projectEmployee);
        } catch (InsufficientWorkHoursException e) {
            assert(e.getMessage().contains("Horas insuficientes para o dia:"));
        }

        verify(projectEmployeeRepository, times(0)).save(projectEmployee);
    }
}
