package com.allocation.manager.model;

import com.allocation.manager.exceptions.InsufficientWorkHoursException;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "Employees")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "employeeId")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, nullable = false)
    private UUID employeeId;

    @Column(nullable = false, length = 100)
    private String name;
    @Column(nullable = false, length = 50)
    private String email;

    @Column(nullable = false)
    private long workInSeconds;

    @Column(nullable = false, length = 50)
    private String jobRole;
    @Column(nullable = false)
    private double wage;
    @Column(length = 50)
    private String qualification;
    @Column(length = 100)
    private String specializations;

    public UUID getEmployeeId() {
        return employeeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getWorkInSeconds() {
        return workInSeconds;
    }

    public void setWorkInSeconds(long workeHours) {
        this.workInSeconds = workeHours;
    }

    public String getJobRole() {
        return jobRole;
    }

    public void setJobRole(String jobRole) {
        this.jobRole = jobRole;
    }

    public double getWage() {
        return wage;
    }

    public void setWage(double wage) {
        this.wage = wage;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getSpecializations() {
        return specializations;
    }

    public void setSpecializations(String specializations) {
        this.specializations = specializations;
    }

    public void verifyHoursDisponible(long requestInSeconds){
        if(getWorkInSeconds() < requestInSeconds){
            throw new InsufficientWorkHoursException("O colaborador não contém horas disponíveis suficientes para a nova alocação.");
        }
    }

}
