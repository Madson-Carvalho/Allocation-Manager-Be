package com.allocation.manager.service;

import java.util.Date;
import java.util.UUID;

public interface IAllocationService {
    void allocationEmployeeWithProject(UUID employeeId, UUID projectId, Date startDate, Date endDate);
    boolean isEmployeeAllocatedToProject(UUID employeeId, Date startDate, Date endDate);
}
