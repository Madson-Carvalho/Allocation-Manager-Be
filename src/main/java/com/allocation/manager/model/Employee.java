package com.allocation.manager.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "Employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, nullable = false)
    private UUID employeeId;

    @Column(nullable = false, length = 100)
    private String name;
    @Column(nullable = false, length = 50)
    private String email;
    @Column(nullable = false, length = 50)
    private double workeHours;
    @Column(nullable = false, length = 50)
    private String jobRole;
    @Column()
    private double wage;
    @Column(length = 50)
    private String qualification;
    @Column(length = 100)
    private String specializations;

    public UUID getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(UUID employeeId) {
        this.employeeId = employeeId;
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

    public double getWorkeHours() {
        return workeHours;
    }

    public void setWorkeHours(double workeHours) {
        this.workeHours = workeHours;
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
}
