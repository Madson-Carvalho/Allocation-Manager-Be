package com.allocation.manager.repository;

import com.allocation.manager.model.Employee;
import com.allocation.manager.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProjectRepository extends JpaRepository<Project, UUID> {
    @Query("SELECT e FROM Project e WHERE e.projectId = ?1")
    Employee findByUuid(UUID uuid);
}
