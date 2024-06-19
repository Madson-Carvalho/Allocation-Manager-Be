package com.allocation.manager.repository;

import com.allocation.manager.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, UUID> {
    @Query("SELECT e FROM Employee e WHERE e.employeeId = ?1")
    Employee findByUuid(UUID uuid);
}
