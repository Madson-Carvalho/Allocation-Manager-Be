package com.allocation.manager.controller;

import com.allocation.manager.service.IAllocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/allocation")
public class AllocationController {

    @Autowired
    private IAllocationService service;

    @PostMapping("/allocated-employee/{employeeId}/in/project/{projectId}/{startDate}/{endDate}")
    public ResponseEntity<Void> allocatedEmployeeInProject(@PathVariable UUID employeeId,
                                                                      @PathVariable UUID projectId,
                                                                      @PathVariable String startDate,
                                                                      @PathVariable String endDate) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startDate1 = LocalDate.parse(startDate, formatter);
        LocalDate endDate1 = LocalDate.parse(endDate, formatter);

        Date startDateAsDate = Date.from(startDate1.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date endDateAsDate = Date.from(endDate1.atStartOfDay(ZoneId.systemDefault()).toInstant());

        service.allocationEmployeeWithProject(employeeId, projectId, startDateAsDate, endDateAsDate);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
