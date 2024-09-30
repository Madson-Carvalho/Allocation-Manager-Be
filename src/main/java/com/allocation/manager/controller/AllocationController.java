package com.allocation.manager.controller;

import com.allocation.manager.controller.routes.Routes;
import com.allocation.manager.service.IAllocationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.UUID;


@RestController
@RequestMapping("api/v1/allocation")
@Tag(name = "Allocation Controller", description = "Operações de alocação de colaboladores em projetos")
public class AllocationController {

    @Autowired
    private IAllocationService service;

    @PostMapping(Routes.AllocationEmployeeInProject)
    public ResponseEntity<Void> allocatedEmployeeInProject(@PathVariable UUID employeeId,
                                                                      @PathVariable UUID projectId,
                                                                      @PathVariable Date startDate,
                                                                      @PathVariable Date endDate)
    {
        service.allocationEmployeeWithProject(employeeId, projectId, startDate, endDate);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
