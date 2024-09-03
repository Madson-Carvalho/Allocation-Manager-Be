package com.allocation.manager.repository;

import com.allocation.manager.model.ProjectEmployee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.UUID;

@Repository
public interface ProjectEmployeeRepository extends JpaRepository<ProjectEmployee, UUID> {

    @Query("SELECT COUNT(pe) > 0 FROM ProjectEmployee pe WHERE pe.EmployeeId = :employeeId AND (pe.startDate < :endDate AND pe.endDate > :startDate)")
    boolean isEmployeeAllocatedDuringPeriod(@Param("employeeId") Long employeeId, @Param("startDate") Date startDate, @Param("endDate") Date endDate);


}
