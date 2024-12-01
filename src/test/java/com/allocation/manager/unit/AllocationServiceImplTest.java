package com.allocation.manager.unit;

import com.allocation.manager.model.ProjectEmployee;
import com.allocation.manager.service.impl.AllocationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.time.Instant;
import java.util.Collections;

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
        verify(employeeRepository, times(1)).save(projectEmployee.getEmployee());
        verify(projectRepository, times(1)).save(projectEmployee.getProject());
    }

    //region Test Exception
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


    //endregion Test Exception
}
