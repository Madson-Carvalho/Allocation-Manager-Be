package com.allocation.manager.controller.routes;

public class Routes {

    //region Allocation Employee In Project Routes
    public static final String AllocateEmployeeInProject = "/allocate-employee/{employeeId}/in/project/{projectId}/{startDate}/{endDate}";
    public static final String DeallocateEmployeeTheProject = "/deallocate-employee-the-project";
    public static final String FindAllProjectsEmployees = "/find-all" ;
    public static final String UpdateAllocationsEmployeesWithProjects = "/update-allocations-employees-with-projects";
    public static final String UpdateAllocationEmployeeWithProject = "/update-allocation-employee-with-project";
    //endregion

    //region Employee Routes
    public static final String CreateEmployee = "/create-employee";
    public static final String UpdateEmployee = "/update-employee";
    public static final String DeleteEmployee = "/delete-employee/{id}";
    public static final String FindAllEmployees = "/find-all";
    public static final String FindEmployeeById = "/find-by-id/{id}";
    //endregion

    //region Project Routes
    public static final String CreateProject = "/create-project";
    public static final String UpdateProject = "/update-project";
    public static final String DeleteProject = "/delete-project/{id}";
    public static final String FindAllProjects = "/find-all";
    public static final String FindProjectById = "/find-by-id/{id}";
    //endregion
}
