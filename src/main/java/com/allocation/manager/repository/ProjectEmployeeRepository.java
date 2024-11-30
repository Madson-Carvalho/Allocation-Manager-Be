package com.allocation.manager.repository;

import com.allocation.manager.model.ProjectEmployee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Repository
public interface ProjectEmployeeRepository extends JpaRepository<ProjectEmployee, UUID> {

    @Query("SELECT COUNT(pe) > 0 FROM ProjectEmployee pe WHERE pe.employee.employeeId = :employeeId AND (pe.startDate < :endDate AND pe.endDate > :startDate)")
    boolean isEmployeeAllocatedDuringPeriod(@Param("employeeId") UUID employeeId, @Param("startDate") Instant startDate, @Param("endDate") Instant endDate);

    @Query("SELECT pe FROM ProjectEmployee pe WHERE pe.employee.employeeId = :employeeId AND pe.project.projectId = :projectId")
    ProjectEmployee findByEmployeeIdAndProjectId(@Param("employeeId") UUID employeeId, @Param("projectId") UUID projectId);

    @Query("SELECT pe FROM ProjectEmployee pe WHERE (:employeeId IS NULL OR pe.employee.employeeId = :employeeId) AND (:projectId IS NULL OR pe.project.projectId = :projectId) AND (:startDate IS NULL OR pe.startDate >= :startDate) AND (:endDate IS NULL OR pe.endDate <= :endDate)")
    List<ProjectEmployee> findAllEmployeeInProject(UUID employeeId, UUID projectId, Instant startDate, Instant endDate);

    @Query("SELECT pe.project FROM ProjectEmployee pe WHERE pe.employee.employeeId = :employeeId")
    List<ProjectEmployee> findAllProjectsByEmployeeId(UUID employeeId);
}
