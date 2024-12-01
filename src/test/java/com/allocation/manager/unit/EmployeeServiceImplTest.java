package com.allocation.manager.unit;

import com.allocation.manager.model.Employee;
import com.allocation.manager.service.impl.EmployeeServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EmployeeServiceImplTest extends BaseUnitTest {

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee employee;
    private UUID employeeId;

    @BeforeEach
    void setUp() {
        employee = createEmployee();
        employeeId = employee.getEmployeeId();
    }

    @Test
    void testCreateEmployee() {
        when(employeeRepository.save(employee)).thenReturn(employee);

        Employee createdEmployee = employeeService.createEmployee(employee);

        assertNotNull(createdEmployee);
        assertEquals(employee.getEmployeeId(), createdEmployee.getEmployeeId());
        assertEquals(employee.getName(), createdEmployee.getName());
        verify(employeeRepository, times(1)).save(employee);
    }

    @Test
    void testUpdateEmployee() {
        Employee updatedEmployee = createEmployee();
        updatedEmployee.setName("Updated Name");
        updatedEmployee.setEmail("updated.email@example.com");

        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));
        when(employeeRepository.save(any(Employee.class))).thenReturn(updatedEmployee);

        Employee result = employeeService.updateEmployee(updatedEmployee);

        assertNotNull(result);
        assertEquals("Updated Name", result.getName());
        assertEquals("updated.email@example.com", result.getEmail());
        verify(employeeRepository, times(1)).findById(employeeId);
        verify(employeeRepository, times(1)).save(any(Employee.class));
    }

    @Test
    void testDeleteEmployee_NotFound() {
        when(employeeRepository.existsById(employeeId)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> employeeService.deleteEmployee(employeeId));

        verify(employeeRepository, times(1)).existsById(employeeId);
    }

    @Test
    void testDeleteEmployee() {
        when(employeeRepository.existsById(employeeId)).thenReturn(true);

        employeeService.deleteEmployee(employeeId);

        verify(employeeRepository, times(1)).deleteById(employeeId);
    }

    @Test
    void testFindAllEmployees() {
        when(employeeRepository.findAll()).thenReturn(List.of(createEmployee()));

        List<Employee> employees = employeeService.findAll();

        assertNotNull(employees);
        assertFalse(employees.isEmpty());
        verify(employeeRepository, times(1)).findAll();
    }

    @Test
    void testFindEmployeeById() {
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(createEmployee()));

        Optional<Employee> foundEmployee = employeeService.findById(employeeId);

        assertTrue(foundEmployee.isPresent());
        assertEquals(employeeId, foundEmployee.get().getEmployeeId());
        verify(employeeRepository, times(1)).findById(employeeId);
    }
}
