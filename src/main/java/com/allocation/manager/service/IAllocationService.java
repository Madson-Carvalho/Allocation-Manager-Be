package com.allocation.manager.service;

import java.util.Date;

public interface IAllocationService {
    void allocationEmployeeWithProject(long employeeId, long projectId, Date startDate, Date endDate);
    boolean isEmployeeAllocatedToProject(long employeeId, Date startDate, Date endDate);
}
